package modele;

import java.util.*;

public class Main {


	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		PlayerFactory factory = PlayerFactory.getInstance();
		RealGrid g = new RealGrid(10,10,4);
		g.generateRandomGrid();
		Player p1 = factory.buildBasic(g);
		p1.setPosition(4,5);
		g.addPlayer(p1);
		Player p2 = factory.buildBasic(g);
		p2.setPosition(5,5);
		g.addPlayer(p2);
		Player p3 = factory.buildBasic(g);
		p3.setPosition(6,6);
		g.addPlayer(p3);
		Player p4 = factory.buildBasic(g);
		p4.setPosition(7,7);
		g.addPlayer(p4);
		end :
		while(!(g.gameIsOver())) {
			for (int i=0 ; i<g.getPlayers().length ; i++) {
				Player p = g.getPlayers()[i];
				System.out.println("\033[H\033[2J");
				System.out.println("================ STRATEGY GAME =================\n");
				System.out.println("Tour " + g.getTurnNumber());
				System.out.println("Player " + (i+1) + "\n");
				System.out.println(g + "\n");
				System.out.println("# : mur");
				System.out.println("; : mine");
				System.out.println("! : bombe");
				System.out.println(". : bonus");
				System.out.println("@ : joueur");
				System.out.println("\n" + p.printStats());
				System.out.println("\nz,q,s,d : déplacer joueur    m : poser mine     n : tour suivant     e : quitter");
				if (i!=0) {
					p.act();
					continue;
				}
				String input=sc.nextLine();
				if (input.equals("E") || input.equals("e")) {
					break end;
				}
				if (input.equals("N") || input.equals("n")) {
					p.act();
				}
				if (input.equals("M") || input.equals("m")) {
					ArrayList<FreeTile> sites = g.getNeighbouringFreeTiles(p);
					String site_list = "";
					for (FreeTile f : sites) {
						site_list+=f.printCoords()+" ";
					}
					System.out.println(site_list+"\nChoisissez un emplacement :");
					p.plantMine(sites.get(Integer.parseInt(sc.nextLine())));
				}
				if (input.equals("Z") || input.equals("z")) {
					p.move(Direction.z);
				}
				else if (input.equals("Q") || input.equals("q")) {
					p.move(Direction.q);
				}
				else if (input.equals("D") || input.equals("d")) {
					p.move(Direction.d);
				}
				else if (input.equals("S") || input.equals("s")) {
					p.move(Direction.s);
				}
				else {
					System.out.println("Entrez une commande valide.");
				}
			}
			g.nextTurn();
		}
	}
}
