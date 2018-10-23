package modele;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
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
		g.generateRandomGrid();
		end :
		while(!(g.gameIsOver())) {
			for (int i=0 ; i<g.getPlayers().length ; i++) {
				Player p = g.getPlayers()[i];
				Runnable thread = new PrintThread(g,i,p);
				Thread t = new Thread(thread);
				t.start();
				String input=sc.nextLine();
				if (input.equals("E") || input.equals("e")) {
					t.interrupt();
					break end;
				}
				if (input.equals("Z") || input.equals("z")) {
					t.interrupt();
					p.move(Direction.z);
				}
				else if (input.equals("Q") || input.equals("q")) {
					t.interrupt();
					p.move(Direction.q);
				}
				else if (input.equals("D") || input.equals("d")) {
					t.interrupt();
					p.move(Direction.d);
				}
				else if (input.equals("S") || input.equals("s")) {
					t.interrupt();
					p.move(Direction.s);
				}
				else {
					t.interrupt();
					System.out.println("Entrez une commande valide.");
				}
			}
			g.nextTurn();
		}
	}
}
