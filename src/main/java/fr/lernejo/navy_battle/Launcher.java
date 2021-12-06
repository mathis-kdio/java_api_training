package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.api.game.Start;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) {
        if (args.length < 1)
            return;

        String myPort = args[0];
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", Integer.parseInt(myPort));
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (http == null)
            return;

        http.createContext("/ping", new CallHandler());
        HttpContext httpContext = http.createContext("/api/game/start", new Start());

        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();
    }

}
