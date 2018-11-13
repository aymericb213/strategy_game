package modele;

import java.util.Random;

public class RandomStrategy implements PlayerStrategy {
    
    private final Player client;
    
    public RandomStrategy(Player client) {	
        this.client = client;	
    }

    @Override
    public void execute() {
        int r = new Random().nextInt(client.possibleMoves().size());	
        client.move(Direction.getDirections().get(r));	
    }
}
