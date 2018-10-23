package modele;

import java.util.Random;

public class RandomStrategy implements PlayerStrategy {

	private Player p;

	public RandomStrategy(Player p) {
		this.p = p;
	}
	
	public void execute() {
		int r = new Random().nextInt(4);
		p.move(Direction.getDirections().get(r));
	}
}
