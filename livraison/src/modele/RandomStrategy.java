package modele;

import java.util.*;

public class RandomStrategy implements PlayerStrategy {

    private final Player client;

    public RandomStrategy(Player client) {
        this.client = client;
    }

    @Override
    public void execute() {
        int r = new Random().nextInt(4);
        switch (r) {
            case 0 :
                client.move(Direction.getDirections().get(r));
                break;
            case 1 :
                boolean r_plant = new Random().nextBoolean();
                ArrayList<FreeTile> sites = client.getView().getModel().getNeighbouringFreeTiles(this.client,1);
                if (sites.size()>0) {
                    int r_site = new Random().nextInt(sites.size());
                    if (r_plant) {
                        client.plant(new Mine(this.client), sites.get(r_site));
                    } else {
                        client.plant(new Bomb(this.client), sites.get(r_site));
                    }
                }
                break;
            case 2 :
                client.enableShield();
                break;
            case 3 :
                client.fire(Direction.getDirections().get(r));
                break;
            default :
                System.out.println("Out of range");
        }
    }
}
