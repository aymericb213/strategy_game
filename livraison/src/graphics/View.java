package graphics;

import modele.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JPanel;

public class View extends JPanel implements ModelListener{
    private Tile[] entities;
    private final Game game;
    private final Dimension sizeImg;

    public View(Tile[] entities, Game game) {    
        this.entities = entities;
        this.game = game;
        this.sizeImg = new Dimension(35,43);
        game.addListener(this);
    }
    
    /*
    public void addEntity(Tile entity){
        entities.add(entity);
    }
    */

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < game.getTileMap().size(); i++){
            ArrayList<Tile> list = game.getTileMap().get(i);
            for(Tile t : list){
                int x = 64 * t.getX();
                int y = 64 * t.getY();
                g.drawImage(t.getImgRepr(), x , y, this);
            }
        }

        Set<Player> players = game.getListPlayers().keySet();
        for(Player p: players){
            BufferedImage img = p.getImgRepr();

            int baseX = (int)(( 64 - sizeImg.getWidth())/2);
            int baseY = (int)(( 64 - sizeImg.getHeight())/2);

            int x = 64 * p.getX() + baseX;
            int y = 64 * p.getY() + baseY;
            displayBullet(g, p);
            displayPlayer(g, p);
            
            //Draw shield
            if(p.isShield_up()){
                g.drawImage(ImagesLoader.shield, p.getX()*64, p.getY()*64, this);
            }
            
            drawLife(p,g);
            drawActionPoint(p,g);
            //g.drawImage(img,x ,y , this);

            ArrayList<Direction> possibleMoves = p.possibleMoves();

            if(p.getEnergy() == 0){
                g.setColor(new Color(255,0,0));

                g.drawRect(64 * (p.getX()), 64 * (p.getY()), 64, 64);
            }
            
            if(p.isSelected()){
                if(p.getEnergy() != 0){
                    g.setColor(new Color(0,255,0));
                    for(Direction d : possibleMoves){
                        //g.drawRect(64 * (p.getX()+d.x()), 64* (p.getY()+d.y()), 64, 64);
                        g.fillOval(64 * (p.getX()+d.x()) + 17, 64* (p.getY()+d.y()) +17 , 30, 30);
                    }
                }
            }
            
            /*
            ArrayList<ArrayList> viewableTiles = p.visibleTiles();
            System.out.print(viewableTiles);
            
            if(p.isSelected()){
                g.setColor(new Color(25,125,255));
                int counter=0;
                for(ArrayList t : viewableTiles){
                    System.out.print("nik"+counter+"\n"); counter++;
                    Integer tx = Integer.parseInt(t.get(0).toString());
                    Integer ty = Integer.parseInt(t.get(1).toString());
                    g.fillOval(64*tx+22, 64*ty+22, 20, 20);
                }
            }
            */
        }
        Grid grid = game.getGrid();
    }

    public void displayBullet(Graphics g, Player p){
        if(p.isShooting()){
            int x = p.getThreadShoot().getX() * 64;
            int y = p.getThreadShoot().getY() * 64;
            g.fillRect(x, y, 20, 20);
        }
    }

    public void displayPlayer(Graphics g, Player p){

        BufferedImage img = p.getImgRepr();

        int baseX = (int)(( 64 - sizeImg.getWidth())/2);
        int baseY = (int)(( 64 - sizeImg.getHeight())/2);

        int x = 64 * p.getX() + baseX;
        int y = 64 * p.getY() + baseY;


        // The required drawing location
        int drawLocationX = 300;
        int drawLocationY = 300;

        // Rotation information

        if(p.lastMove == Direction.z){
            g.drawImage(ImagesLoader.lookUp(img), x, y, this);
        }else if(p.lastMove == Direction.s){
            g.drawImage(ImagesLoader.lookDown(img), x, y, this);
        }else if(p.lastMove == Direction.q){
            g.drawImage(ImagesLoader.lookLeft(img), x, y, this);
        }else if(p.lastMove == Direction.d){
            g.drawImage(ImagesLoader.lookRight(img), x, y, this);
        }
        /*
        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
        //at.translate(getWidth() / 2, getHeight() / 2);
        at.translate(64 * p.getX() + 32, 63*p.getY() +32);

        // 3. do the actual rotation
        double secAngle = Math.toRadians(angle);
        at.rotate(secAngle);//Math.PI/4);

        // 2. just a scale because this image is big
        //at.scale(0.5, 0.5);

        // 1. translate the object so that you rotate it around the
        //    center (easier :))
        at.translate(-img.getWidth()/2, -img.getHeight()/2);

        // draw the image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, at, null);
        */

    }

    public void drawActionPoint(Player p, Graphics g){
        int nbAction = p.getEnergy();
        
        int x = 64 * (p.getX());
        int y = 64 * (p.getY()+1);
        
        g.setColor(new Color(0,255,255));
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(Integer.toString(nbAction), x+10, y-10);
        //g.fillOval(x+10, y-10, 5, 5);
        
    }
    
    public void drawLife(Player p, Graphics g){
        int life = p.getLife();

        int size = 64 - 20;
        int x = 64 * p.getX();
        int y = 64 * p.getY();

        double percentLife = life / (double)10;// * 100;
        int hpSize = (int) (size * percentLife);
        g.drawRect(x+10,y+2,size, 5);

        if(life >= 7){
            g.setColor(new Color(0,255,0));
        }else if(life >= 4){
            g.setColor(new Color(229,133,61));
        }else{
            g.setColor(new Color(255,0,0));
        }

        //g.setColor(new Color(0,255,0));
        g.fillRect(x+10+1,y+2+1,hpSize-2, 5-2);
    }

    public void setEntities(Tile[] l){
        this.entities = l;
    }

    @Override
    public void update(Object source) {
        this.repaint();
    }
}
