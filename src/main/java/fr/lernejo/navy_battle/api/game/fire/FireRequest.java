package fr.lernejo.navy_battle.api.game.fire;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FireRequest {
    public JSONObject fire(String adversaryUrl, String cell) {
        HttpResponse<String> response = null;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/fire?cell=" + cell))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        return new JSONObject(response.body());
    }
}
