package klass;

import dao.DAOFactory;
import dao.MySqlDAO;
import entity.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionTest {
    private static Class<Client> klass = Client.class;

    public static void main(String[] args) {
        MySqlDAO<Client, String> clientDAO = DAOFactory.getClientDAO();
        clientDAO.delete("00000000000");

        List<Field> parameters = new ArrayList<>();
        parameters.addAll(Arrays.asList(klass.getDeclaredFields()));

        List<Method> methods = getClassGetters(parameters);
        for (Method method : methods) {
            try {
                System.out.println(method.invoke(new Client("")));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Method> getClassGetters(List<Field> parameters) {
        List<Method> methods = new ArrayList<>();
        for (Field field : parameters) {
            try {
                Method method = klass.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                methods.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methods;
    }
}
