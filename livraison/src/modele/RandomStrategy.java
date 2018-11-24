package modele;

import java.util.*;

public class RandomStrategy implements PlayerStrategy {

    private final Player client;

    public RandomStrategy(Player client) {
        this.client = client;
    }

    @Override
    public void execute() {
        int nb_actions = 4;
        int r = new Random().nextInt(nb_actions);//tirage de l'action du bot
        int r_dir = new Random().nextInt(Direction.getDirections().size());//tirage de la direction de déplacement ou de tir
        switch (r) {
            case 0 ://déplacement
                client.move(Direction.getDirections().get(r_dir));
                break;
            case 1 ://posage d'explosif
                boolean r_plant = new Random().nextBoolean();//tirage du type d'explosif
                ArrayList<FreeTile> sites = client.getView().getModel().getNeighbouringFreeTiles(this.client,1);
                if (sites.size()>0) {
                    int r_site = new Random().nextInt(sites.size());//tirage du site de posage
                    if (r_plant) {
                        client.plant(new Mine(this.client), sites.get(r_site));
                    } else {
                        client.plant(new Bomb(this.client), sites.get(r_site));
                    }
                }
                break;
            case 2 ://bouclier
                client.enableShield();
                break;
            case 3 ://tir
                client.fire(Direction.getDirections().get(r_dir));
                break;
            default :
                System.out.println("Out of range");
        }
    }
}
