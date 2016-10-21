package dao;

import entity.Client;

public interface ClientDAO extends DAO<Client, String> {
	Client findByName(String cpf);
}
