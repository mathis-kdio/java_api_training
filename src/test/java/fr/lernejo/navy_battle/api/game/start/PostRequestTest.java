package fr.lernejo.navy_battle.api.game.start;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Boat;
import fr.lernejo.navy_battle.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

class PostRequestTest {
    public final HttpClient client;

    public PostRequestTest() {
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", 9876);
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Boat> boatList = new ArrayList<>();
        ArrayList<List<Integer>> positions = new ArrayList<>();
        positions.add(Arrays.asList(0,0));
        positions.add(Arrays.asList(1,0));
        new Boat(Boat.BoatType.TORPILLEUR, positions);
        Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.TORPILLEUR};
        Game game = new Game(boatList, availableBoats);

        assert http != null;
        http.createContext("/api/game/start", new PostRespond(game));
        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();

        this.client = HttpClient.newHttpClient();
    }


    @Test
    void PostRequest_should_return_202() throws IOException, InterruptedException {
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:9876\", \"message\":\"Programme 2 ID & url:port\"}"))
            .build();

        Assertions.assertThat(this.client.send(requetePost, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("/api/game/start return 202")
            .isEqualTo(202);
    }
}
