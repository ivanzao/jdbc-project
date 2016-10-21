package entity;

public enum Genre {
	ACTION("action"),
	ADVENTURE("adventure"),
	HORROR("horror"),
	RPG("rpg"),
	SPORTS("sports"),
	FIGHTING("fighting");
	
	private String genre;
	
	private Genre(String genre) {
		this.genre = genre;
	}
	
	@Override
	public String toString() {
		return genre;
	}
}
