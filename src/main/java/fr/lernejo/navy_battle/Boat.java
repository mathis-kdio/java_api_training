package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    public final int size;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public final ArrayList<List<Integer>> positions;
    public Boat(BoatType boatType, ArrayList<List<Integer>> positions) {
        this.size = boatType.getSize();
        this.positions = positions;
    }

    public ArrayList<List<Integer>> boatPositions() {
        return this.positions;
    }

    public boolean isBoatOnPosition(List<Integer> coo) {
        ArrayList<List<Integer>> boatPositions = boatPositions();
        for (List<Integer> boatPosition : boatPositions) {
            if (boatPosition.equals(coo)) {
                return true;
            }
        }
        return false;
    }

    public enum BoatType {
        PORTE_AVION(5), CROISEUR(4), CONTRE_TORPILLEURS(3), TORPILLEUR(2);
        private final int size;
        public int getSize() {
            return this.size;
        }
        BoatType(int size) {
            this.size = size;
        }
    }
}
