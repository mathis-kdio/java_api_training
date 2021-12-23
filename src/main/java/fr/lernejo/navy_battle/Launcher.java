package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.fire.FireResponse;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import fr.lernejo.navy_battle.api.game.start.PostRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) {
        if (args.length < 1)
            return;

        //Requêtes POST pour que joueur 1 récupère l'URL et le port de joueur 2
        int myPort = Integer.parseInt(args[0]);
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", myPort);
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (http == null)
            return;

        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();

        http.createContext("/ping", new CallHandler());

        //Si programme 2 alors url en 2ème arg donc envoi PostRequest
        if (args.length == 2) {
            new PostRequest(myPort, args[1]);
        }

        //Placement des Bateaux
        Boat.BoatType[] availableBoats = {Boat.BoatType.TORPILLEUR, Boat.BoatType.PORTE_AVION, Boat.BoatType.CROISEUR, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.CONTRE_TORPILLEURS};
        String[][] positionsBoats = {{"A1", "A2"}, {"B1", "C1","D1","E1","F1"}, {"A5", "B5","C5","D5"}, {"E5", "E6","E7"}, {"C6", "C7",}};
        Game game = new Game(availableBoats, positionsBoats);

        //Respond
        http.createContext("/api/game/start", new PostRespond(game, args[0]));

        //Fire API
        http.createContext("/api/game/fire", new FireResponse(game));

        if (args.length == 2) {
            System.out.println("C'est le joueur 1 qui commence");
        }
    }
}
