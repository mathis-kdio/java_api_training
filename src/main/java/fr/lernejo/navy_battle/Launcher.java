package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.fire.FireRequest;
import fr.lernejo.navy_battle.api.game.fire.FireResponse;
import fr.lernejo.navy_battle.api.game.start.PostRespond;
import fr.lernejo.navy_battle.api.game.start.PostRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
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

        List<ArrayList<String>> playerGrid = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            playerGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                playerGrid.get(i).add("inconnu");
            }
        }

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

        http.createContext("/api/game/fire", new FireResponse());

        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();
        String adversaryURL = "http://localhost:8795";

        //Attendre que J2 fasse la requête
        //while (adversaryURL == null)

        int nbTurn = 1;
        JSONObject jsonFireRespond;
        List<ArrayList<String>> adversaryGrid = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            adversaryGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                adversaryGrid.get(i).add("inconnu");
            }
        }
        do {
            System.out.println("Tour n°" + nbTurn);
            FireRequest fireRequest = new FireRequest();
            String coo = fireRequest.getCooAttack(scanner);
            jsonFireRespond = fireRequest.fire(adversaryURL, "A1");
            String attackResult = jsonFireRespond.get("consequence").toString();
            if (attackResult.equals("hit")) {
                System.out.println("Le tire a réussi");
            }
            else if (attackResult.equals("sunk")) {
                System.out.println("Le bateau est coulé");
            }
            else {
                System.out.println("Le tire a manqué");
            }
            adversaryGrid = game.addAttackOnGrid(adversaryGrid, attackResult, coo);
            boolean isBoat = game.isBoatOnPosition(coo);


            nbTurn++;
        } while (jsonFireRespond.get("shipLeft").equals(true));
        System.out.println("Fin de la partie");
    }

}
