package graphics;

import modele.Game;
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
    public static Player playerToPlay = null;
    private boolean isShooting = false;
    private boolean isMoving = false;
    private boolean isPlanting = false;
    private SoundLoader sound;
    
    
    
    public GUI(){    
        this(new Game());
    }

    public GUI(Game game){
        com.sun.javafx.application.PlatformImpl.startup(()->{});       
        this.sound = new SoundLoader(0);       
        sound.getTrack().setOnEndOfMedia(() -> {
            if(game.getGrid().gameIsOver()){
                sound.getTrack().stop();
            }else{
                sound.getTrack().seek(sound.getTrack().getStartTime());
            }
        });
        
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
        
        System.out.println("INIT");
        int index = (game.getGrid().getTurnNumber() % game.getListPlayers().size());
        playerToPlay = game.getGrid().getPlayers()[index];
        playerToPlay.setAsTurn(true);
        System.out.println(game.getGrid().getPlayers().length);
        
        //Création de menu
        final JPopupMenu popup = new JPopupMenu();
        
        JMenuItem depItem = new JMenuItem("Déplacement", new ImageIcon("src/Images/moveIcon.png"));
        depItem.getAccessibleContext().setAccessibleDescription("Déplacer le personnage");
        depItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(playerToPlay.getEnergy() >= GameConfig.MOVE_COST){
                        playerToPlay.select();
                        isMoving = true;
                        game.stateChange();
                    }
                }           
        });        
        popup.add(depItem);  
        
        JMenuItem shieldItem = new JMenuItem("Activer bouclier", new ImageIcon("src/Images/shieldIcon.png"));
        shieldItem.getAccessibleContext().setAccessibleDescription("Activer le bouclier");
        shieldItem.addActionListener(new ActionListener(){           
            @Override
            public void actionPerformed(ActionEvent e) {
                Player p = playerToPlay;
                    if(!p.isShield_up() && p.getEnergy() >= GameConfig.SHIELD_COST){
                        p.enableShield();
                        if(p.getEnergy() == 0){
                            changeTurn();
                        }else{
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
                if(playerToPlay.getEnergy() >= GameConfig.FIRE_COST){
                    isShooting = true;                    
                }
            }
            
        });
        popup.add(shootItem);
        
        JMenuItem plantMine = new JMenuItem("Poser une mine", new ImageIcon("src/Images/mine.png"));
        plantMine.getAccessibleContext().setAccessibleDescription("Poser une mine");
        plantMine.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //playerToPlay.plant(new Mine(playerToPlay), game.getGrid().getTileAt(playerToPlay.getX(), playerToPlay.getY() -1));
                if(playerToPlay.getEnergy() >= GameConfig.PLANT_COST){
                    playerToPlay.enablePlant();
                    game.stateChange();
                }
            }
            
        });
        popup.add(plantMine);
        
        JMenuItem plantBomb = new JMenuItem("Poser une bombe", new ImageIcon("src/Images/bomb.png"));
        plantBomb.getAccessibleContext().setAccessibleDescription("Poser une bombe");
        plantBomb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //playerToPlay.plant(new Mine(playerToPlay), game.getGrid().getTileAt(playerToPlay.getX(), playerToPlay.getY() -1));
                if(playerToPlay.getEnergy() >= GameConfig.PLANT_COST){
                    playerToPlay.enablePlant();
                    playerToPlay.enablePlantingBomb();
                    game.stateChange();
                }
            }
            
        });
        popup.add(plantBomb);
        
        JMenuItem passTurn = new JMenuItem("Passer", new ImageIcon("src/Images/pass.png"));
        passTurn.getAccessibleContext().setAccessibleDescription("Passer son tour");
        passTurn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {   
                changeTurn();
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
//                if(game.getGrid().getTileAt(x, y) instanceof Player ){ //Test si il est le joueur qui doit jouer
//                    Player p = (Player) game.getGrid().getTileAt(x, y);
//                    if(p.getAsTurn()){
//                        //coordPlayer[0] = x;
//                        //coordPlayer[1] = y;                    
//
//                        if(!p.isSelected()){
//                            //p.select();
//                            showPopup(e);
//                            if(p.getEnergy() >= GameConfig.MOVE_COST){                            
//                                depItem.setText("Déplacement");
//                            }else{
//                                depItem.setText("D̶é̶p̶l̶a̶c̶e̶m̶e̶n̶t̶");
//                            }
//                            if(p.getEnergy() >= GameConfig.SHIELD_COST && !p.isShield_up()){                            
//                                shieldItem.setText("Activer bouclier");
//                            }else{
//                                shieldItem.setText("A̶c̶t̶i̶v̶e̶r̶ ̶b̶o̶u̶c̶l̶i̶e̶r̶");
//                            }
//                            if(p.getEnergy() >= GameConfig.FIRE_COST){ 
//                                shootItem.setText("Tirer");
//                            }else{
//                                shootItem.setText("T̶i̶r̶e̶r̶");
//                            }
//                        }else{
//                            p.unselect();
//                        }
//                    }
//                    game.stateChange();
//                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int popX = e.getX();
                int popY = e.getY();
                int x = e.getX() / 64;
                int y = e.getY() / 64;
                
                if(game.getGrid().getTileAt(x, y) instanceof Player ){ //Test si il est le joueur qui doit jouer
                    Player p = (Player) game.getGrid().getTileAt(x, y);
                    if(p.getAsTurn()){
                        //coordPlayer[0] = x;
                        //coordPlayer[1] = y;                    

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
                            if(p.getEnergy() >= GameConfig.PLANT_COST){
                                plantBomb.setText("Poser une bombe");
                                plantMine.setText("Poser une mine");
                            }else{
                                plantBomb.setText("P̶o̶s̶e̶r̶ ̶u̶n̶e̶ ̶b̶o̶m̶b̶e̶");
                                plantMine.setText("P̶o̶s̶e̶r̶ ̶u̶n̶e̶ ̶m̶i̶n̶e̶");
                            }
                        }else{
                            p.unselect();
                        }
                    }
                    game.stateChange();
                }
                //System.out.println("Etat du tir : "+isShooting);
                //if(coordPlayer[0] != null){
                //    Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);
                    Player p = playerToPlay;
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
                            if(p.possibleMoves().contains(d)){
                                //System.out.println("Le déplacement est possible");
                                p.move(d);
                                isMoving = false;
                                //coordPlayer[0] = null;
                                //coordPlayer[1] = null;
                                p.unselect();
                                if(p.getEnergy()==0){
                                    changeTurn();
                                }
                                game.stateChange();
                            }
                        }else if(isShooting){
                            Direction d = null;
                            //Player p = (Player) game.getGrid().getTileAt(coordPlayer[0], coordPlayer[1]);

                            int depX = x - p.getX();
                            int depY = y - p.getY();

                            d = block2dir(x,y);
//                            if(depX == 0 && depY == -1){
//                                d = Direction.z;
//                            }else if(depX == 0 && depY == 1){
//                                d = Direction.s;
//                            }else if(depX == -1 && depY == 0){
//                                d = Direction.q;
//                            }else if(depX == 1 && depY == 0){
//                                d = Direction.d;
//                            }
                            p.fire(d);
                            isShooting = false;
                            if(p.getEnergy()==0){
                                changeTurn();
                            }
                            game.stateChange();
                        }else if(p.isPlanting()){
                            int distance = (int) Math.sqrt(Math.pow(playerToPlay.getX() - x, 2) + Math.pow(playerToPlay.getY() - y, 2));
                            if(distance == 1){
                                if(playerToPlay.isPlantingBomb()){
                                    playerToPlay.plant(new Bomb(playerToPlay), game.getGrid().getTileAt(x,y));
                                }else{
                                    playerToPlay.plant(new Mine(playerToPlay), game.getGrid().getTileAt(x,y));
                                }
                            }
                            p.disablePlant();
                            p.disablePlantingBomb();
                            game.stateChange();
                            
                        }
                    }
                //}
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
    
    public void changeTurn(){
        //Le joueur actuel ne joues plus
        playerToPlay.setAsTurn(false);
        //On incrémente le tour
        
        game.getGrid().nextTurn();
        game.getGrid().nextPlayer();        
        //int index = (game.getGrid().getTurnNumber() % game.getListPlayers().size()) -1;
        //Dans les tests on a 2 jours donc : 
        int index = (game.getGrid().getTurnNumber() % 2) ;
        System.out.println("index= "+index);
        System.out.println(game.getGrid().getTurnNumber());
        playerToPlay = game.getGrid().getPlayers()[index];
        playerToPlay.setAsTurn(true);
        game.stateChange();
    }
    
    public Direction block2dir(int x, int y){
        int varX = playerToPlay.getX() - x;
        int varY = playerToPlay.getY() - y;
        
        if(varX == 0){
            if(varY<0){
                return Direction.s;
            }else if(varY > 0){
                return Direction.z;
            }
        }else{
            if(varX<0){
                return Direction.d;
            }else{
                return Direction.q;
            }
        }
        return null;
    }
}