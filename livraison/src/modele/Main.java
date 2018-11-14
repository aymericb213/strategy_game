package modele;

import java.util.*;
import java.io.*;

public class Main {
    
    public static void main(String[] args) {
        
        new GameConfig();      
        Scanner sc= new Scanner(System.in);      
        PlayerFactory factory = PlayerFactory.getInstance();      
        RealGrid g = new RealGrid();
     
        if (args.length>0) {        
            if (args[0].equals("-p0") || args[0].equals("-p1")) {//p0 config simple, p1 config avancée            
                System.out.println("\033[H\033[2J");              
                System.out.println("Dimensions de la grille (nxm) :");              
                String[] dim = sc.nextLine().split("x");              
                System.out.println("Nombre de joueurs :");              
                String nb_players = sc.nextLine();              
                g = new RealGrid(Integer.parseInt(dim[0]),Integer.parseInt(dim[1]),Integer.parseInt(nb_players));              
                if (args[0].equals("-p1")) {//classe de chaque joueur                
                    for (int i=1 ; i<=Integer.parseInt(nb_players) ; i++) {                    
                        System.out.println("Classe du joueur " + i);   
                        
                        switch (sc.nextLine()) {                        
                            case "basic" : 
                                g.addPlayer(factory.buildBasic(g)); 
                                break;   
                                
                            case "tank" : 
                                g.addPlayer(factory.buildTank(g)); 
                                break;
                          
                            default : 
                                System.out.println(PlayerFactory.nb_instances);                      
                        }                  
                    }              
                } else {//p0 : tous les joueurs sont basic                
                    for (int i=1 ; i<=Integer.parseInt(nb_players) ; i++) {                    
                        g.addPlayer(factory.buildBasic(g));                  
                    }             
                }          
            }
            
        } else {//setup standard        
            g = new RealGrid(10,10,4);          
            for (int i=1 ; i<=4 ; i++) {            
                g.addPlayer(factory.buildBasic(g));          
            }      
        }      
        g.createGrid();

      
        end :      
        while(!(g.gameIsOver())) {        
            next :         
            for (int i=0 ; i<g.getPlayers().length ; i++) {            
                Player p = g.getPlayers()[i];              
                while (p.getEnergy()>0) {                
                    System.out.println("\033[H\033[2J");                 
                    System.out.println("================ STRATEGY GAME =================\n");                 
                    System.out.println("Tour " + g.getTurnNumber());                 
                    System.out.println("Player " + (i+1) + "\n");                  
                    System.out.println(g + "\n");//vue globale                  
                    //System.out.println(p.getView() + "\n");//vues joueur              
                    System.out.println("# : mur");                  
                    System.out.println("; : mine");                  
                    System.out.println("3 : bombe");                  
                    System.out.println(". : bonus");                 
                    System.out.println("@ : joueur (€ si bouclier actif)");                  
                    System.out.println("\n" + p.printStats());                  
                    System.out.println("\nz,q,s,d : déplacer joueur\nm : poser mine     b : poser bombe      t : tirer     a : activer bouclier\nn : tour auto     p : fin de tour     e : quitter");									
                    p.act();
                    
                    /*String input=sc.nextLine();
                    switch (input) {
                    case "E"://quitter
                    case "e":
                    break end;
                    case "P"://fin de tour même avec AP>0
                    case "p":
                    continue next;
                    case "N"://action de l'IA
                    case "n":
                    p.act();
                    break;
                    case "A"://bouclier
                    case "a":
                    p.enableShield();
                    break;
                    case "M"://posage d'explosif
                    case "m":
                    case "B":
                    case "b":
                    ArrayList<FreeTile> sites = g.getNeighbouringFreeTiles(p);
                    String site_list = "";
                    for (FreeTile f : sites) {
                    site_list+=f.printCoords()+" ";
                    }
                    System.out.println(site_list+"\nChoisissez un emplacement :");
                    if (input.equals("M") || input.equals("m")) {
                    p.plant(new Mine(p), sites.get(Integer.parseInt(sc.nextLine())));
                    }
                    if (input.equals("B") || input.equals("b")) {
                    p.plant(new Bomb(p), sites.get(Integer.parseInt(sc.nextLine())));
                    }
                    break;
                    case "T"://tir
                    case "t":
                    System.out.println("\nChoisissez une direction (z,q,s,d):");
                    switch (sc.nextLine()) {
                    case "Z" :
                    case "z" :
                    p.fire(Direction.z);
                    break;
                    case "Q" :
                    case "q" :
                    p.fire(Direction.q);
                    break;
                    case "S" :
                    case "s" :
                    p.fire(Direction.s);
                    break;
                    case "D" :
                    case "d" :
                    p.fire(Direction.d);
                    break;
                    default :
                    System.out.println("Non");
                    break;
                    }
                    break;
                    case "Z"://mouvements
                    case "z":
                    p.move(Direction.z);
                    break;
                    case "Q":
                    case "q":
                    p.move(Direction.q);
                    break;
                    case "D":
                    case "d":
                    p.move(Direction.d);
                    break;
                    case "S":
                    case "s":
                    p.move(Direction.s);
                    break;
                    default:
                    System.out.println("Entrez une commande valide.");
                    break;
                    }*/ //jeu automatique									
                }          
            }         
            g.nextTurn();      
        }  
    }
 
    public String executeCommand(String command) {    
        StringBuilder output = new StringBuilder();      
        Process p;      
        try {        
            p = Runtime.getRuntime().exec(command);          
            p.waitFor();          
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));          
            String line = "";         
            while ((line = reader.readLine())!= null) {          
                output.append(line + "\n");          
            }      
        } catch (IOException | InterruptedException e) {      
            e.printStackTrace();      
        }      
        return output.toString(); 
    }
}
