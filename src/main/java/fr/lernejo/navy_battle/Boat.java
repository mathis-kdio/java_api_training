package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    public final int size;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public final ArrayList<List<Integer>> positions = new ArrayList<>();

    public Boat(BoatType boatType, String[] positions) {
        this.size = boatType.getSize();
        for (String strings : positions) {
            List<Integer> position = new ArrayList<>();
            if (strings.length() == 2)
                position.add(Integer.parseInt(strings.substring(1, 2)) - 1);
            else
                position.add(Integer.parseInt(strings.substring(1, 3)) - 1);
            position.add(List.of(this.alphabetCoo).indexOf(String.valueOf(strings.charAt(0))));
            this.positions.add(position);
        }
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
