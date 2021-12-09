package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitGame {
    private final Boat.BoatType[] availableBoats;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public InitGame(Boat.BoatType[] availableBoats) {
        this.availableBoats = availableBoats;
    }

    public List<Integer> getCoo(Scanner sc) {
        List<Integer> coo = new ArrayList<Integer>();
        System.out.println("Veuillez donner la colonne (lettre de A à J):");
        String col = sc.nextLine();
        System.out.println("Veuillez donner la rangée (de 1 à 10):");
        String row = sc.nextLine();
        coo.add(Integer.parseInt(row) - 1);
        coo.add(List.of(this.alphabetCoo).indexOf(col));
        return coo;
    }

    public ArrayList<Boat> addAllBoats (Scanner scanner) {
        ArrayList<Boat> boatList = new ArrayList<Boat>();
        for (int i = 0; i < this.availableBoats.length; i++) {
            System.out.println("Placement de: " + this.availableBoats[i] + ", taille de: " + this.availableBoats[i].getSize());
            ArrayList<List<Integer>> positions = new ArrayList<>();
            for (int j = 0; j < this.availableBoats[i].getSize(); j++) {
                positions.add(getCoo(scanner));
            }
            boatList.add(addBoat(this.availableBoats[i], positions));
            System.out.println(boatList.get(i).boatPositions());
        }
        return boatList;
    }

    public Boat addBoat (Boat.BoatType boatType, ArrayList<List<Integer>> positions) {
        Boat boat = new Boat(boatType, positions);
        return boat;
    }

}
