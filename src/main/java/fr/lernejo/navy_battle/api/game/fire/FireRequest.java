package fr.lernejo.navy_battle.api.game.fire;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FireRequest {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public String getCooAttack(Scanner sc) {
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
                else {
                    return coo;
                }
            }
            else {
                System.out.println("Position incorrecte");
            }
        }
    }

    public JSONObject fire(String adversaryUrl, String cell) {
        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/fire?cell=" + cell))
            .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new JSONObject(response.body());
    }
}
