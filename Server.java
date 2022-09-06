package GameProgram;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
    private final static int port = 8888;

    public static HashMap<Socket, Integer> joinedClients = new HashMap<>();
    public static TreeMap<Integer, Boolean> gameStatus = new TreeMap<>();
    private static int clientId = 0;
    private static Ball ball;



    public static void main(String[] args) {

        RunServer();
    }

    public static void firstPlayer(int clientID) {
        if (Server.gameStatus.isEmpty()) {
            Server.gameStatus.put(clientID, true);
            System.out.println("First player is here! Ball is passed to ClientID: " + clientID);
        } else {
            Server.gameStatus.put(clientID, false);
        }
    }


    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for incoming connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                clientId++;
                joinedClients.put(socket, clientId);
                firstPlayer(clientId);
                System.out.println("ClientID: " + clientId + ", " + " Connected successfully.");
                System.out.println("CONNECTED PLAYERS: ");
                System.out.println(ball.listPlayers());


                Runnable r1 = new ClientHandler(socket, clientId);
                Thread thread = new Thread(r1);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
