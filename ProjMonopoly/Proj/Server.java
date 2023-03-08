package Proj;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int PLAYER_MAX = 2;
        ServerSocket serverSocket = null;
        boolean listening = true;
        int playerCount = 0;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        int temp = 0;
        while (listening) {
            if (playerCount < PLAYER_MAX) {
                new Thread(new ServerThread(serverSocket.accept(), playerCount, PLAYER_MAX)).start();
                playerCount++;
            }
            else if (temp == 0) {
                System.out.println("Maximum number of players reached.");
                temp++;
            }
        }

        serverSocket.close();
    }
}

class ServerThread implements Runnable {
    private Socket socket = null;
    private int playerId = -1;
    private int playerCount = -1;
    private static int currentPlayer = 0;
    private static final Object lock = new Object();

    public ServerThread(Socket socket, int playerId, int playerCount) {
        this.playerCount = playerCount;
        this.socket = socket;
        this.playerId = playerId;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                synchronized (lock) {
                    if (playerId == currentPlayer) {
                        System.out.println("Player " + playerId + " sent: " + inputLine);
                        outputLine = "It's your turn.";
                        currentPlayer = (currentPlayer + 1) % playerCount;
                    }
                    else {
                        System.out.println("Player " + playerId + " sent: " + inputLine+ " but it's not his turn.");
                        outputLine = "It's not your turn.";
                    }
                }
                out.println(outputLine);
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
