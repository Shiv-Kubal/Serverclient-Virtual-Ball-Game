package GameProgram;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable{
    final int port = 8888;

    public final Scanner reader;
    private final PrintWriter writer;

    public Client() throws Exception {
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        writer = new PrintWriter(socket.getOutputStream(), true);

    }
    public int[] getPlayers(){
        // Reading the number of players
        String line = reader.nextLine();
        int numberOfPlayers = Integer.parseInt(line);
        // Reading the player numbers
        int[] players = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            line = reader.nextLine();
            players[i] = Integer.parseInt(line);
        }
        return players;
    }
    public int getClientId(){
        int client_id = Integer.parseInt(reader.nextLine());
        return client_id;
    }
    public boolean checkHasBall(){

        return Boolean.parseBoolean(reader.nextLine());
    }

    public void playerInput(String clientId){
        writer.println(clientId);
    }


    public void playerInput2(String str){
         writer.println(str);
    }



    public int PlayerWithBall(){

        return Integer.parseInt(reader.nextLine());
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}