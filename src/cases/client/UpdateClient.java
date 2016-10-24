package cases.client;

import dao.MySqlDAO;
import entity.Client;

public class UpdateClient {

    private MySqlDAO<Client, String> dao;

    public UpdateClient(MySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public boolean update(Client client) {
        return dao.update(client);
    }
}
