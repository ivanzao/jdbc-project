package entity;

import java.util.Date;

public class Sale {
	@Id private int id;
	private float value;
	private Date date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
