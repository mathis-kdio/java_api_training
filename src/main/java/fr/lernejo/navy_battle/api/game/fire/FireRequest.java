package fr.lernejo.navy_battle.api.game.fire;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FireRequest {
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public List<Integer> getCooAttack(Scanner sc) {
        List<Integer> coo = new ArrayList<Integer>();
        System.out.println("Veuillez donner la colonne (lettre de A à J):");
        String str = sc.nextLine();
        coo.add(List.of(this.alphabetCoo).indexOf(str));
        System.out.println("Veuillez donner la rangée (de 1 à 10):");
        str = sc.nextLine();
        coo.add(Integer.parseInt(str));
        return coo;
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
