package app;

import elevator.ElevatorSystem;
import utils.OrderDirection;

import java.util.Optional;

public class OptionsHandler {

    private final ElevatorSystem elevatorSystem;

    OptionsHandler(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    void handleOption(String[] optionsArguments) {
        switch (optionsArguments[0]) {
            case "step" -> handleStep(optionsArguments);
            case "pickup" -> handlePickup(optionsArguments);
            case "update" -> handleUpdate(optionsArguments);
            default -> System.out.println("There is no such an option");
        }
    }

    private void handleStep(String[] arguments) {
        elevatorSystem.step();
        System.out.println(elevatorSystem);
    }

    private void handlePickup(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("Wrong number of arguments");
            return;
        }

        int pickupFloor = Integer.parseInt(arguments[1]);
        OrderDirection orderDirection;
        if (arguments[2].equals("up")) {
            orderDirection = OrderDirection.UPWARDS;
        } else if (arguments[2].equals("down")) {
            orderDirection = OrderDirection.DOWNWARDS;
        } else {
            System.out.println("Wrong direction");
            return;
        }
        Optional<Integer> elevatorId = elevatorSystem.pickup(pickupFloor, orderDirection);
        if (elevatorId.isEmpty()) {
            System.out.println("Wrong pickup floor number");
        }
    }

    private void handleUpdate(String[] arguments) {
        if (arguments.length != 4) {
            System.out.println("Wrong number of arguments");
            return;
        }

        int elevatorId = Integer.parseInt(arguments[1]);
        int currentFloor = Integer.parseInt(arguments[2]);
        int targetFloor = Integer.parseInt(arguments[3]);

        Optional<Integer> updatedElevatorId = elevatorSystem.update(elevatorId, currentFloor, targetFloor);
        if (updatedElevatorId.isEmpty()) {
            System.out.println("Wrong update parameters");
        }
    }
}
