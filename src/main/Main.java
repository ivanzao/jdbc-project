package main;

import dao.DAOFactory;
import dao.DynamicMySqlDAO;
import entity.Producer;

public class Main {

    public static void main(String[] args) {
        DynamicMySqlDAO dao = DAOFactory.getProducerDAO();

        Producer producer = new Producer("9872640184635");
        producer.setName("Ubisoft");

        dao.save(producer);
    }
}
