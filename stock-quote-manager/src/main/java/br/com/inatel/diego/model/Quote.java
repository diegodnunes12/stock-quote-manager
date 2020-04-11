package br.com.inatel.diego.model;

import javax.persistence.*;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	private String stock;
	private String value;
	private String date;
	
	public Quote() {
		// TODO Auto-generated constructor stub
	}	

	public Quote(int id, String stock, String value, String date) {
		super();
		this.id = id;
		this.stock = stock;
		this.value = value;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
