/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quentindeme
 */
public class Game extends AbstractListenableModel {

    private Grid grid;
    protected BufferedImage tile_image;
    protected BufferedImage player_image;

    public Game(){
        this(null);
    }

    public Game(Grid grid){
        this.grid = grid;
    }

		public Grid getGrid() {
			return this.grid;
		}

		public BufferedImage getTileImg(){
				return this.tile_image;
		}

		public void setTileImg(BufferedImage imageRepr) {
				this.tile_image = imageRepr;
		}

		public BufferedImage getPlayerImg(){
				return this.player_image;
		}

		public void setPlayerImg(BufferedImage imageRepr) {
				this.player_image = imageRepr;
		}

		public void paint(Graphics g){
	//			g.drawImage(imageRepr, super.x, super.y, null);
		}

}
