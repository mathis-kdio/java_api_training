package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) {
        InetSocketAddress addrToBind = new InetSocketAddress("localhost", Integer.parseInt(args[0]));
        HttpServer http = null;
        try {
            http = HttpServer.create(addrToBind,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (http == null)
            return;

        HttpContext httpContext = http.createContext("/ping", new CallHandler());
        http.setExecutor(Executors.newFixedThreadPool(1));
        http.start();
    }

}
