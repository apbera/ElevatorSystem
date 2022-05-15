package app;

import elevator.ElevatorSystem;
import utils.OrderDirection;

import java.util.Scanner;

public class App {
    private static ElevatorSystem elevatorSystem;

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Arguments needed: floorsAmount, elevatorsAmount");
            return;
        }

        elevatorSystem = new ElevatorSystem(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        printOptions();

        readInstructions();
    }

    private static void printOptions() {
        System.out.println("""
                Elevator System Aplication
                Options:
                step [numberOfSteps]
                pickup [floorNumber] [direction]
                """);
    }

    private static void readInstructions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String instruction = scanner.nextLine();
            if (instruction.equals("exit")) {
                return;
            }
            String[] splitInstruction = instruction.split(" ");
            if (splitInstruction[0].equals("step")) {
                elevatorSystem.step();
                System.out.println(elevatorSystem);
            } else if (splitInstruction[0].equals("pickup")) {
                int floorsAmount = Integer.parseInt(splitInstruction[1]);
                OrderDirection orderDirection;
                if (splitInstruction[2].equals("up")) {
                    orderDirection = OrderDirection.UPWARDS;
                } else if (splitInstruction[2].equals("down")) {
                    orderDirection = OrderDirection.DOWNWARDS;
                } else {
                    System.out.println("Wrong direction");
                    continue;
                }
                elevatorSystem.pickUp(floorsAmount, orderDirection);
            }
        }
    }
}
