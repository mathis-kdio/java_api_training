package fr.lernejo.navy_battle.api.game.fire;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class FireRequest {
    public String getCooAttack(Scanner sc) {
        System.out.println("Veuillez donner la colonne (lettre de A à J) et la rangée (de 1 à 10):");
        return sc.nextLine();
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
