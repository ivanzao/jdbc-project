package cases.client;

import dao.DynamicMySqlDAO;
import entity.Client;

public class UpdateClient {

    private DynamicMySqlDAO<Client, String> dao;

    public UpdateClient(DynamicMySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean update(Client client) {
        return dao.update(client);
    }
}
