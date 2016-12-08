package entity;

import entity.annotation.Id;

import java.util.Date;

public class Sale {
	@Id
    private int id;
	private float value;
	private Date createdAt;
	private Store store;
	private Client client;
	
	public Sale(int id, Store store, Client client) {
		this.id = id;
		this.store = store;
		this.client = client;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public Store getStore() {
		return store;
	}

	public Client getClient() {
		return client;
	}
}
