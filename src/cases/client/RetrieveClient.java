package cases.client;

import dao.DynamicMySqlDAO;
import entity.Client;
import util.ReflectionUtils;

import java.util.List;

public class RetrieveClient {

    private DynamicMySqlDAO<Client, String> dao;

    public RetrieveClient(DynamicMySqlDAO<Client, String> dao) {
        this.dao = dao;
    }

    public Client findByCPF(String cpf) {
        return dao.findOne(cpf);
    }

    public List<Client> findAll() {
        return dao.findAll();
    }

    public Client findByParameter(String parameter, Object value) throws NoSuchFieldException {
        if (ReflectionUtils.hasField(parameter, Client.class)) {
            return dao.findByParameter(parameter, value);
        }
        throw new NoSuchFieldException();
    }

    public List<Client> findAllByParameter(String parameter, Object value) throws NoSuchFieldException {
        if (ReflectionUtils.hasField(parameter, Client.class)) {
            return dao.findAllByParameter(parameter, value);
        }
        throw new NoSuchFieldException();
    }
}

