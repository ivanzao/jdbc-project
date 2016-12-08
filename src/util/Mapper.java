package util;

import dao.DAOFactory;
import dao.DynamicMySqlDAO;
import entity.Client;
import entity.Game;
import entity.Genre;
import entity.Producer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

    private static DynamicMySqlDAO<Producer, String> producerDao = DAOFactory.getProducerDAO();

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
        Integer id = rs.getInt("id");
        String producerCnpj = rs.getString("producer_cnpj");
        if (!producerCnpj.isEmpty()) {
            Producer producer =  producerDao.findOne(producerCnpj);
            Game game = new Game(id, producer);
            game.setName(rs.getString("name"));
            game.setPrice(rs.getFloat("price"));
            game.setGenre(Genre.valueOf(rs.getString("genre")));
            return game;
        }

        return null;
    }
}