package modele;

import java.util.*;

public class Main {


	public static void main(String[] args) {

		PlayerFactory factory = PlayerFactory.getInstance();
		RealGrid g = new RealGrid(10,10,4);
		Player p1 = factory.buildBasic();
		g.addPlayer(p1);
		Player p2 = factory.buildBasic();
		p2.setPosition(2,3);
		g.addPlayer(p2);
		Player p3 = factory.buildBasic();
		p3.setPosition(1,9);
		g.addPlayer(p3);
		Player p4 = factory.buildBasic();
		p4.setPosition(7,7);
		g.addPlayer(p4);
		System.out.println("\033[H\033[2J");
		g.generateRandomGrid();
		while(true) {
			try {
				Thread.sleep(500);
				System.out.println(g);
			} catch (InterruptedException e) {}
		}
	}

}
