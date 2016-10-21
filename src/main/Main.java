package main;

import dao.ClientDAO;
import dao.DAOFactory;
import entity.Client;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ClientDAO dao = DAOFactory.getClientDAO();

        System.out.println("----- FindAll -----\n");
        List<Client> clients = dao.findAll();

        clients.forEach(client -> {
            System.out.println("----- Client -----");
            System.out.println("CPF: " + client.getCpf());
            System.out.println("Nome: " + client.getName());
            System.out.println("Telefone: " + client.getPhone());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Endereço: " + client.getAddress());
            System.out.println("------------------\n");
        });

        Client client = dao.findOne("12345678910");
        dao.delete(client);

        System.out.println("\n----- FindAll -----");
        List<Client> clientsAfter = dao.findAll();

        clientsAfter.forEach(clientAfter -> {
            System.out.println("----- Client -----");
            System.out.println("CPF: " + clientAfter.getCpf());
            System.out.println("Nome: " + clientAfter.getName());
            System.out.println("Telefone: " + clientAfter.getPhone());
            System.out.println("Email: " + clientAfter.getEmail());
            System.out.println("Endereço: " + clientAfter.getAddress());
            System.out.println("------------------\n");
        });
    }
}
