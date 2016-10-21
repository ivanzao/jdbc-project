package dao;

import entity.Client;
import util.Mapper;

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
        String query = "SELECT * FROM client WHERE cpf = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return Mapper.getClientFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                clients.add(Mapper.getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public boolean save(Client client) {
        try {
            String query = "INSERT INTO client (cpf, name, phone, email, address, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, now());";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getCpf());
            statement.setString(2, client.getName());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            String query = "UPDATE client SET name = ?, phone = ?, email = ?, address = ? WHERE cpf = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setString(5, client.getCpf());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Client client) {
        try {
            String query = "DELETE FROM client WHERE cpf = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getCpf());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Client findByName(String name) {
        String query = "SELECT * FROM client WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return Mapper.getClientFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Something went wrong with MySQL." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
