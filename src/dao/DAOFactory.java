package dao;

import entity.*;

public class DAOFactory {

    public static DynamicMySqlDAO<Client, String> getClientDAO() {
        try {
            return new DynamicMySqlDAO<>(Client.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DynamicMySqlDAO<Producer, String> getProducerDAO() {
        try {
            return new DynamicMySqlDAO<>(Producer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DynamicMySqlDAO<Game, Integer> getGameDAO() {
        try {
            return new DynamicMySqlDAO<>(Game.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DynamicMySqlDAO<Sale, Integer> getSaleDAO() {
        try {
            return new DynamicMySqlDAO<>(Sale.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DynamicMySqlDAO<SaleGame, Integer> getSaleGameDAO() {
        try {
            return new DynamicMySqlDAO<>(SaleGame.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DynamicMySqlDAO<Store, String> getStoreDAO() {
        try {
            return new DynamicMySqlDAO<>(Store.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
