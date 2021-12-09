package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FireResponse implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            int b;
            StringBuilder buf = new StringBuilder();
            while ((b = br.read()) != -1)
                buf.append((char) b);
            String requestBody = buf.toString();
            br.close();
            isr.close();
            JSONObject jsonResquestBody = new JSONObject(requestBody);
            System.out.println(jsonResquestBody);
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
