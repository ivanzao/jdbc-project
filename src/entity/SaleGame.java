package entity;

public class SaleGame {
	private Sale sale;
	private Game game;
	
	public SaleGame(Sale sale, Game game) {
		this.sale = sale;
		this.game = game;
	}

	public Sale getSale() {
		return sale;
	}

	public Game getGame() {
		return game;
	}
}
