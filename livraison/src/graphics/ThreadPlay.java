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
        BufferedReader r = null;

        while(!action.contains("p")){
            displayInstructions(p);
            //BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            r = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("On demande une entrée");
            try {
                action = r.readLine();
            } catch (IOException ex) {

            }

            System.out.println("Et on traite l'info: "+action);
            try {
                treatInfo(action, p, r);
            } catch (IOException ex) {
                Logger.getLogger(ThreadPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Fin de tour");
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(ThreadPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        game.getGrid().nextPlayer();
        try {
            r.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        game.stateChange();
    }

    public void displayInstructions(Player p){
        System.out.println("Directions possibles: ");
        System.out.println(p.possibleMoves());

        System.out.println("Vous pouvez consulter les commandes en tapant: man");
        System.out.println("Saisissez votre actions : ");

    }

    public void treatInfo(String input, Player p, BufferedReader r) throws IOException{
        switch (input) {
            case "man":
                displayMan(r, p);
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
                  p.plant(new Mine(p), sites.get(Integer.parseInt(r.readLine())));
                }
                if (input.equals("B") || input.equals("b")) {
                  p.plant(new Bomb(p), sites.get(Integer.parseInt(r.readLine())));
                }
                break;
              case "T"://tir
              case "t":
                System.out.println("\nChoisissez une direction (z,q,s,d):");
                switch (r.readLine()) {
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

    public void displayMan(BufferedReader r, Player p) throws IOException{
        String action = "";
                System.out.println("# : mur");
                System.out.println("; : mine");
                System.out.println("3 : bombe");
                System.out.println(". : bonus");
                System.out.println("@ : joueur (€ si bouclier actif)");
                System.out.println("\n" + p.printStats());
                System.out.println(p.printControls());


                System.out.println("Appuyez sur q puis entrer pour quitter.");
                action = r.readLine();
                while(!action.equals("q")){
                    System.out.println("Appuyez sur q puis entrer pour quitter.");
                    action = r.readLine();
                }
                System.out.println(game.getGrid().getPlayerToPlay().getView());
            }
}