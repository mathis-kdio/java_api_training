package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Boat;
import fr.lernejo.navy_battle.Game;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FireResponse implements HttpHandler {
    public final Game game;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public FireResponse(Game game) {
        this.game = game;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")) {
            String query = exchange.getRequestURI().getQuery();
            String[] tokens = query.split("=");
            String coo = tokens[1];
            List<Integer> cell = new ArrayList<>();
            if (coo.length() == 2)
                cell.add(Integer.parseInt(coo.substring(1, 2)) - 1);
            else
                cell.add(Integer.parseInt(coo.substring(1, 3)) - 1);
            cell.add(List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0))));
            Boat boat = this.game.boatOnPosition(cell);
            String consequence;
            if (boat != null) {
                this.game.boatLosesLife(boat);
                if (this.game.boatsLifes.get(this.game.boatList.indexOf(boat)) == 0) {
                    consequence = "sunk";
                }
                else {
                    consequence = "hit";
                }
            }
            else
                consequence = "miss";

            boolean shipLeft = this.game.shipLeft();
            String body = "{\n\"consequence\": \"" + consequence + "\",\n\"shipLeft\": " + shipLeft+ "\n}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, body.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
            }
            //Une fois que la réponse est envoyée, c'est au tour du joueur
            if (shipLeft) {
                game.gameTurn();
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
}
