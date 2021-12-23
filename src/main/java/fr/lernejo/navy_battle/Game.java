package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.api.game.fire.FireRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public final ArrayList<Boat> boatList = new ArrayList<>();
    public final ArrayList<Integer> boatsLifes = new ArrayList<>();
    public final List<ArrayList<String>> adversaryGrid = new ArrayList<>();
    public final List<ArrayList<String>> playerGrid = new ArrayList<>();
    public final ArrayList<String> adversaryURL = new ArrayList<>();
    public final List<int[]> previousAttack = new ArrayList<>();
    public final Affichage affichage = new Affichage();

    public Game(Boat.BoatType[] availableBoats, String[][] positionsBoats) {
        for (int i = 0; i < availableBoats.length; i++)
            this.boatList.add(new Boat(availableBoats[i], positionsBoats[i]));
        for (Boat boat : this.boatList)
            this.boatsLifes.add(boat.size);
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
            if (boatsLife != 0)
                return true;
        }
        return false;
    }

    public void boatLosesLife (Boat boat) {
        int boatIndex = this.boatList.indexOf(boat);
        this.boatsLifes.set(boatIndex, this.boatsLifes.get(boatIndex)-1);
    }

    public Boat boatOnPosition(int[] position) {
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

    public String generateNextCoo() { //Coo à attaquer brute force
        if (this.previousAttack.size() != 0) {
            previousAttack.get(0)[1]++; //Attaque sur rangée en dessous
            if (previousAttack.get(0)[1] > 9) { //Retour en haut si pas de rangée après
                previousAttack.get(0)[1] = 0;
                previousAttack.get(0)[0]++; //Colonne suivante
            }
            return this.alphabetCoo[previousAttack.get(0)[0]] + (previousAttack.get(0)[1] + 1);
        }
        else {
            previousAttack.add(new int[]{0, 0});
            return "A1";
        }
    }

    public void gameTurn() {
        String coo = generateNextCoo();
        System.out.println("Attaque de la case : " + coo);
        JSONObject jsonFireRespond = new FireRequest().fire(adversaryURL.get(0), coo);
        String attackResult = jsonFireRespond.get("consequence").toString();
        this.affichage.resultAttack(attackResult);
        if (!(Boolean) jsonFireRespond.get("shipLeft"))
            System.out.println("Partie terminée. Vous avez gagné");
        List<Integer> cell = new ArrayList<>();
        cell.add(previousAttack.get(0)[1]);
        cell.add(previousAttack.get(0)[0]);
        addAttackOnGrid(attackResult, cell);
        System.out.println("Grille ennemie");
        this.affichage.showGrid(adversaryGrid);
    }
}
