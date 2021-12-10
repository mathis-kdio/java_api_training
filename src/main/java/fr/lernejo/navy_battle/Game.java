package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.api.game.fire.FireRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public final ArrayList<Boat> boatList;
    public final ArrayList<Integer> boatsLifes;
    public final Boat.BoatType[] availableBoats;
    public final List<ArrayList<String>> adversaryGrid;
    public final List<ArrayList<String>> playerGrid;

    public Game(ArrayList<Boat> boatList, Boat.BoatType[] availableBoats) {
        this.boatList = boatList;
        this.availableBoats = availableBoats;
        this.boatsLifes = new ArrayList<>(availableBoats.length);
        for (Boat.BoatType availableBoat : availableBoats) {
            boatsLifes.add(availableBoat.getSize());
        }
        this.adversaryGrid = new ArrayList<>();
        this.playerGrid = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.adversaryGrid.add(new ArrayList<>());
            this.playerGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                this.adversaryGrid.get(i).add("inconnu");
                this.playerGrid.get(i).add("rien");
            }
        }
    }

    public boolean shipLeft() {
        for (Integer boatsLife : this.boatsLifes) {
            if (boatsLife != 0) {
                return true;
            }
        }
        return false;
    }

    public void boatLosesLife (Boat boat) {
        int boatIndex = this.boatList.indexOf(boat);
        this.boatsLifes.set(boatIndex, this.boatsLifes.get(boatIndex)-1);
    }

    public Boat boatOnPosition(List<Integer> position) {
        for (Boat boat : this.boatList) {
            boolean isBoat = boat.isBoatOnPosition(position);
            if (isBoat)
                return boat;
        }
        return null;
    }

    public void addAttackOnGrid(String attackResult, List<Integer> coo) {
        this.adversaryGrid.get(coo.get(0)).set(coo.get(1), attackResult);
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

    public void gameTurn(String adversaryURL) {
        FireRequest fireRequest = new FireRequest();
        Scanner scanner = new Scanner(System.in);
        String coo = fireRequest.getCooAttack(scanner);

        JSONObject jsonFireRespond = fireRequest.fire(adversaryURL, coo);
        String attackResult = jsonFireRespond.get("consequence").toString();
        if (attackResult.equals("hit")) {
            System.out.println("Le tire a réussi");
        } else if (attackResult.equals("sunk")) {
            System.out.println("Le bateau est coulé");
        } else {
            System.out.println("Le tire a manqué");
        }
        boolean isShipLeft = (Boolean) jsonFireRespond.get("shipLeft");
        if (!isShipLeft) {
            System.out.println("Partie terminée. Vous avez gagné");
        }
        List<Integer> cell = new ArrayList<>();
        if (coo.length() == 2)
            cell.add(Integer.parseInt(coo.substring(1, 2)) - 1);
        else
            cell.add(Integer.parseInt(coo.substring(1, 3)) - 1);
        cell.add(List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0))));
        addAttackOnGrid(attackResult, cell);
        System.out.println("Grille ennemie");
        showGrid(adversaryGrid);
    }
}
