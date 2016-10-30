package entity;

import entity.annotation.Id;

public class Store {
	@Id
	private String cnpj;
	private String name;
	private String url;
	private String email;
	private String phone;
	
	public Store(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCnpj() {
		return cnpj;
	}
}
