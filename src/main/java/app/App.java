package app;

import elevator.ElevatorSystem;
import utils.OrderDirection;

import java.util.Optional;
import java.util.Scanner;

public class App {
    private static ElevatorSystem elevatorSystem;
    private static int floorsAmount;
    private static int elevatorsAmount;

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

        try{
            floorsAmount = Integer.parseInt(args[0]);
            elevatorsAmount = Integer.parseInt(args[1]);

            if(floorsAmount < 0 || elevatorsAmount < 0){
                System.out.println("Arguments can't be less than 0");
                System.exit(1);
            }
            if(elevatorsAmount > 16){
                System.out.println("System supports up to 16 elevators");
                System.exit(1);
            }

        } catch (NumberFormatException ex){
            System.out.println("Arguments need to be Integers: floorsAmount, elevatorsAmount");
            System.exit(1);
        }
    }


    private static void printOptions() {
        System.out.println("""
                Elevator System Aplication
                Options:
                step [numberOfSteps]
                pickup [floorNumber] [direction]
                exit
                """);
    }

    private static void readInstructions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String instruction = scanner.nextLine();
            if (instruction.equals("exit")) {
                return;
            }
            String[] splitInstruction = instruction.split("\\s+");
            if (splitInstruction[0].equals("step")) {
                elevatorSystem.step();
                System.out.println(elevatorSystem);
            } else if (splitInstruction[0].equals("pickup") && splitInstruction.length == 3) {
                int pickupFloor = Integer.parseInt(splitInstruction[1]);
                OrderDirection orderDirection;
                if (splitInstruction[2].equals("up")) {
                    orderDirection = OrderDirection.UPWARDS;
                } else if (splitInstruction[2].equals("down")) {
                    orderDirection = OrderDirection.DOWNWARDS;
                } else {
                    System.out.println("Wrong direction");
                    continue;
                }
                Optional<Integer> elevatorId = elevatorSystem.pickup(pickupFloor, orderDirection);
                if(elevatorId.isEmpty()){
                    System.out.println("Argument for pickup must be integer beetwen 0 and " + floorsAmount);
                }
            }
        }
    }

}
