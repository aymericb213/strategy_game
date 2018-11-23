package modele;

import java.util.*;

public class RandomGeneration implements GridStrategy {

    @Override
    public void generate(RealGrid g) {
        Tile[] random_grid=g.getGrid();
        Random r = new Random();
        for (int i=0 ; i<random_grid.length ; i++) {
            Tile n = new FreeTile(i%g.getWidth(),i/g.getWidth());
            double nr = r.nextDouble();
            if (nr < 0.2) {
                n = new Bonus(i%g.getWidth(),i/g.getWidth());
            } else if (nr >= 0.2 && nr < 0.5) {
                n = new Wall(i%g.getWidth(),i/g.getWidth());
            }
            random_grid[i]=n;
        }

        for (Player p : g.getPlayers()) {
            int rx = r.nextInt(g.getWidth());
            int ry = r.nextInt(g.getGrid().length/g.getWidth());
            p.setPosition(rx,ry);
            g.setTileAt(p);
            for (Tile t : g.getNeighbouringTiles(p,1)) {
              if (!(t instanceof Player)) {
                g.setTileAt(new FreeTile(t.getX(),t.getY()));
              }
            }
        }
        g.setGrid(random_grid);
    }
}
