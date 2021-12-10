package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitGame {
    private final Boat.BoatType[] availableBoats;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public final ArrayList<Boat> boatList = new ArrayList<>();

    public InitGame(Boat.BoatType[] availableBoats) {
        this.availableBoats = availableBoats;
    }

    public String getCoo(Scanner sc) {
        while(true) {
            System.out.println("Veuillez donner la colonne (lettre de A à J) et la rangée (de 1 à 10):");
            String coo = sc.nextLine();
            boolean alreadyBoat = false;
            for (Boat boat : this.boatList) {
                alreadyBoat = boat.isBoatOnPosition(coo);
                if (alreadyBoat)
                    break;
            }
            if (alreadyBoat)
                System.out.println("Il y a déjà un bateau");
            else
                return coo;
        }
    }

    public ArrayList<Boat> addAllBoats (Scanner scanner) {
        for (int i = 0; i < this.availableBoats.length; i++) {
            System.out.println("Placement de: " + this.availableBoats[i] + ", taille de: " + this.availableBoats[i].getSize());
            ArrayList<List<Integer>> positions = new ArrayList<>();
            Boat newboat = addBoat(this.availableBoats[i], positions);
            this.boatList.add(newboat);
            for (int j = 0; j < this.availableBoats[i].getSize(); j++) {
                String coo = getCoo(scanner);
                List<Integer> cell = new ArrayList<>();
                cell.add(coo.charAt(1)-'0' - 1);
                cell.add(List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0))));
                newboat.positions.add(cell);
            }
            System.out.println(this.boatList.get(i).boatPositions());
        }
        return this.boatList;
    }

    public Boat addBoat (Boat.BoatType boatType, ArrayList<List<Integer>> positions) {
        return new Boat(boatType, positions);
    }

}
