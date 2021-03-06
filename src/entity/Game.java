package entity;

public class Game {
	private int id;
	private String name;
	private float price;
	private Genre genre;
	private Producer producer;
	
	public Game(int id, Producer producer) {
		this.id = id;
		this.producer = producer;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Producer getProducer() {
		return producer;
	}
}
