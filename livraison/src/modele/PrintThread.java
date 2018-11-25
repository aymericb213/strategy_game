package modele;

/**
* Processus de résolution du niveau lancé dans le mode IA battle ou en cas de demande de résolution.
*/
public class PrintThread implements Runnable {
    private final RealGrid g;
    private final Player p;

    public PrintThread(RealGrid g, Player p) {
        this.g = g;
        this.p = p;
    }

    @Override
    public synchronized void run() {
        while(true) {
            try {
                System.out.println("\033[H\033[2J");
                System.out.println("Tour " + g.getTurnNumber());
                System.out.println(p.getName() + "\n");
                //System.out.println(g + "\n");//vue globale
                System.out.println(p.getView().toStringForThread() + "\n");//vues joueur
                System.out.println("# : mur");
                System.out.println("; : mine");
                System.out.println("3 : bombe (délai avant détonation)");
                System.out.println(". : bonus");
                System.out.println("@ : joueur (€ si bouclier actif)");
                System.out.println("\n" + p.printStats());
                System.out.println(p.printControls());
                Thread.sleep(800);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
