package fr.lernejo.navy_battle.api.game.fire;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Boat;
import fr.lernejo.navy_battle.Game;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class FireResponse implements HttpHandler {
    public final Game game;
    public final String[] alphabetCoo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    public FireResponse(Game game) {
        this.game = game;
    }

    public int[] convertCooStrToInt(String coo) {
        int col, row;
        if (coo.length() == 2)
            row = Integer.parseInt(coo.substring(1, 2)) - 1;
        else
            row = Integer.parseInt(coo.substring(1, 3)) - 1;
        col = List.of(this.alphabetCoo).indexOf(String.valueOf(coo.charAt(0)));
        return new int[]{row, col};
    }

    public String returnConsequence(Boat boat) {
        String consequence;
        if (boat != null) {
            this.game.boatLosesLife(boat);
            if (this.game.boatsLifes.get(this.game.boatList.indexOf(boat)) == 0)
                consequence = "sunk";
            else
                consequence = "hit";
        }
        else
            consequence = "miss";
        return consequence;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            String query = exchange.getRequestURI().getQuery();
            int[] coo = convertCooStrToInt(query.split("=")[1]);
            String consequence = returnConsequence(this.game.boatOnPosition(coo));
            boolean shipLeft = this.game.shipLeft();
            String body = "{\n\"consequence\": \"" + consequence + "\",\n\"shipLeft\": " + shipLeft+ "\n}";
            writeReponse(exchange, body, 202);

            if (shipLeft) //Une fois que la réponse est envoyée, c'est au tour du joueur
                game.gameTurn();
            else // S'il ne reste plus de bateaux alors fin
                System.out.println("Partie terminée. Vous avez perdu");
        }
        else
            writeReponse(exchange, "Not Found", 404);
    }

    public void writeReponse(HttpExchange exchange, String body, Integer rCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(rCode, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
