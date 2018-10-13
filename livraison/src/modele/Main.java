package modele;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		Grid g = new Grid(10,10,1);
		Player p1 = new Player();
		p1.printStats();
		/*System.out.println("\033[H\033[2J");
		g.addPlayer(p1);
		g.generateRandomGrid();
		while(true) {
			try {
			Thread.sleep(500);
			System.out.println(g);
			} catch (InterruptedException e) {}
		}*/
	}
}
