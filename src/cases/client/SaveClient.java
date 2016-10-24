package cases.client;

import dao.MySqlDAO;
import entity.Client;

public class SaveClient {

    private MySqlDAO<Client, String> dao;

    public SaveClient(MySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean save(Client client) {
        return dao.save(client);
    }
}
