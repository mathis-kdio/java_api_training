package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public final ArrayList<String> playerGrid;
    public final ArrayList<String> adversaryGrid;
    public final ArrayList<Boat> boatList;
    private final Boat.BoatType[] availableBoats;

    public Game(ArrayList<Boat> boatList, Boat.BoatType[] availableBoats) {
        this.playerGrid = new ArrayList<>();
        this.adversaryGrid = new ArrayList<>();
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

    public boolean isBoatOnPosition(List<Integer> coo) {
        for (int i = 0; i < this.boatList.size(); i++) {
            ArrayList<List<Integer>> boatPositions = this.boatList.get(i).boatPositions();
            for (int j = 0; j < boatPositions.size(); j++) {
                if (boatPositions.get(j) == coo) {
                    boatList.get(i).setLife(1);
                    return true;
                }
            }
        }
        return false;
    }
}
