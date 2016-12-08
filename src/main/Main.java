package main;

import dao.DAOFactory;
import dao.DynamicMySqlDAO;
import entity.Game;
import entity.Producer;

public class Main {

    public static void main(String[] args) {
        DynamicMySqlDAO<Game, Integer> gameDAO = DAOFactory.getGameDAO();

        Game game = gameDAO.findOne(1);
        Producer producer = game.getProducer();

        System.out.println(producer.getName());
        System.out.println(producer.getCnpj());
    }
}
