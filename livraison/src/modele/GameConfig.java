package modele;

import java.util.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;

public final class GameConfig {

	public static int PLAYER_BASE_HP;
	public static int PLAYER_BASE_AP;
	public static int PLAYER_FOV;
	public static int MOVE_COST;
	public static int SHIELD_COST;
	public static int BOMB_BASE_COUNT;
	public static int BOMB_DAMAGE;
	public static int BOMB_DELAY;
	public static int BOMB_VISIBILITY;
	public static int BOMB_PLANT_COST;
	public static int MINE_BASE_COUNT;
	public static int MINE_DAMAGE;
	public static int MINE_VISIBILITY;
	public static int MINE_PLANT_COST;
	public static int RIFLE_BASE_AMMO;
	public static int RIFLE_DAMAGE;
	public static int RIFLE_RANGE;
	public static int RIFLE_FIRE_COST;

	public GameConfig() {
		this.assignParameters();
	}

	public void assignParameters() {
		ArrayList<Integer> parameters = new ArrayList<>();
		try {
			Path file_path = FileSystems.getDefault().getPath("config.txt");
			List<String> d = Files.readAllLines(file_path, StandardCharsets.UTF_8);
			for (String line : d) {
				String[] s = line.split("=");
				parameters.add(Integer.parseInt(s[1]));
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		this.PLAYER_BASE_HP=parameters.get(0);
		this.PLAYER_BASE_AP=parameters.get(1);
		this.PLAYER_FOV=parameters.get(2);
		this.MOVE_COST=parameters.get(3);
		this.SHIELD_COST=parameters.get(4);
		this.BOMB_BASE_COUNT=parameters.get(5);
		this.BOMB_DAMAGE=parameters.get(6);
		this.BOMB_DELAY=parameters.get(7);
		this.BOMB_VISIBILITY=parameters.get(8);
		this.BOMB_PLANT_COST=parameters.get(9);
		this.MINE_BASE_COUNT=parameters.get(10);
		this.MINE_DAMAGE=parameters.get(11);
		this.MINE_VISIBILITY=parameters.get(12);
		this.MINE_PLANT_COST=parameters.get(13);
		this.RIFLE_BASE_AMMO=parameters.get(14);
		this.RIFLE_DAMAGE=parameters.get(15);
		this.RIFLE_RANGE=parameters.get(16);
		this.RIFLE_FIRE_COST=parameters.get(17);
	}
}
