package modele;

import java.util.Random;

public class RandomGeneration implements GridStrategy {

	public void generate(RealGrid g) {
		Tile[] random_grid=g.getGrid();
		Random r = new Random();
		for (int i=0 ; i<random_grid.length ; i++) {
			Tile n = new FreeTile(i%g.getWidth(),i/g.getWidth());
			double nr = r.nextDouble();
			if (nr < 0.1) {
				n = new Bonus(i%g.getWidth(),i/g.getWidth(), 50);
			} else if (nr >= 0.1 && nr < 0.3) {
				n = new Wall(i%g.getWidth(),i/g.getWidth());
			}
			random_grid[i]=n;
		}
		g.setGrid(random_grid);
	} 
}
