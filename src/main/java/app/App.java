package app;

import elevator.ElevatorSystem;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Application class with main method which provides manual interaction with elevator system.
 */
public class App {
    private static ElevatorSystem elevatorSystem;
    private static int floorsAmount;
    private static int elevatorsAmount;

    /**
     * @param args list of options [floors number, elevators number].
     */
    public static void main(String[] args) {

        checkIfArgsCorrect(args);

        elevatorSystem = new ElevatorSystem(floorsAmount, elevatorsAmount);

        printOptions();

        readInstructions();
    }

    private static void checkIfArgsCorrect(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments needed: floorsAmount, elevatorsAmount");
            System.exit(1);
        }

        try {
            floorsAmount = Integer.parseInt(args[0]);
            elevatorsAmount = Integer.parseInt(args[1]);

        } catch (NumberFormatException ex) {
            System.out.println("Arguments need to be Integers: floorsAmount, elevatorsAmount");
            System.exit(2);
        }

        checkElevatorsAndFloorsAmount(floorsAmount, elevatorsAmount);
    }

    private static void checkElevatorsAndFloorsAmount(int floorsAmount, int elevatorsAmount) {
        if (floorsAmount < 0 || elevatorsAmount < 0) {
            System.out.println("Arguments can't be less than 0");
            System.exit(3);
        }
        if (elevatorsAmount > 16) {
            System.out.println("System supports up to 16 elevators");
            System.exit(4);
        }
    }

    private static void printOptions() {
        System.out.println("""
                Elevator System Aplication
                Options:
                step [numberOfSteps]
                pickup [floorNumber] [direction]
                update [elevatorId] [currentFloor] [targetFloor]
                order [pickupFloor] [targetFloor]
                exit
                """);
    }

    private static void readInstructions() {
        Scanner scanner = new Scanner(System.in);
        InstructionsHandler instructionsHandler = new InstructionsHandler(elevatorSystem);
        while (true) {
            String instruction = scanner.nextLine().trim();
            if (instruction.equals("exit")) {
                return;
            }
            String[] splitInstruction = instruction.split("\\s+");
            try {
                InstructionsParser instructionsParser = new InstructionsParser(splitInstruction);
                instructionsHandler.handleInstruction(instructionsParser);
            } catch (NumberFormatException ex) {
                System.out.println("Wrong arguments type");
            } catch (NoSuchElementException ex) {
                System.out.println("Wrong order arguments");
            } catch (IllegalArgumentException ex) {
                System.out.println("Wrong instruction");
            }
        }
    }

}
