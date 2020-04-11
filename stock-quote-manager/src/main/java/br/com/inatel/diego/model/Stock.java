package br.com.inatel.diego.model;

public class Stock {
	private String id;
	private String description;
	public Stock() {
		// TODO Auto-generated constructor stub
	}
	public Stock(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
