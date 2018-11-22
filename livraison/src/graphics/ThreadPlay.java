/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Bomb;
import modele.Direction;
import modele.FreeTile;
import modele.Mine;
import modele.Player;

/**
 *
 * @author quentindeme
 */
public class ThreadPlay extends Thread{

    private Game game;
    public static int counterInstance = 0;

    public ThreadPlay(Game game){
        this.game = game;
    }

    @Override
    public void run(){
        Player p = game.getGrid().getPlayerToPlay();
        String action = "";

        while(!action.contains("p")){
            displayInstructions(p);
            //BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            Scanner r = new Scanner(System.in);
            System.out.println("On demande une entrée");
            action = r.nextLine();

            System.out.println("Et on traite l'info: "+action);
            treatInfo(action, p);
        }
        System.out.println("Fin de tour");
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(ThreadPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        game.getGrid().nextPlayer();
        game.stateChange();
    }

    public void displayInstructions(Player p){
        System.out.println("Directions possibles: ");
        System.out.println(p.possibleMoves());

        System.out.println("Vous pouvez consulter les commandes en tapant: man");
        System.out.println("Saisissez votre actions : ");

    }

    public void treatInfo(String input, Player p){
        Scanner sc = new Scanner(System.in);
        switch (input) {
            case "man":
                displayMan(sc, p);
                break;
              case "E"://quitter
              case "e":
                break;
              case "P"://fin de tour même avec AP>0
              case "p":
                break;
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
                ArrayList<FreeTile> sites = game.getGrid().getNeighbouringFreeTiles(p,1);
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
              }
        game.stateChange();
    }

    public void displayMan(Scanner r, Player p){
        String action = "";
                System.out.println("# : mur");
                System.out.println("; : mine");
                System.out.println("3 : bombe");
                System.out.println(". : bonus");
                System.out.println("@ : joueur (€ si bouclier actif)");
                System.out.println("\n" + p.printStats());
                System.out.println(p.printControls());


                System.out.println("Appuyez sur entrer pour quitter.");
                action = r.nextLine();
                while(!action.contains("q")){
                    System.out.println("Appuyez sur entrer pour quitter.");
                    action = r.nextLine();
                }
                System.out.println(game.getGrid().getPlayerToPlay().getView());
            }
}
