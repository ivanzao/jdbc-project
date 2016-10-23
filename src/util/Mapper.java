package util;

import entity.Client;
import entity.Producer;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

    //public static Object getObjectFromResultSet(ResultSet rs, Class klass) {
     //   Object id =
    //}

    private void invokeGetMethod() {

    }

    public static Client getClientFromResultSet(ResultSet rs) throws SQLException {
        String cpf = rs.getString("cpf");
        if (cpf != null) {
            Client client = new Client(cpf);
            client.setAddress(rs.getString("address"));
            client.setEmail(rs.getString("email"));
            client.setName(rs.getString("name"));
            client.setPhone(rs.getString("phone"));
            client.setCreatedAt(rs.getDate("created_at"));
            return client;
        }
        return null;
    }

    public static Producer getProducerFromResultSet(ResultSet rs) throws SQLException {
        String cnpj = rs.getString("cnpj");
        if (cnpj != null) {
            Producer producer = new Producer(cnpj);
            producer.setName(rs.getString("name"));
            return producer;
        }
        return null;
    }
}