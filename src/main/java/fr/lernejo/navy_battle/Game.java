package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public final ArrayList<Boat> boatList;
    private final Boat.BoatType[] availableBoats;

    public Game(ArrayList<Boat> boatList, Boat.BoatType[] availableBoats) {
        this.boatList = boatList;
        this.availableBoats = availableBoats;
    }

    public boolean gameFinish() {
        int nbBoatAlive = this.availableBoats.length;
        for (int i = 0; i < this.boatList.size(); i++) {
            if(!this.boatList.get(i).isBoatAlive()) {
                nbBoatAlive--;
            }
        }
        if (nbBoatAlive == 0)
            return true;
        else
            return false;
    }

    public boolean isBoatOnPosition(String coo) {
        List<Integer> cell = new ArrayList<>();
        cell.add((int) coo.charAt(1));
        cell.add(List.of(this.alphabetCoo).indexOf(coo.charAt(0)));

        for (int i = 0; i < this.boatList.size(); i++) {
            ArrayList<List<Integer>> boatPositions = this.boatList.get(i).boatPositions();
            for (int j = 0; j < boatPositions.size(); j++) {
                if (boatPositions.get(j) == cell) {
                    boatList.get(i).setLife(1);
                    return true;
                }
            }
        }
        return false;
    }

    public List<ArrayList<String>> addAttackOnGrid(List<ArrayList<String>> adversaryGrid, String attackResult, String coo) {
        int row = coo.charAt(1)-'0';
        int col = List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0)));
        adversaryGrid.get(row).set(col, attackResult);
        return adversaryGrid;
    }

    public void showGrid(List<ArrayList<String>> grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print((i+1) + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(this.alphabetCoo[i] + " ");
            for (int j = 0; j < 10; j++) {
                if (grid.get(i).get(j).equals("hit")) {
                    System.out.print("X ");
                }
                else {
                    System.out.print("* ");
                }
            }
            System.out.print("\n");
        }
    }
}
