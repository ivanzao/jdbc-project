package cases.client;

import dao.DynamicMySqlDAO;
import entity.Client;

public class SaveClient {

    private DynamicMySqlDAO<Client, String> dao;

    public SaveClient(DynamicMySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean save(Client client) {
        return dao.save(client);
    }
}
