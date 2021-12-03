package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class CallHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        String body = "OK";

        try {
            exchange.sendResponseHeaders(200, body.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
