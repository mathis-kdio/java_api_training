package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Affichage {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public void showGrid(List<ArrayList<String>> grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++)
            System.out.print(this.alphabetCoo[i] + " ");
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < 10; j++) {
                if (grid.get(i).get(j).equals("hit") || grid.get(i).get(j).equals("sunk"))
                    System.out.print("X ");
                else
                    System.out.print("* ");
            }
            System.out.print("\n");
        }
    }

    public void resultAttack(String attackResult) {
        if (attackResult.equals("hit"))
            System.out.println("Le tire a réussi");
        else if (attackResult.equals("sunk"))
            System.out.println("Le bateau est coulé");
        else
            System.out.println("Le tire a manqué");
    }

}
