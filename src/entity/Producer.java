package entity;

import entity.annotation.Id;

public class Producer {
    @Id
    private String cnpj;
    private String name;

    public Producer() {

    }

    public Producer(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }
}
