package graphics;

import modele.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class GUI extends JFrame{
    
    private View view;
    private Game game;    
    private Integer[] coordPlayer = new Integer[2];
    private boolean isShooting = false;
    private boolean isMoving = false;

    
    public GUI(){    
        this(new Game());
    }

    public GUI(Game game){
        this.game = game;
        this.view = new View(null,game);
        view.setEntities(game.getGrid().getGrid());
        setContentPane(view);
        setTitle("Shooter Game");
        setSize(832,854); //64*20;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Pour lire les entrées claviers
        setFocusable(true);
        requestFocus();
        
        //Création de menu
        final JPopupMenu popup = new JPopupMenu();
        
        JMenuItem depItem = new JMenuItem("Déplacement", new ImageIcon("src/Images/moveIcon.png"));
        depItem.getAccessibleContext().setAccessibleDescription("Déplacer le personnage");
        depItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coordPlayer[0] != null){
                    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    if(p.getEnergy() >= GameConfig.MOVE_COST){
                        p.select();
                        isMoving = true;
                        game.stateChange();
                    }
                }
            }            
        });        
        popup.add(depItem);  
        
        JMenuItem shieldItem = new JMenuItem("Activer bouclier", new ImageIcon("src/Images/shieldIcon.png"));
        shieldItem.getAccessibleContext().setAccessibleDescription("Activer le bouclier");
        shieldItem.addActionListener(new ActionListener(){           
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coordPlayer[0] != null){
                    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    if(!p.isShield_up() && p.getEnergy() >= GameConfig.SHIELD_COST){
                        p.enableShield();
                        game.stateChange();
                    }
                }
            }
            
        });
        popup.add(shieldItem);
        
        JMenuItem shootItem = new JMenuItem("Tirer", new ImageIcon("src/Images/target.png"));
        shootItem.getAccessibleContext().setAccessibleDescription("Tirer dans une direction");
        shootItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coordPlayer[0] != null){
                    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    if(p.getEnergy() >= GameConfig.FIRE_COST){
                        isShooting = true;
                        System.out.println("Is shooting est mis à true");
                    }
                }
            }
            
        });
        popup.add(shootItem);
        
        JMenuItem passTurn = new JMenuItem("Passer", new ImageIcon("src/Images/pass.png"));
        passTurn.getAccessibleContext().setAccessibleDescription("Passer son tour");
        passTurn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(coordPlayer[0] != null){
                    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    p.nextTurn();
                    game.stateChange();
                }
            }
            
        });
        popup.add(passTurn);
        
        
        //Je mets ça juste pour les tests, on verra ce qu'on en fait
        addKeyListener(new KeyListener(){            
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.z)){
                          p.move(Direction.z);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.q)){
                          p.move(Direction.q);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.s)){
                          p.move(Direction.s);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.d)){
                          p.move(Direction.d);
                          game.stateChange();
                        }
                    }
                }
            }
            
        });
        
        getContentPane().addMouseListener(new MouseListener(){
            public void showPopup(MouseEvent e){
                //if(e.isPopupTrigger())
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 64;
                int y = e.getY() / 64;                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / 64;
                int y = e.getY() / 64;
                System.out.println(x+" "+y);
                if(game.getGrid().getTileAt(x, y) instanceof Player){
                    Player p = (Player) game.getGrid().getTileAt(x, y);
                    coordPlayer[0] = x;
                    coordPlayer[1] = y;                    

                    if(!p.isSelected()){
                        //p.select();
                        showPopup(e);
                        if(p.getEnergy() >= GameConfig.MOVE_COST){                            
                            depItem.setText("Déplacement");
                        }else{
                            depItem.setText("D̶é̶p̶l̶a̶c̶e̶m̶e̶n̶t̶");
                        }
                        if(p.getEnergy() >= GameConfig.SHIELD_COST && !p.isShield_up()){                            
                            shieldItem.setText("Activer bouclier");
                        }else{
                            shieldItem.setText("A̶c̶t̶i̶v̶e̶r̶ ̶b̶o̶u̶c̶l̶i̶e̶r̶");
                        }
                        if(p.getEnergy() >= GameConfig.FIRE_COST){ 
                            shootItem.setText("Tirer");
                        }else{
                            shootItem.setText("T̶i̶r̶e̶r̶");
                        }
                    }else{
                        p.unselect();
                    }
                }
                game.stateChange();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / 64;
                int y = e.getY() / 64;
                System.out.println("Etat du tir : "+isShooting);
                if(coordPlayer[0] != null){
                    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    
                    if(p.getEnergy() > 0){
                        if(isMoving){
                            int depX = x - p.getX();
                            int depY = y - p.getY();

                            //System.out.println("Le joueur: "+p.getX()+", "+p.getY());
                            //System.out.println("La nouvelle case: "+x+", "+y);

                            Direction d = null;

                            if(depX == 0 && depY == -1){
                                d = Direction.z;
                            }else if(depX == 0 && depY == 1){
                                d = Direction.s;
                            }else if(depX == -1 && depY == 0){
                                d = Direction.q;
                            }else if(depX == 1 && depY == 0){
                                d = Direction.d;
                            }
                            System.out.println(d);
                            if(p.possibleMoves().contains(d)){
                                //System.out.println("Le déplacement est possible");
                                p.move(d);
                                isMoving = false;
                                coordPlayer[0] = null;
                                coordPlayer[1] = null;
                                p.unselect();
                                game.stateChange();
                            }
                        }else if(isShooting){
                            System.out.println("Bonjour le monde");
                            Direction d = null;
                            //Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);

                            int depX = x - p.getX();
                            int depY = y - p.getY();

                            if(depX == 0 && depY == -1){
                                d = Direction.z;
                            }else if(depX == 0 && depY == 1){
                                d = Direction.s;
                            }else if(depX == -1 && depY == 0){
                                d = Direction.q;
                            }else if(depX == 1 && depY == 0){
                                d = Direction.d;
                            }
                            System.out.println("Je m'appréte à tirer");
                            p.fire(d);
                            isShooting = false;
                            game.stateChange();
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }            
        });   
        setVisible(true);
    }

    public View getView(){
        return this.view;
    }

    public Game getGame(){
        return this.game;
    }
}