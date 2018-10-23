package modele;

/**
	* Processus de résolution du niveau lancé dans le mode IA battle ou en cas de demande de résolution.
	*/
public class PrintThread implements Runnable {

    private Grid g;
		private int i;
		private Player p;

    public PrintThread(Grid g, int i, Player p) {
      this.g = g;
			this.i = i;
			this.p = p;
    }

    @Override
    public void run() {
			while(true) {
				try {
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
					System.out.println("\nz,q,s,d : déplacer joueur    n : tour suivant     e : quitter");
					Thread.sleep(750);
				} catch (InterruptedException e) {
					break;
				}
			}
    }
}
