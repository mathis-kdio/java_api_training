package fr.lernejo.navy_battle.api.game.fire;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class FireRequest {
    public String getCooAttack(Scanner sc) {
        System.out.println("Veuillez donner la colonne (lettre de A à J) et la rangée (de 1 à 10):");
        String str = sc.nextLine();
        return str;
    }

    public void fire(String adversaryUrl, String cell) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/fire?cell=" + cell))
            .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
    }

}
