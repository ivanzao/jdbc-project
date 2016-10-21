package main;

import dao.ClientDAO;
import dao.DAOFactory;
import entity.Client;

public class Main {

	public static void main(String[] args) {
		ClientDAO dao = DAOFactory.getClientDAO();
		Client client = dao.findOne("12345678910");
		
		System.out.println("----- Client -----");
		System.out.println("CPF: " + client.getCpf());
		System.out.println("Nome: " + client.getName());
		System.out.println("Telefone: " + client.getPhone());
		System.out.println("Email: " + client.getEmail());
		System.out.println("Endereço: " + client.getAddress());
		System.out.println("------------------");
	}
}
