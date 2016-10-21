package dao;

import entity.Client;
import mapper.ClientMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlClientDao implements ClientDAO {

    private Connection connection;

    public MySqlClientDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findOne(String id) {
        String query = "SELECT * FROM client WHERE cpf = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return ClientMapper.getFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                clients.add(ClientMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void save(Client entity) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Client entity) {
        // TODO Auto-generated method stub
    }

    @Override
    public Client findByName(String cpf) {
        // TODO Auto-generated method stub
        return null;
    }
}
