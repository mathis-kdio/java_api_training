package fr.lernejo.navy_battle.api.game.start;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
        if(exchange.getRequestMethod().equals("POST")) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            int b;
            StringBuilder buf = new StringBuilder();
            while ((b = br.read()) != -1)
                buf.append((char) b);
            String requestBody = buf.toString();
            br.close();
            isr.close();
            JSONObject jsonResquestBody = new JSONObject(requestBody);
            //Si format JSON correct
            if (jsonResquestBody.has("id") && jsonResquestBody.has("url") && jsonResquestBody.has("message")) {
                this.game.adversaryURL.add((String) jsonResquestBody.get("url"));
                //Réponse avec ID du programme
                String body = "{\n\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\",\n\"url\": \"http://localhost:" + this.port +"\",\n\"message\": \"May the best code win\"\n}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(202, body.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(body.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.game.gameTurn();
            }
            else {
                String body = "Bad Request";
                exchange.sendResponseHeaders(400 , body.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(body.getBytes());
                }
            }
        }
        else {
            String body = "Not Found";
            exchange.sendResponseHeaders(404 , body.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
            }
        }
    }
}
