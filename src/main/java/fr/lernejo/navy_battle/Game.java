package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public final ArrayList<Boat> boatList;
    private final Boat.BoatType[] availableBoats;
    public final List<ArrayList<String>> adversaryGrid;

    public Game(ArrayList<Boat> boatList, Boat.BoatType[] availableBoats) {
        this.boatList = boatList;
        this.availableBoats = availableBoats;
        this.adversaryGrid = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.adversaryGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                this.adversaryGrid.get(i).add("inconnu");
            }
        }
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
        cell.add(coo.charAt(1)-'0' - 1);
        cell.add(List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0))));
        for (int i = 0; i < this.boatList.size(); i++) {
            ArrayList<List<Integer>> boatPositions = this.boatList.get(i).boatPositions();
            for (int j = 0; j < boatPositions.size(); j++) {
                if (boatPositions.get(j).equals(cell)) {
                    boatList.get(i).setLife(1);
                    return true;
                }
            }
        }
        return false;
    }

    public void addAttackOnGrid(String attackResult, String coo) {
        int row = coo.charAt(1)-'0' - 1;
        int col = List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0)));
        this.adversaryGrid.get(row).set(col, attackResult);
    }

    public void showGrid(List<ArrayList<String>> grid) {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(this.alphabetCoo[i] + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print((i+1) + " ");
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
