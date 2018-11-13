package modele;

/**
* Processus de résolution du niveau lancé dans le mode IA battle ou en cas de demande de résolution.
*/
public class PrintThread implements Runnable {
    private final RealGrid g;
    private final int i;
    private final Player p;
    
    public PrintThread(RealGrid g, int i, Player p) {    
        this.g = g;	
        this.i = i;	
        this.p = p;    
    }
    
    @Override    
    public void run() {
        while(true) {	
            try {	
                Thread.sleep(750);		
            } catch (InterruptedException e) {	
                break;		
            }	
        }    
    }
}
