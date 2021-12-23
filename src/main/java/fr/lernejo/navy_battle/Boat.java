package fr.lernejo.navy_battle;

import java.util.Arrays;
import java.util.List;

public class Boat {
    public final int size;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public final int[][] positions;

    public Boat(BoatType boatType, String[] positions) {
        this.size = boatType.getSize();
        int col, row;
        this.positions = new int[this.size][];
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].length() == 2)
                row = Integer.parseInt(positions[i].substring(1, 2)) - 1;
            else
                row = Integer.parseInt(positions[i].substring(1, 3)) - 1;
            col = List.of(this.alphabetCoo).indexOf(String.valueOf(positions[i].charAt(0)));
            this.positions[i] = new int[]{row, col};
        }
    }

    public boolean isBoatOnPosition(int[] coo) {
        for (int[] boatPosition : this.positions) {
            if (Arrays.equals(boatPosition, coo))
                return true;
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
