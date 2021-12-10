package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    public final int size;
    public final int life;
    public final ArrayList<List<Integer>> positions;
    public Boat(BoatType boatType, ArrayList<List<Integer>> positions) {
        this.size = boatType.getSize();
        this.life = boatType.getSize();
        this.positions = positions;
    }
    public boolean isBoatAlive() {
        return this.life == 0;
    }
    public ArrayList<List<Integer>> boatPositions() {
        return this.positions;
    }

    public void setLife(Integer life) {
        //this.life = life;
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
