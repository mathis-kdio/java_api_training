package fr.lernejo.navy_battle.api.game.start;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

import fr.lernejo.navy_battle.Game;
import org.json.JSONObject;

public class PostRespond implements HttpHandler {
    public final Game game;
    public final String port;

    public PostRespond(Game game, String port) {
        this.game = game;
        this.port = port;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            JSONObject jsonResquestBody = convertStreamToJSONObject(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            if (jsonResquestBody.has("id") && jsonResquestBody.has("url") && jsonResquestBody.has("message")) {             //Si format JSON correct
                this.game.adversaryURL.add((String) jsonResquestBody.get("url"));
                String body = "{\n\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\",\n\"url\": \"http://localhost:" + this.port + "\",\n\"message\": \"May the best code win\"\n}";
                writeReponse(exchange, body, 202);
                this.game.gameTurn(); //Lancement de la partie
            } else {
                writeReponse(exchange, "Bad Request", 400);
            }
        } else {
            writeReponse(exchange, "Not Found", 404);
        }
    }

    public void writeReponse(HttpExchange exchange, String body, Integer rCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(rCode, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    public JSONObject convertStreamToJSONObject(InputStreamReader isr) throws IOException {
        int b;
        BufferedReader br = new BufferedReader(isr);
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1)
            buf.append((char) b);
        String requestBody = buf.toString();
        br.close();
        isr.close();
        return new JSONObject(requestBody);
    }
}
