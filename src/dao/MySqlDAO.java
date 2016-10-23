package dao;

import connection.ConnectionFactory;
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

@SuppressWarnings("unchecked")
public class MySqlDAO<T, K> {

    private Connection connection;
    private Class<T> klass;
    private String className;
    private String entityName;
    private Field[] fields;
    private String idName;
    private Method mapperMethod;

    MySqlDAO(Class<T> klass) {
        this.connection = ConnectionFactory.getConnection();
        this.klass = klass;
        this.className = klass.getName().substring(klass.getName().lastIndexOf(".") + 1);
        this.entityName = getEntityName(className);
        this.fields = klass.getDeclaredFields();
        this.idName = ReflectionUtils.getIdName(fields);
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

    public List findAll() {
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

    public boolean save(T entity) {
        StringBuilder sbQuery = new StringBuilder("INSERT INTO ").append(entityName).append("(");
        StringBuilder sbValues = new StringBuilder("VALUES(");
        List<Object> parameters = new ArrayList<>();
        try {
            for (Field field : fields) {
                Method method = ReflectionUtils.getGetterMethodForField(field, klass);
                String fieldName = field.getName();
                if (method != null) {
                    parameters.add(method.invoke(entity));
                    if (fields[fields.length - 1] != field) {
                        sbQuery.append(getEntityName(fieldName)).append(", ");
                        sbValues.append("?, ");
                    } else {
                        sbQuery.append(getEntityName(fieldName)).append(") ");
                        sbValues.append("?);");
                    }
                }
            }
            sbQuery.append(sbValues);
            PreparedStatement statement = prepareStatementWithParameters(sbQuery.toString(), parameters);
            statement.executeUpdate();
            return true;
        } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
            System.out.println("Could not save entity " + entityName + ". " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(T entity) {
        StringBuilder sb = new StringBuilder("UPDATE ").append(entityName).append(" SET ");
        List<Object> parameters = new ArrayList<>();
        try {
            for (Field field : fields) {
                Method method = ReflectionUtils.getGetterMethodForField(field, klass);
                String fieldName = field.getName();
                if (method != null) {
                    parameters.add(method.invoke(entity));
                    if (fields[fields.length - 1] != field) {
                        sb.append(getEntityName(fieldName)).append(" = ?, ");
                    } else {
                        sb.append(getEntityName(fieldName)).append(" = ?);");
                    }
                }
            }
            PreparedStatement statement = prepareStatementWithParameters(sb.toString(), parameters);
            statement.executeUpdate();
            return true;
        } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
            System.out.println("Could not save entity " + entityName + ". " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(K id) {
        try {
            String query = "DELETE FROM " + entityName + " WHERE " + idName + " = ?;";
            PreparedStatement statement = connection.prepareStatement(query.toString());
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

    private String getEntityName(String name) {
        StringBuilder builder = new StringBuilder();
        char[] nameAsArray = name.toCharArray();
        for (char character : nameAsArray) {
            if (!Character.isUpperCase(character)) {
                builder.append(character);
            } else if (nameAsArray[0] != character) {
                builder.append("_");
                builder.append(Character.toLowerCase(character));
            } else {
                builder.append(Character.toLowerCase(character));
            }
        }
        return builder.toString();
    }

    private PreparedStatement prepareStatementWithParameters(String query, List<Object> parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i <= parameters.size(); i++) {
            statement.setObject(i, parameters.get(i - 1));
        }
        return statement;
    }
}