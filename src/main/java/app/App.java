package app;

import elevator.ElevatorSystem;

/**
 * Application class with main method which provides manual interaction with elevator system.
 */
public class App {
    /**
     * @param args list of options [floors number, elevators number].
     */
    public static void main(String[] args) {
        OptionsParser optionsParser = new OptionsParser();
        if (!optionsParser.checkIfArgsCorrectAndParse(args)) {
            return;
        }
        int floorsAmount = optionsParser.getFloorsAmount();
        int elevatorsAmount = optionsParser.getElevatorsAmount();
        ElevatorSystem elevatorSystem = new ElevatorSystem(floorsAmount, elevatorsAmount);
        System.out.println("""
                Elevator System Aplication
                Options:
                step [numberOfSteps]
                pickup [floorNumber] [direction]
                update [elevatorId] [currentFloor] [targetFloor]
                order [pickupFloor] [targetFloor]
                exit
                """);
        new InstructionsReader().readInstructions(elevatorSystem);
    }
}
