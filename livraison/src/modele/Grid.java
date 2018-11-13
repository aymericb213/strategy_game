package modele;

public interface Grid {
    public Tile getTileAt(int x, int y);

    public void setTileAt(int x, int y, Tile t);

    @Override
    public String toString();
}
