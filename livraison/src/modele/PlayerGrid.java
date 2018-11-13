package modele;

public class PlayerGrid implements Grid {
    
    private final RealGrid model;
    private final Player client;

    public PlayerGrid(RealGrid g, Player p) {
        this.model = g;	
        this.client = p;	
    }

    public boolean playerCanSee(Tile t) {
        return ((!(t instanceof Bomb || t instanceof Mine)) || ((t instanceof Bomb || t instanceof Mine) && (((Mine)t).getOwner().equals(this.client))));	
    }

    @Override
    public Tile getTileAt(int x, int y) {
        Tile test = model.getTileAt(x,y);	
        if (playerCanSee(test)) {	
            return test;	
        } else {	
            return new FreeTile(x,y);	
        }	
    }

    @Override
    public void setTileAt(int x, int y, Tile t) {
        model.setTileAt(x,y,t);	
    }


    public void addBomb(Bomb b) {
        model.addBomb(b);	
    }    

    public RealGrid getModel() {
        return this.model;	
    }    
	
    @Override    
    public String toString() {
        StringBuilder res = new StringBuilder();    
        for (int i=0 ; i<model.getGrid().length ; i++) {      
            if (i>0 && i%model.getWidth() == 0) {        
                res.append("\n");      
            }
            if (playerCanSee(model.getGrid()[i])) {	
                res.append(model.getGrid()[i]);		
            } else {	
                res.append(new FreeTile(-1,-1).toString());//affichage de FreeTile si la case est une bombe ou une mine adverse		
            }    
        }    
        return res.toString();	
    }
}
