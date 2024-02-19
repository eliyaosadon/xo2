//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.xo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

public class Game {
    private int boardSize;
    private int[][] board;
    private String player1;
    private String player2;
    private int playersJoined;
    private GUI gui1;
    private GUI gui2;
    private int gameId;
    private ExecutorService executorService;

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Game(int boardSize, GUI gui) {
        this.boardSize = boardSize;
        this.player1 = "player 1";
        this.player2 = "player 2";
        this.playersJoined = 1;
        this.board = new int[boardSize][boardSize];
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public Game(int boardSize, String player1, GUI gui) {
        this.boardSize = boardSize;
        this.player1 = player1;
        this.player2 = "player 2";
        this.playersJoined = 1;
        this.setGUI(gui);
        this.board = new int[boardSize][boardSize];
        this.executorService = Executors.newSingleThreadExecutor();
        this.gameId = (int)(Math.random() * 1000.0);
    }

    public Game(int boardSize, String player1, String player2) {
        this.boardSize = boardSize;
        this.player1 = player1;
        this.player2 = player2;
        this.playersJoined = 2;
        this.board = new int[boardSize][boardSize];
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public synchronized void changeBoard(int x, int y, int value) {
        System.out.println("changeBoard called on thread: " + Thread.currentThread().getName());
        this.executorService.submit(() -> {
            System.out.println("Executing changeBoard in ExecutorService on thread: " + Thread.currentThread().getName());
            this.board[x][y] = value;
            this.checkWin();
            Platform.runLater(() -> {
                System.out.println("Updating GUI from Platform.runLater on thread: " + Thread.currentThread().getName());
                this.gui1.updateBoard();
                this.gui2.updateBoard();
                this.switchBoard(value);
            });
        });
    }

    public void shutdown() {
        System.out.println("shutdown thread: " + Thread.currentThread().getName());
        this.executorService.shutdown();
    }

    public void setGUI(GUI gui) {
        if (this.gui1 == null) {
            this.gui1 = gui;
        } else {
            this.gui2 = gui;
        }

    }

    private void checkWin() {
        int size = this.boardSize;
        int[][] board = this.board;
        int value = 0;
        boolean win = false;

        int i;
        int h;
        for(i = 0; i < this.boardSize; ++i) {
            value = board[i][0];
            if (value != 0) {
                win = true;

                for(h = 1; h < size; ++h) {
                    if (board[i][h] != value) {
                        win = false;
                        break;
                    }
                }

                if (win) {
                    break;
                }
            }
        }

        if (win) {
            this.win(value);
        } else {
            for(i = 0; i < size; ++i) {
                value = board[0][i];
                if (value != 0) {
                    win = true;

                    for(h = 1; h < size; ++h) {
                        if (board[h][i] != value) {
                            win = false;
                            break;
                        }
                    }

                    if (win) {
                        break;
                    }
                }
            }

            if (win) {
                this.win(value);
            } else {
                value = board[0][0];
                if (value != 0) {
                    win = true;

                    for(i = 1; i < size; ++i) {
                        if (board[i][i] != value) {
                            win = false;
                            break;
                        }
                    }

                    if (win) {
                        this.win(value);
                        return;
                    }
                }

                value = board[0][size - 1];
                if (value != 0) {
                    win = true;

                    for(i = 1; i < size; ++i) {
                        if (board[i][size - 1 - i] != value) {
                            win = false;
                            break;
                        }
                    }

                    if (win) {
                        this.win(value);
                        return;
                    }
                }

                for(i = 0; i < size; ++i) {
                    for(h = 0; h < size; ++h) {
                        if (board[i][h] == 0) {
                            return;
                        }
                    }
                }

                this.draw();
            }
        }
    }

    private void win(int value) {
        if (value == 1) {
            Platform.runLater(() -> {
                this.gui1.win();
                this.gui2.lose();
            });
        } else {
            Platform.runLater(() -> {
                this.gui1.lose();
                this.gui2.win();
            });
        }

        this.shutdown();
    }

    void switchBoard(int value) {
        if (value == 1) {
            Platform.runLater(() -> {
                this.gui1.disableBoard();
                this.gui2.enableBoard();
            });
        } else {
            Platform.runLater(() -> {
                this.gui1.enableBoard();
                this.gui2.disableBoard();
            });
        }

    }

    public int[][] getBoard() {
        return this.board;
    }

    public void updateGUI() {
        Platform.runLater(() -> {
            this.gui1.refresh();
            this.gui2.refresh();
        });
    }

    public void draw() {
        Platform.runLater(() -> {
            this.gui1.draw();
            this.gui2.draw();
        });
        this.shutdown();
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
        this.playersJoined = 2;
        if (this.boardSize > 20) {
            Platform.runLater(() -> {
                this.gui1.createBoard();
                this.gui2.createBoard();
                this.updateGUI();
            });
        } else {
            this.gui1.createBoard();
            this.gui2.createBoard();
            this.updateGUI();
        }

    }

    public boolean isFull() {
        return this.playersJoined == 2;
    }

    public String[] getPlayers() {
        return new String[]{this.player1, this.player2};
    }

    public int getBoardSize() {
        return this.boardSize;
    }
}
