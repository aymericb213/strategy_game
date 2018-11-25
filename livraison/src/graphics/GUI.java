package graphics;

import modele.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI extends JFrame{

    private View view;
    private Game game;
    private Integer[] coordPlayer = new Integer[2];
    public  Player playerToPlay = null;
    private boolean isShooting = false;
    private boolean isMoving = false;
    private boolean isPlanting = false;
    private Player player;
    private SoundLoader sound;

    public GUI(Player p){
        this(new Game(),p);
    }

    public GUI(Game game, Player p){
        this.player = p;
        this.game = game;
        playerToPlay = game.getGrid().getPlayerToPlay();
        System.out.println("JOUEUR ==> "+playerToPlay);
        this.view = new View(null,game, player, this);
        view.setEntities(game.getGrid().getGrid());
        setContentPane(view);
        setTitle("Shooter Game ("+player.getName()+")");
        setSize(832,832); //64*20;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Pour lire les entrées claviers
        setFocusable(true);
        requestFocus();
        
        setTitle("Shooter Game ("+player.getName()+"). Tour de: "+playerToPlay.getName());

        //Création de menu
        final JPopupMenu popup = new JPopupMenu();

        JMenuItem depItem = new JMenuItem("Déplacement"  + GameConfig.MOVE_COST + " AP", new ImageIcon("src/Images/moveIcon.png"));
        depItem.getAccessibleContext().setAccessibleDescription("Déplacer le personnage");
        depItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getEnergy() >= GameConfig.MOVE_COST){
                        player.select();
                        isMoving = true;
                        game.stateChange();
                    }
                }
        });
        popup.add(depItem);

        JMenuItem shieldItem = new JMenuItem("Activer bouclier"  + GameConfig.SHIELD_COST + " AP", new ImageIcon("src/Images/shieldIcon.png"));
        shieldItem.getAccessibleContext().setAccessibleDescription("Activer le bouclier");
        shieldItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Player p = player;
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

        JMenuItem shootItem = new JMenuItem("Tirer"  + GameConfig.FIRE_COST + " AP", new ImageIcon("src/Images/target.png"));
        shootItem.getAccessibleContext().setAccessibleDescription("Tirer dans une direction");
        shootItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getEnergy() >= GameConfig.FIRE_COST){
                    isShooting = true;
                }
            }
        });
        popup.add(shootItem);

        JMenuItem plantMine = new JMenuItem("Poser une mine"  + GameConfig.PLANT_COST + " AP", new ImageIcon("src/Images/mine.png"));
        plantMine.getAccessibleContext().setAccessibleDescription("Poser une mine");
        plantMine.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getEnergy() >= GameConfig.PLANT_COST){
                    player.enablePlant();
                    game.stateChange();
                }
            }
        });
        popup.add(plantMine);

        JMenuItem plantBomb = new JMenuItem("Poser une bombe"  + GameConfig.PLANT_COST + " AP", new ImageIcon("src/Images/bomb.png"));
        plantBomb.getAccessibleContext().setAccessibleDescription("Poser une bombe");
        plantBomb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getEnergy() >= GameConfig.PLANT_COST){
                    player.enablePlant();
                    player.enablePlantingBomb();
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
                if(player.getEnergy() >= GameConfig.MOVE_COST && player.getName().equals(playerToPlay.getName())){
                    if(e.getKeyCode() == KeyEvent.VK_Z){
                        if(player.possibleMoves().contains(Direction.z)){
                          player.move(Direction.z);
                          game.stateChange();
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_Q){
                        if(player.possibleMoves().contains(Direction.q)){
                          player.move(Direction.q);
                          game.stateChange();
                        }

                    }
                    if(e.getKeyCode() == KeyEvent.VK_S){
                            if(player.possibleMoves().contains(Direction.s)){
                              player.move(Direction.s);
                              game.stateChange();
                            }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_D){
                        if(player.possibleMoves().contains(Direction.d)){
                          player.move(Direction.d);
                          game.stateChange();
                        }
                    }
                }
                if(player.getEnergy() == 0){
                    changeTurn();
                }
            }
        });

        getContentPane().addMouseListener(new MouseListener(){
            public void showPopup(MouseEvent e){
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
                playerToPlay = game.getGrid().getPlayerToPlay();
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
                        if(!p.isSelected() && p.getName().equals(player.getName()) && !game.getGrid().gameIsOver()){
                            showPopup(e);
                            if(p.getEnergy() >= GameConfig.MOVE_COST){
                                depItem.setText("Déplacement: "  + GameConfig.MOVE_COST + " AP");
                            }else{
                                depItem.setText("D̶é̶p̶l̶a̶c̶e̶m̶e̶n̶t̶: " + GameConfig.MOVE_COST + " AP");
                            }
                            if(p.getEnergy() >= GameConfig.SHIELD_COST && !p.isShield_up()){
                                shieldItem.setText("Activer bouclier: " + GameConfig.SHIELD_COST + " AP");
                            }else{
                                shieldItem.setText("A̶c̶t̶i̶v̶e̶r̶ ̶b̶o̶u̶c̶l̶i̶e̶r̶: " + GameConfig.SHIELD_COST + " AP");
                            }
                            if(p.getEnergy() >= GameConfig.FIRE_COST){
                                shootItem.setText("Tirer: " + GameConfig.FIRE_COST + " AP");
                            }else{
                                shootItem.setText("T̶i̶r̶e̶r̶: "  + GameConfig.FIRE_COST + " AP");
                            }
                            if(p.getEnergy() >= GameConfig.PLANT_COST){
                                plantBomb.setText("Poser une bombe: " + GameConfig.PLANT_COST + " AP");
                                plantMine.setText("Poser une mine: " + GameConfig.PLANT_COST + " AP");
                            }else{
                                plantBomb.setText("P̶o̶s̶e̶r̶ ̶u̶n̶e̶ ̶b̶o̶m̶b̶e̶: "  + GameConfig.PLANT_COST + " AP");
                                plantMine.setText("P̶o̶s̶e̶r̶ ̶u̶n̶e̶ ̶m̶i̶n̶e̶: " + GameConfig.PLANT_COST + " AP");
                            }
                        }else{
                            p.unselect();
                        }
                    }
                    game.stateChange();
                }

                Player p = playerToPlay;
                if(p.getEnergy() > 0){
                    if(isMoving){
                        int depX = x - p.getX();
                        int depY = y - p.getY();

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
                            if (game.getGrid().getTileAt(p.getX()+d.x(),p.getY()+d.y()) instanceof Mine) {                                
                                sound = new SoundLoader(1);
                            }

                            p.move(d);
                            isMoving = false;
                            p.unselect();
                            if(p.getEnergy()==0){
                                changeTurn();
                            }
                            game.stateChange();
                        }
                        isMoving = false;
                        p.unselect();
                        game.stateChange();
                    }else if(isShooting){
                        Direction d = null;
                        int depX = x - p.getX();
                        int depY = y - p.getY();
                        d = block2dir(x,y);

                        if(depX != p.getX() && depY != p.getY()){
                            p.fire(d);
                            sound = new SoundLoader(3);
                        }
                        
                        isShooting = false;
                        if(p.getEnergy()==0){
                            changeTurn();
                        }
                        game.stateChange();

                    }else if(p.isPlanting() && game.getGrid().getTileAt(x,y) instanceof FreeTile){
                        int distance = (int) Math.sqrt(Math.pow(playerToPlay.getX() - x, 2) + Math.pow(playerToPlay.getY() - y, 2));
                        if(distance == 1 ){
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
                    damageSound();
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
        int difX = 832 + (832 - this.getContentPane().getWidth());
        int difY = 832 + (832 - this.getContentPane().getHeight());
        this.setSize(difX, difY);
    }

    public View getView(){
        return this.view;
    }

    public Game getGame(){
        return this.game;
    }

    public void changeTurn(){
        playerToPlay.setAsTurn(false);
        playerToPlay = game.getGrid().nextPlayer();
        playerToPlay.setAsTurn(true);
        setTitle("Shooter Game ("+player.getName()+"). Tour de: "+playerToPlay.getName());

        if(game.getGrid().hearExplosion()){            
            sound = new SoundLoader(1);
            damageSound();
            game.getGrid().endExplosion();
        }
        game.stateChange();
    }
    
    public void damageSound(){
        if(game.getGrid().checkDamage()){
            sound = new SoundLoader(2);
            game.getGrid().checkDamage();
        }
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
