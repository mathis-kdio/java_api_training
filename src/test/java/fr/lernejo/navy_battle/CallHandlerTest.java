package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

class CallHandlerTest {

    @Test
    void CallHandler_should_return_OK() throws IOException, InterruptedException {
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", 9876);
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert http != null;
        http.createContext("/ping", new CallHandler());
        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .build();

        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).body())
            .as("/ping return body = ok")
            .isEqualTo("OK");
        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode())
            .as("/ping return statusCode = 200")
            .isEqualTo(200);
        http.stop(1);
    }
}
