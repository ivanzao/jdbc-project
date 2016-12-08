package entity.type;

public enum Genre {
	ACTION("ACTION"),
	ADVENTURE("ADVENTURE"),
	HORROR("HORROR"),
	RPG("RPG"),
	SPORTS("SPORTS"),
	FIGHTING("FIGHTING");
	
	private String genre;
	
	private Genre(String genre) {
		this.genre = genre;
	}
	
	@Override
	public String toString() {
		return genre;
	}
}
