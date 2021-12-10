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

    public List<Integer> getCoo(Scanner sc, ArrayList<List<Integer>> boatPositions) {
        while(true) {
            System.out.println("Veuillez donner la colonne (lettre de A à J) et la rangée (de 1 à 10):");
            String coo = sc.nextLine();
            List<Integer> cell = new ArrayList<>();
            if (coo.length() > 1 && coo.length() < 4) {
                if (coo.length() == 2)
                    cell.add(Integer.parseInt(coo.substring(1, 2)) - 1);
                else
                    cell.add(Integer.parseInt(coo.substring(1, 3)) - 1);
                cell.add(List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0))));
                if (cell.get(0) < 0 || cell.get(1) < 0 || cell.get(0) > 9 || cell.get(1) > 9) { //Test si position est dans la grille
                    System.out.println("Position incorrecte");
                }
                else { //Test s'il n'y a pas déjà un bateau
                    boolean alreadyBoat = false;
                    for (Boat boat : this.boatList) {
                        alreadyBoat = boat.isBoatOnPosition(cell);
                        if (alreadyBoat)
                            break;
                    }
                    if (alreadyBoat)
                        System.out.println("Il y a déjà un bateau");
                    else if (boatPositions.size() > 0) { //Test si la position est dans la continuité des autres
                        List<Integer> lastPosition = boatPositions.get(boatPositions.size() - 1);
                        if (lastPosition.get(0).equals(cell.get(0))) { //Si sur même ligne
                            for (List<Integer> boatPosition : boatPositions) {
                                if (cell.get(1) - 1 == boatPosition.get(1) || cell.get(1) + 1 == boatPosition.get(1)) {
                                    return cell;
                                }
                            }
                            System.out.println("Position n'est pas dans la continuité");
                        } else if (lastPosition.get(1).equals(cell.get(1))) { //Si sur même colonne
                            for (List<Integer> boatPosition : boatPositions) {
                                if (cell.get(0) - 1 == boatPosition.get(0) || cell.get(0) + 1 == boatPosition.get(0)) {
                                    return cell;
                                }
                            }
                            System.out.println("Position n'est pas dans la continuité");
                        } else {
                            System.out.println("Position n'est pas dans la continuité");
                        }
                    } else { //Si première position du bateau
                        return cell;
                    }
                }
            }
            else {
                System.out.println("Position incorrecte");
            }
        }
    }

    public ArrayList<Boat> addAllBoats (Scanner scanner) {
        for (int i = 0; i < this.availableBoats.length; i++) {
            System.out.println("Placement de: " + this.availableBoats[i] + ", taille de: " + this.availableBoats[i].getSize());
            ArrayList<List<Integer>> positions = new ArrayList<>();
            ArrayList<List<Integer>> tmpPositions = new ArrayList<>();
            Boat newboat = addBoat(this.availableBoats[i], positions);
            this.boatList.add(newboat);
            for (int j = 0; j < this.availableBoats[i].getSize(); j++) {
                List<Integer> coo = getCoo(scanner, tmpPositions);
                tmpPositions.add(coo);
                newboat.positions.add(coo);
            }
            System.out.println(this.boatList.get(i).boatPositions());
        }
        return this.boatList;
    }

    public Boat addBoat (Boat.BoatType boatType, ArrayList<List<Integer>> positions) {
        return new Boat(boatType, positions);
    }

}
