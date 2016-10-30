package klass;

import dao.DAOFactory;
import dao.DynamicMySqlDAO;
import entity.Client;
import entity.Game;
import entity.Genre;
import entity.Producer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionTest {
    private static Class<Client> klass = Client.class;

    public static void main(String[] args) {
        DynamicMySqlDAO producerDAO = DAOFactory.getProducerDAO();
        DynamicMySqlDAO gameDAO = DAOFactory.getGameDAO();

        Producer producer = (Producer) producerDAO.findAll().get(0);

        Game game = new Game(1, producer);
        game.setName("FIFA 2017");
        game.setGenre(Genre.SPORTS);
        game.setPrice((float) 210.40);
        gameDAO.save(game);

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
