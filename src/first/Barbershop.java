package first;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
 
//This class Simulates the barbershop with a main chair and a queue of waiting chairs
public class Barbershop {
    private static final int NUM_WAITING_CHAIRS = 5;
    private String mainChair = null;
    private Queue<String> waitingChairs = new LinkedList<>();

 // This class runs the simulation, generating random events and displaying the state of the barbershop.
    public static void main(String[] args) {
        Barbershop shop = new Barbershop();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int ordCount = 1;
        int vipCount = 1;

        while (true) {
            System.out.println("Press space and enter to generate an event or any other key to quit:");
            String input = scanner.nextLine();

            if (!input.equals(" ")) {
                break;
            }

            int x = random.nextInt(4);
            String event = "";

            switch (x) {
                case 0:
                    event = shop.finishHaircut();
                    break;
                case 1:
                    event = shop.arriveVIP("VIP" + vipCount++);
                    break;
                case 2:
                case 3:
                    event = shop.arriveOrdinary("ORD" + ordCount++);
                    break;
            }

            shop.displayState(x, event);
        }

        scanner.close();
    }
   // This class moves a client from the waiting queue to the main chair when a haircut is finished.
    private String finishHaircut() {
        if (mainChair == null) {
            return "-- none";
        }
        String finishedClient = mainChair;
        mainChair = waitingChairs.poll();
        return "-- " + finishedClient;
    }
    // This class handles the arrival of VIP clients, giving them priority in the waiting queue.
    private String arriveVIP(String vipClient) {
        if (mainChair == null) {
            mainChair = vipClient;
            return "++ " + vipClient;
        } else if (waitingChairs.size() < NUM_WAITING_CHAIRS) {
            Queue<String> tempQueue = new LinkedList<>();
            tempQueue.add(vipClient);

            while (!waitingChairs.isEmpty() && waitingChairs.peek().startsWith("VIP")) {
                tempQueue.add(waitingChairs.poll());
            }

            while (!waitingChairs.isEmpty()) {
                tempQueue.add(waitingChairs.poll());
            }

            waitingChairs = tempQueue;
            return "++ " + vipClient;
        } else {
            return "+- " + vipClient;
        }
    }
  // This class handles the arrival of ordinary clients, placing them in the waiting queue if there's space.
    private String arriveOrdinary(String ordClient) {
        if (mainChair == null) {
            mainChair = ordClient;
            return "++ " + ordClient;
        } else if (waitingChairs.size() < NUM_WAITING_CHAIRS) {
            waitingChairs.add(ordClient);
            return "++ " + ordClient;
        } else {
            return "+- " + ordClient;
        }
    }
     // This class print the current state of the barbering shop,including the main chair and the waiting clients
    private void displayState(int x, String event) {
        System.out.print(x + " ---> (" + event + ") [");

        if (mainChair != null) {
            System.out.print(mainChair);
        }

        for (String client : waitingChairs) {
            System.out.print(" " + client);
        }

        System.out.println(" ]");
    }
}
