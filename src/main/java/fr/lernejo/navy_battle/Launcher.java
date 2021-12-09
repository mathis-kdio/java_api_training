package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.Boat;
import fr.lernejo.navy_battle.api.game.Game;
import fr.lernejo.navy_battle.api.game.InitGame;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import fr.lernejo.navy_battle.api.game.start.PostRequest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) {
        if (args.length < 1)
            return;

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

        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();

        Scanner scanner = new Scanner(System.in);
        Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.PORTE_AVION, Boat.BoatType.CROISEUR, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.CONTRE_TORPILLEURS, Boat.BoatType.TORPILLEUR};

        InitGame initGame = new InitGame(availableBoats);
        Game game = new Game(initGame.addAllBoats(scanner), availableBoats);

        int nbTurn = 1;
        while (game.gameFinish()) {
            System.out.println("Tour n°" + nbTurn);
            nbTurn++;
        }

    }

}
