package modele;

import java.util.*;

public class Main {

  public static void main(String[] args) {

    Scanner sc= new Scanner(System.in);
    PlayerFactory factory = PlayerFactory.getInstance();
		RealGrid g = new RealGrid();
		if (args.length>0) {
			if (args[0].equals("-p0") || args[0].equals("-p1")) {
				System.out.println("\033[H\033[2J");
				System.out.println("Dimensions de la grille :");
				String[] dim = sc.nextLine().split("x");
				System.out.println("Nombre de joueurs :");
				String nb_players = sc.nextLine();
				g = new RealGrid(Integer.parseInt(dim[0]),Integer.parseInt(dim[1]),Integer.parseInt(nb_players));
				if (args[0].equals("-p1")) {
					for (int i=1 ; i<=Integer.parseInt(nb_players) ; i++) {
						System.out.println("Classe du joueur " + i);
						switch (sc.nextLine()) {
							case "basic" : g.addPlayer(factory.buildBasic(g));
							case "tank" : g.addPlayer(factory.buildTank(g));
						}
					}
				}
			}
		} else {
    g = new RealGrid(10,10,4);
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
		}
		g.createGrid();
    end :
    while(!(g.gameIsOver())) {
      for (int i=0 ; i<g.getPlayers().length ; i++) {
        Player p = g.getPlayers()[i];
        System.out.println("\033[H\033[2J");
        System.err.println("================ STRATEGY GAME =================\n");
        System.out.println("Tour " + g.getTurnNumber());
        System.out.println("Player " + (i+1) + "\n");
				System.out.println(g + "\n");//vue globale
      //  System.out.println(p.getView() + "\n");//vues joueur
        System.out.println("# : mur");
        System.out.println("; : mine");
        System.out.println("3 : bombe");
        System.out.println(". : bonus");
        System.out.println("@ : joueur (€ si bouclier actif)");
        System.out.println("\n" + p.printStats());
        System.out.println("\nz,q,s,d : déplacer joueur\nm : poser mine     b : poser bombe      t : tirer     a : activer bouclier\nn : tour suivant     e : quitter");
        /*if (i!=0) {//jeu automatique
          p.act();
          continue;
        }*/
        String input=sc.nextLine();
        if (input.equals("E") || input.equals("e")) {
          break end;
        }
        if (input.equals("N") || input.equals("n")) {
          p.act();
        }
				if (input.equals("A") || input.equals("a")) {
					p.enableShield();
				}
        if (input.equals("M") || input.equals("m") || input.equals("B") || input.equals("b")) {
          ArrayList<FreeTile> sites = g.getNeighbouringFreeTiles(p);
          String site_list = "";
          for (FreeTile f : sites) {
              site_list+=f.printCoords()+" ";
          }
          System.out.println(site_list+"\nChoisissez un emplacement :");
          if (input.equals("M") || input.equals("m")) {
              p.plantMine(sites.get(Integer.parseInt(sc.nextLine())));
          }
          if (input.equals("B") || input.equals("b")) {
              p.plantBomb(sites.get(Integer.parseInt(sc.nextLine())));
          }
        }
        if (input.equals("T") || input.equals("t")) {
          System.out.println("\nChoisissez une direction :");
          switch (sc.nextLine()) {
						case "Z" : case "z" : p.fire(Direction.z);
						case "Q" : case "q" : p.fire(Direction.q);
            case "S" : case "s" : p.fire(Direction.s);
            case "D" : case "d" : p.fire(Direction.d);
            default : System.out.println("Non");
          }
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
