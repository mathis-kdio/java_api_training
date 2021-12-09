package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class FireResponse implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")) {
            String query = exchange.getRequestURI().getQuery();
            String[] tokens = query.split("=");
            String cellFire = tokens[1];
            System.out.println("GetRequest: Case visée:");
            System.out.println(cellFire);

            String body = "{\n\"consequence\": \"sunk\",\n\"shipLeft\": true\n}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, body.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
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
