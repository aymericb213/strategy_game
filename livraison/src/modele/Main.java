package modele;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		Grid g = new Grid(10,10,4);
		g.generateRandomGrid();
		System.out.println(g);
	}
}
