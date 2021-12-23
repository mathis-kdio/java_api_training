package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.fire.FireResponse;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import fr.lernejo.navy_battle.api.game.start.PostRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) return;
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", Integer.parseInt(args[0]));
        HttpServer http = HttpServer.create(addrToBind,0);
        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();
        http.createContext("/ping", new CallHandler());
        Boat.BoatType[] availableBoats = {Boat.BoatType.TORPILLEUR, Boat.BoatType.PORTE_AVION, Boat.BoatType.CROISEUR, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.CONTRE_TORPILLEURS};
        String[][] positionsBoats = {{"A1", "A2"}, {"B1", "C1","D1","E1","F1"}, {"A5", "B5","C5","D5"}, {"E5", "E6","E7"}, {"C6", "C7","C8"}};
        Game game = new Game(availableBoats, positionsBoats);
        if (args.length == 2) { //Si programme 2 alors envoi PostRequest
            new PostRequest(Integer.parseInt(args[0]), args[1]);
            game.adversaryURL.add(args[1]);
        }
        http.createContext("/api/game/start", new PostRespond(game, args[0]));
        http.createContext("/api/game/fire", new FireResponse(game));
    }
}
