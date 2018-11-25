package modele;

public class PlayerGrid implements Grid {

    private final RealGrid model;
    private final Player client;
    private boolean alt_string =false;

    public PlayerGrid(RealGrid g, Player p) {
        this.model = g;
        this.client = p;
    }

    public boolean playerCanSee(Tile t) {
        return (t.isVisible() || (!(t.isVisible()) && (((Weapon)t).getOwner().equals(this.client))));
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
    public void setTileAt(Tile t) {
        model.setTileAt(t);
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

    public String toStringForThread() {
      StringBuilder res_thread = new StringBuilder(this.toString());
      int position = client.getX()+(client.getY()*(this.getModel().getWidth()+1));
      res_thread.replace(position,position+1,new FreeTile(-1,-1).toString());
      alt_string=!alt_string;
      return alt_string ? this.toString() : res_thread.toString();
    }
}