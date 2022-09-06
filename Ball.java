package GameProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ball {


    public static List<Integer> listPlayers() {
        List<Integer> intList = new ArrayList<Integer>();

        for (int value : Server.joinedClients.values()) {
            intList.add(value);
        }
        return intList;
    }


    public int findClientWithBall() {
        int clientID = 0;
        for (Map.Entry entry : Server.gameStatus.entrySet()) {
            if ((boolean) entry.getValue()) {
                clientID = (int) entry.getKey();
            }
        }
        return clientID;

    }

    public  int ifClientHaveBall(int clientID) {
        int clientIDHasBall = findClientWithBall();
        if (clientIDHasBall == clientID) {
            return 1;
        } else {
            return 0;
        }

    }

    public static void removeBall(int clientID) {

        Server.gameStatus.replace(clientID, false);
    }


    public static boolean isClientPresent(int clientID) {
        boolean present = false;
        for (Integer entryID : Server.gameStatus.keySet()) {
            if (entryID == clientID) {
                present = true;
                break;
            }
        }
        return present;
    }

    public  void passBall(int collector, int currentID) {
        if (isClientPresent(collector) && collector != currentID) {
            System.out.println("Player: " + currentID + " passes ball to Player: " + collector + ".");
            Server.gameStatus.replace(collector, true);
            removeBall(currentID);
        } else if (collector == currentID) {
            System.out.println("Player: " + collector + " is throwing the ball back to him/herself...");
        } else {
            System.out.println("Player: " + collector + " does not exist, returning ball!");
            Server.gameStatus.replace(currentID, true);
        }
    }

    public void playerWithBallDisconnected() {
        if (Server.gameStatus.containsValue(false) && Server.gameStatus.size() >= 1) {
            Map.Entry<Integer, Boolean> previous = Server.gameStatus.lastEntry();
            System.out.println("Automatically passing ball to ClientID: " + previous.getKey());
            Server.gameStatus.replace(previous.getKey(), true);

        }
        if (Server.gameStatus.isEmpty()) {
            System.out.println("No Players in the lobby :(");
        }
    }
}
