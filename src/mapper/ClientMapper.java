package mapper;

import entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper {

    public static Client getFromResultSet(ResultSet rs) throws SQLException {
        String cpf = rs.getString("cpf");
        if (cpf != null) {
            Client client = new Client(cpf);
            client.setAddress(rs.getString("address"));
            client.setEmail(rs.getString("email"));
            client.setName(rs.getString("name"));
            client.setPhone(rs.getString("phone"));
            return client;
        }
        return null;
    }
}
