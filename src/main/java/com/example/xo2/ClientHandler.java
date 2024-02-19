package com.example.xo2;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);

            String message;
            while((message = in.readLine()) != null) {
                if (message.startsWith("game available")) {
                    int size = Integer.parseInt(message.split(" ")[2]);
                    int gameId = this.findGame(size);
                    out.println(gameId);
                } else {
                    out.println("Invalid command");
                }

                System.out.println("Received from client: " + message);
                out.println("Server echoes: " + message);
            }

            in.close();
            out.close();
            this.clientSocket.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    private int findGame(int size) {
        Iterator var2 = Server.games.iterator();

        Game game;
        do {
            if (!var2.hasNext()) {
                return -1;
            }

            game = (Game)var2.next();
        } while(game.getBoardSize() != size || game.isFull());

        return game.getGameId();
    }
}
