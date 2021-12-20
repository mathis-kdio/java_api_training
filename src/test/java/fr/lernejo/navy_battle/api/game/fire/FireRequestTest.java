package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.CallHandler;
import fr.lernejo.navy_battle.api.game.start.PostRequest;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


class FireRequestTest {

    @Test
    void getCooAttack_should_return_string() {
        FireRequest fireRequest = new FireRequest();
        System.setIn(new ByteArrayInputStream("A1".getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(System.in);

        Assertions.assertThat(fireRequest.getCooAttack(scanner)).as("get coo attack")
            .isEqualTo("A1");
    }

    /*@Test
    void fire_should_return_JSONObject() {
        FireRequest fireRequest = new FireRequest();

        //Serveur réponse
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", 9876);
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (http == null)
            return;

        //Request
        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1"))
            .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(response.body());

        Assertions.assertThat(fireRequest.fire("http://localhost:9876" ,"A1")).as("shipLeft true")
            .isEqualTo(jsonObject);
    }*/
}
