package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Game;

import java.io.IOException;
import java.io.OutputStream;

public class FireResponse implements HttpHandler {
    public final Game game;
    public final String adversaryURL;

    public FireResponse(Game game, String adversaryURL) {
        this.game = game;
        this.adversaryURL = adversaryURL;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")) {
            String query = exchange.getRequestURI().getQuery();
            String[] tokens = query.split("=");
            String cellFire = tokens[1];
            boolean isBoat = this.game.isBoatOnPosition(cellFire);
            String consequence;
            if (isBoat)
                consequence = "hit";
            else
                consequence = "miss";

            boolean shipLeft = this.game.shipLeft();

            String body = "{\n\"consequence\": \"" + consequence + "\",\n\"shipLeft\": " + shipLeft+ "\n}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, body.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
                setTour();
            }
            //Une fois que la réponse est envoyée, c'est au tour du joueur
            if (shipLeft) {
                game.gameTurn(0, adversaryURL);
            }
            else {
                System.out.println("Partie terminée. Vous avez perdu");
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

    public void setTour() {
        //this.tour = !this.tour;
    }
}
