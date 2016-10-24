package cases.client;

import dao.MySqlDAO;
import entity.Client;

public class DeleteClient {

    private MySqlDAO<Client, String> dao;

    public DeleteClient(MySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean delete(Client client) {
        return dao.delete(client.getCpf());
    }
}
