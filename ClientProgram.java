package GameProgram;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientProgram  {
    private Scanner reader;
    private PrintWriter writer;
    Server s = new Server();
    Ball ball = new Ball();

    Socket socket;

    public static void main(String[] args) {
        int readClientID;

        try (Client client = new Client()){
            readClientID = client.getClientId();
            System.out.println("You are Player: " + readClientID + "\n" + "CONNECTED PLAYERS: ");

                var playerNumbers = client.getPlayers();
                for (int i : playerNumbers)
                    System.out.println(i);

            System.out.println("Player: " + client.PlayerWithBall() + " has the ball.");


            while(true) {

                boolean hasBall = client.checkHasBall();


                        if (hasBall) {

                                System.out.println("YOU HAVE THE BALL!!! Who would you like to pass it to? Enter 0, to list the players.");
                                Scanner in = new Scanner(System.in);
                                String input = in.nextLine();
                                client.playerInput(input);
                                    if (input.equals("0")) {
                                        int[] playerNumbers2 = client.getPlayers();
                                        System.out.println("CONNECTED PLAYERS:");
                                        for (int i : playerNumbers2)
                                            System.out.println(i);
                                    }
                                    if(input.chars().allMatch(Character::isLetter)){
                                        System.out.println("Invalid Input!!!");
                                    }

                        }

                        if(!hasBall){
                            System.out.println("You currently DON'T have the ball. Type who to know who has the ball. ");
                            Scanner sc= new Scanner(System.in);
                            String str= sc.nextLine();
                            client.playerInput2(str);
                            if(str.equals("who")){
                                System.out.println("Player: " + client.PlayerWithBall() + " has the ball.");
                                int[] playerNumbers2 = client.getPlayers();
                                System.out.println("CONNECTED PLAYERS:");
                                for (int i : playerNumbers2) {
                                    System.out.println(i);
                                    }
                            }
                            else
                                System.out.println("Invalid input!!!");
                        }





            }



        }

         catch (Exception e) {
            e.printStackTrace();
        }

    }
}





