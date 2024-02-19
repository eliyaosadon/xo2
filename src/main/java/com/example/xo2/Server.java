//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.xo2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Game> games = new ArrayList();

    public Server(ArrayList<Game> games) {
        Server.games = games;
        (new Thread(() -> {
            this.runServer();
        })).start();
    }

    public void runServer() {
        boolean PORT_NUMBER = true;

        try {
            ServerSocket serverSocket = new ServerSocket(12344);
            System.out.println("Server is listening on port 12344");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                (new Thread(clientHandler)).start();
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }
}
