package dao;

import java.sql.Connection;

import connection.ConnectionFactory;

public class DAOFactory {

	public static ClientDAO getClientDAO() {
		Connection connection = ConnectionFactory.getConnection();
		return new MySqlClientDao(connection);
	}
}
