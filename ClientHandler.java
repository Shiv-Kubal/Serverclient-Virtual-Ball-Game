package GameProgram;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private PrintWriter writer;
    private Socket socket;
    public int clientId;
    Scanner reader;
    Ball ball = new Ball();

    public ClientHandler(Socket socket, int clientId) throws IOException {

        this.socket = socket;
        this.clientId = clientId;
        reader = null;


    }


    public int returnClientID() {
        int cID = this.clientId;
        return cID;
    }


    public void playerDisconnected() throws IOException {
        System.out.println("\nPlayer: " + this.clientId + ", " + " Connection closed.");
        Server.joinedClients.remove(this.socket, this.clientId);


        Ball.removeBall(this.clientId);
        Server.gameStatus.remove(this.clientId);
        ball.playerWithBallDisconnected();

        System.out.println();
        System.out.println("CONNECTED PLAYERS: ");
        if (Ball.listPlayers().isEmpty()) {
            System.out.println("Waiting for PLayers to join....");
            System.out.println();
        } else {
            System.out.println(Ball.listPlayers());
        }

        this.socket.close();

    }








    @Override
    public void run() {
        try {
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);


            writer.println(this.clientId);
            var listplayers = ball.listPlayers();
            writer.println(listplayers.size());
            for (var players : listplayers) {
                writer.println(players);

            }
            writer.println(ball.findClientWithBall());


            while (true) {


                if (ball.ifClientHaveBall(this.clientId) == 1) {
                    writer.println(true);
                    int input;
                        input = Integer.parseInt(reader.nextLine());
                        if (input > 0) {
                            ball.passBall(input, this.clientId);

                        } if (input == 0){

                            List<Integer> listplayers2 = ball.listPlayers();
                            writer.println(listplayers2.size());
                            for (var players : listplayers2) {
                                writer.println(players);
                            }

                        }




                }

                if(ball.ifClientHaveBall(this.clientId)==0){
                    writer.println(false);
                    String line = reader.nextLine();
                    if (line.equals("who")) {
                        writer.println(ball.findClientWithBall());
                        List<Integer> listplayers2 = ball.listPlayers();
                        writer.println(listplayers2.size());
                        for (var players : listplayers2) {
                            writer.println(players);
                        }
                    }


                }

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                playerDisconnected();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



            }


}


