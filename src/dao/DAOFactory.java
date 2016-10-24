package dao;

import entity.*;

public class DAOFactory {

    public static MySqlDAO<Client, String> getClientDAO() {
        return new MySqlDAO<>(Client.class);
    }

    public static MySqlDAO<Producer, String> getProducerDAO() {
        return new MySqlDAO<>(Producer.class);
    }

    public static MySqlDAO<Game, String> getGameDAO() {
        return new MySqlDAO<>(Game.class);
    }

    public static MySqlDAO<Sale, String> getSaleDAO() {
        return new MySqlDAO<>(Sale.class);
    }

    public static MySqlDAO<SaleGame, String> getSaleGameDAO() {
        return new MySqlDAO<>(SaleGame.class);
    }

    public static MySqlDAO<Store, String> getStoreDAO() {
        return new MySqlDAO<>(Store.class);
    }
}
