package cases.client;

import dao.DynamicMySqlDAO;
import entity.Client;

public class DeleteClient {

    private DynamicMySqlDAO<Client, String> dao;

    public DeleteClient(DynamicMySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean delete(Client client) {
        return dao.delete(client.getCpf());
    }
}
