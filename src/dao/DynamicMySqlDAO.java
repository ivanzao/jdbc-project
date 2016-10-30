package dao;

import connection.ConnectionFactory;
import entity.annotation.EnumType;
import entity.annotation.ManyToOne;
import util.DatabaseNaming;
import util.Mapper;
import util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ReflectionUtils.getIdField;

@SuppressWarnings("unchecked")
public class DynamicMySqlDAO<T, K> {

    private Connection connection;
    private Class<T> klass;
    private String entityName;
    private Field[] fields;
    private String idName;
    private Method mapperMethod;

    DynamicMySqlDAO(Class<T> klass) throws NoSuchMethodException {
        String className = klass.getName().substring(klass.getName().lastIndexOf(".") + 1);
        
        this.connection = ConnectionFactory.getConnection();
        this.klass = klass;
        this.entityName = DatabaseNaming.getEntityName(className);
        this.fields = klass.getDeclaredFields();
        this.idName = getIdField(fields).toString();
        this.mapperMethod = ReflectionUtils.getMapperMethod(className);
    }

    public T findOne(K id) {
        try {
            String query = "SELECT * FROM " + entityName + " WHERE " + idName + " = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return mapEntity(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public T findByParameter(String parameter, Object value) throws NoSuchFieldException {
        try {
            Field field = klass.getDeclaredField(parameter);
            String query = "SELECT * FROM " + entityName + " WHERE " + DatabaseNaming.getColumnName(field) + " = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, value);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return mapEntity(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll() {
        String query = "SELECT * FROM " + entityName + ";";
        List<T> entities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                entities.add(mapEntity(rs));
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return entities;
    }

    public List<T> findAllByParameter(String parameter, Object value) throws NoSuchFieldException {
        List<T> entities = new ArrayList<>();
        try {
            Field field = klass.getDeclaredField(parameter);
            String query = "SELECT * FROM " + entityName + " WHERE " + DatabaseNaming.getColumnName(field) + " = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, value);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                entities.add(mapEntity(rs));
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return entities;
    }

    public boolean save(T entity) {
        StringBuilder sbQuery = new StringBuilder("INSERT INTO ").append(entityName).append("(");
        StringBuilder sbValues = new StringBuilder("VALUES(");
        List<Field> parameters = new ArrayList<>();
        try {
            for (Field field : fields) {
                parameters.add(field);
                if (fields[fields.length - 1] != field) {
                    sbQuery.append(DatabaseNaming.getColumnName(field)).append(", ");
                    sbValues.append("?, ");
                } else {
                    sbQuery.append(DatabaseNaming.getColumnName(field)).append(") ");
                    sbValues.append("?);");
                }
            }
            sbQuery.append(sbValues);
            PreparedStatement statement = prepareStatementWithParameters(sbQuery.toString(), parameters, entity);
            if (statement != null) {
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Could not save entity " + entityName + ". " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(T entity) {
        StringBuilder sb = new StringBuilder("UPDATE ").append(entityName).append(" SET ");
        List<Field> parameters = new ArrayList<>();
        try {
            for (Field field : fields) {
                Method method = ReflectionUtils.getGetterMethodForField(field, klass);
                if (method != null) {
                    parameters.add(field);
                    if (fields[fields.length - 1] != field) {
                        sb.append(DatabaseNaming.getColumnName(field)).append(" = ?, ");
                    } else {
                        sb.append(DatabaseNaming.getColumnName(field)).append(" = ?);");
                    }
                }
            }
            PreparedStatement statement = prepareStatementWithParameters(sb.toString(), parameters, entity);
            statement.executeUpdate();
            return true;
        } catch (NoSuchMethodException | SQLException e){
            System.out.println("Could not save entity " + entityName + ". " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(K id) {
        try {
            String query = "DELETE FROM " + entityName + " WHERE " + idName + " = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private T mapEntity(ResultSet rs) {
        try {
            return (T) mapperMethod.invoke(Mapper.class, rs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Something went wrong mapping the entity " + klass.getName());
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement prepareStatementWithParameters(String query, List<Field> fieldParameters, T entity) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 1; i <= fieldParameters.size(); i++) {
                Field field = fieldParameters.get(i - 1);
                if (field.isAnnotationPresent(ManyToOne.class)) {
                    statement = applyManyToOneRules(field, statement, entity, i);
                } else if (field.isAnnotationPresent(EnumType.class)) {
                    statement = applyEnumTypeRules(field, statement, entity, i);
                } else {
                    addParameterToStatement(field, statement, entity, i);
                }
            }
            return statement;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement addParameterToStatement(Field field, PreparedStatement statement, T entity, int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        Method getterMethod = ReflectionUtils.getGetterMethodForField(field, klass);
        if (getterMethod != null) {
            statement.setObject(i, getterMethod.invoke(entity));
        }
        throw new InvocationTargetException(new Throwable());
    }

    private PreparedStatement applyManyToOneRules(Field field, PreparedStatement statement, T entity, int i) throws InvocationTargetException, IllegalAccessException, SQLException, NoSuchMethodException {
        Class fieldClass = field.getType();
        Field relatedEntityIdField = ReflectionUtils.getIdField(fieldClass.getDeclaredFields());

        Method entityGetterMethod = ReflectionUtils.getGetterMethodForField(field, klass);
        Method entityIdGetterMethod = ReflectionUtils.getGetterMethodForField(relatedEntityIdField, fieldClass);

        if (entityGetterMethod != null && entityIdGetterMethod != null) {
            Object relatedEntity = entityGetterMethod.invoke(entity);
            statement.setObject(i, entityIdGetterMethod.invoke(relatedEntity));
            return statement;
        }

        throw new InvocationTargetException(new Throwable());
    }

    private PreparedStatement applyEnumTypeRules(Field field, PreparedStatement statement, T entity, int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        Method getterMethod = ReflectionUtils.getGetterMethodForField(field, klass);
        if (getterMethod != null) {
            statement.setObject(i, getterMethod.invoke(entity).toString());
        }
        throw new InvocationTargetException(new Throwable());
    }
}