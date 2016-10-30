package entity;

import entity.annotation.Id;

public class SaleGame {
	@Id
    private Sale sale;
	@Id private Game game;
	
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
