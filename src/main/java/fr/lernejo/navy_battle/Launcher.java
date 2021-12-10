package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.fire.FireRequest;
import fr.lernejo.navy_battle.api.game.fire.FireResponse;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import fr.lernejo.navy_battle.api.game.start.PostRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) {
        if (args.length < 1)
            return;

        //Placement des Bateaux
        Scanner scanner = new Scanner(System.in);
        //Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.PORTE_AVION, Boat.BoatType.CROISEUR, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.TORPILLEUR};
        Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.TORPILLEUR};

        InitGame initGame = new InitGame(availableBoats);
        Game game = new Game(initGame.addAllBoats(scanner), availableBoats);


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

        //Si programme 2 alors url en 2ème arg donc envoi PostRequest
        if (args.length == 2) {
            new PostRequest(myPort, args[1]);
        }

        http.createContext("/ping", new CallHandler());
        //Respond
        http.createContext("/api/game/start", new PostRespond());

        String adversaryURL = "http://localhost:8795";
        if (args.length == 2) {
            adversaryURL = "http://localhost:9876";
        }

        http.createContext("/api/game/fire", new FireResponse(game, adversaryURL));

        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();

        int nbTurn = 1;
        if (args.length != 2) {
            game.gameTurn(nbTurn, adversaryURL);
        }
        else {
            System.out.println("C'est le joueur 1 qui commence");
        }
    }

}
