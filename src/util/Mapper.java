package util;

import dao.DAOFactory;
import dao.DynamicMySqlDAO;
import entity.*;
import entity.type.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

    private static DynamicMySqlDAO<Producer, String> producerDao = DAOFactory.getProducerDAO();
    private static DynamicMySqlDAO<Client, String> clientDao = DAOFactory.getClientDAO();
    private static DynamicMySqlDAO<Store, String> storeDao = DAOFactory.getStoreDAO();
    private static DynamicMySqlDAO<Sale, Integer> saleDao = DAOFactory.getSaleDAO();
    private static DynamicMySqlDAO<Game, Integer> gameDao = DAOFactory.getGameDAO();

    public static Client getClientFromResultSet(ResultSet rs) throws SQLException {
        String cpf = rs.getString("cpf");
        if (cpf != null) {
            Client client = new Client(cpf);
            client.setAddress(rs.getString("address"));
            client.setEmail(rs.getString("email"));
            client.setName(rs.getString("name"));
            client.setPhone(rs.getString("phone"));
            client.setCreatedAt(rs.getDate("created_at"));
            return client;
        }
        return null;
    }

    public static Producer getProducerFromResultSet(ResultSet rs) throws SQLException {
        String cnpj = rs.getString("cnpj");
        if (cnpj != null) {
            Producer producer = new Producer(cnpj);
            producer.setName(rs.getString("name"));
            return producer;
        }

        return null;
    }

    public static Game getGameFromResultSet(ResultSet rs) throws SQLException {
        String producerCnpj = rs.getString("producer_cnpj");
        if (!producerCnpj.isEmpty()) {
            Producer producer = producerDao.findOne(producerCnpj);
            Game game = new Game(rs.getInt("id"), producer);
            game.setName(rs.getString("name"));
            game.setPrice(rs.getFloat("price"));
            game.setGenre(Genre.valueOf(rs.getString("genre")));
            return game;
        }

        return null;
    }

    public static Sale getSaleFromResultSet(ResultSet rs) throws SQLException {
        String storeCnpj = rs.getString("store_cnpj");
        String clientCpf = rs.getString("client_cpf");
        if (!storeCnpj.isEmpty() && !clientCpf.isEmpty()) {
            Sale sale = new Sale(rs.getInt("id"),
                    storeDao.findOne(storeCnpj),
                    clientDao.findOne(clientCpf));

            sale.setCreatedAt(rs.getDate("created_at"));
            sale.setValue(rs.getFloat("value"));
            return sale;
        }

        return null;
    }

    public static Store getStoreFromResultSet(ResultSet rs) throws SQLException {
        String cnpj = rs.getString("cnpj");
        if (!cnpj.isEmpty()) {
            Store store = new Store(cnpj);
            store.setName(rs.getString("name"));
            store.setUrl(rs.getString("url"));
            store.setEmail(rs.getString("email"));
            store.setPhone(rs.getString("phone"));
            return store;
        }

        return null;
    }

    public static SaleGame getSaleGameFromResultSet(ResultSet rs) throws SQLException {
        Integer saleId = rs.getInt("sale_id");
        Integer gameId = rs.getInt("game_id");
        return new SaleGame(saleDao.findOne(saleId), gameDao.findOne(gameId));
    }
}
