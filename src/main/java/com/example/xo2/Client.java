//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.xo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Client {
    @FXML
    private Button btEnterGame;
    @FXML
    private TextField txSizeInput;
    @FXML
    private TextField txNameInput;

    public Client() {
    }

    @FXML
    public void Init() throws IOException {
        int Size = Integer.parseInt(this.txSizeInput.getText());
        (new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    try {
                        this.initializeGameSession(Size);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }

                });
            } catch (NumberFormatException var3) {
                Platform.runLater(() -> {
                    System.out.println("Error: board size must be an integer");
                });
            }

        })).start();
    }

    private void initializeGameSession(int Size) throws IOException {
        if (Size >= 3 && Size <= 10) {
            try {
                new GUI(Size);
            } catch (Exception var13) {
                throw new RuntimeException(var13);
            }

            String SERVER_HOST = "localhost";
            boolean PORT_NUMBER = true;

            try {
                Socket socket = new Socket("localhost", 12344);
                System.out.println("Connected to server");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("game available " + Size);
                String serverResponse = in.readLine();
                if (!serverResponse.equals("-1")) {
                    int gameId = Integer.parseInt(serverResponse);
                    Iterator var9 = Server.games.iterator();

                    while(var9.hasNext()) {
                        Game game = (Game)var9.next();
                        if (game.getGameId() == gameId) {
                            this.joinGame(game);
                            Server.games.remove(game);
                            return;
                        }
                    }
                }

                GUI gui = new GUI(1);
                Game game = new Game(Integer.parseInt(this.txSizeInput.getText()), this.txNameInput.getText(), gui);
                Server.games.add(game);
                gui.setGame(game);
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/xo2/gui.fxml"));
                loader.setController(gui);
                Scene scene = new Scene((Parent)loader.load(), 640.0, 480.0);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("You are - X");
                stage.show();
                System.out.println("New Game Created");
                in.close();
                out.close();
                socket.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        } else {
            System.out.println("Error: Board size must be between 3 and 10");
        }
    }

    @FXML
    public void joinGame(Game game) throws IOException {
        GUI gui = new GUI(2);
        gui.setGame(game);
        game.setGUI(gui);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/xo2/gui.fxml"));
        loader.setController(gui);
        Scene scene = new Scene((Parent)loader.load(), 640.0, 480.0);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("You are - O");
        stage.show();
        game.setPlayer2(this.txNameInput.getText());
        game.switchBoard(2);
    }

    public void enterGame(KeyEvent keyEvent) {
        try {
            this.Init();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
