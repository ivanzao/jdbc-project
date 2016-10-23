package dao;

import java.sql.Connection;

import connection.ConnectionFactory;
import entity.Client;
import entity.Producer;

public class DAOFactory {

//	public static ClientDAO getClientDAO() {
//		Connection connection = ConnectionFactory.getConnection();
//		return new MySqlClientDao(connection);
//	}

	public static MySqlDAO<Client, String> getClientDAO() {
		return new MySqlDAO<>(Client.class);
	}

    public static MySqlDAO<Producer, String> getProducerDAO() {
        return new MySqlDAO<>(Producer.class);
    }
}
