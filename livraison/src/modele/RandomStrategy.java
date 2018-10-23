package modele;

import java.util.Random;

public class RandomStrategy implements PlayerStrategy {

	private Player client;

	public RandomStrategy(Player client) {
		this.client = client;
	}

	public void execute() {
		int r = new Random().nextInt(4);
		client.move(Direction.getDirections().get(r));
	}
}
