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

    public boolean boatOnPosition(String position) {
        for (Boat boat : this.boatList) {
            boolean isBoat = boat.isBoatOnPosition(position);
            if (isBoat)
                return true;
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

    public void gameTurn(int nbTurn, String adversaryURL) {
        System.out.println("Tour n°" + nbTurn);
        FireRequest fireRequest = new FireRequest();
        Scanner scanner = new Scanner(System.in);
        String coo = fireRequest.getCooAttack(scanner);
        System.out.println(adversaryURL);
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
        addAttackOnGrid(attackResult, coo);
        System.out.println("Grille ennemie");
        showGrid(adversaryGrid);
    }
}
