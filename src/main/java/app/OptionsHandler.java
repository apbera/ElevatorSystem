package app;

import elevator.Elevator;
import elevator.ElevatorSystem;
import utils.OrderDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class OptionsHandler {

    private final ElevatorSystem elevatorSystem;
    private final List<Passenger> waitingPassengers = new ArrayList<>();

    OptionsHandler(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    void handleOption(String[] optionsArguments) {
        switch (optionsArguments[0]) {
            case "step" -> handleStep(optionsArguments);
            case "pickup" -> handlePickup(optionsArguments);
            case "update" -> handleUpdate(optionsArguments);
            case "order" -> handleOrder(optionsArguments);
            default -> System.out.println("There is no such an option");
        }
    }

    private void handleOrder(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("Wrong number of arguments");
            return;
        }
        int pickupFloor = Integer.parseInt(arguments[1]);
        int destinationFloor = Integer.parseInt(arguments[2]);

        OrderDirection orderDirection = getOrderDirection(pickupFloor, destinationFloor)
                .orElseThrow();
        Integer elevatorId = elevatorSystem.pickup(pickupFloor, orderDirection)
                .orElseThrow();

        Elevator elevator = elevatorSystem.getElevatorsList().get(elevatorId);
        Passenger passenger = new Passenger(pickupFloor,destinationFloor,elevator);
        waitingPassengers.add(passenger);
        System.out.println("Elevator " + elevator.getId() + " is coming for passenger");
    }

    private Optional<OrderDirection> getOrderDirection(int pickupFloor, int destinationFloor) {
        if (destinationFloor > pickupFloor) return Optional.of(OrderDirection.UPWARDS);
        if (destinationFloor < pickupFloor) return Optional.of(OrderDirection.DOWNWARDS);
        System.out.println("You are already on this floor");
        return Optional.empty();
    }

    private void handleStep(String[] arguments) {
        if (arguments.length == 1) {
            makeStep();
        } else if (arguments.length == 2) {
            int stepsAmount = Integer.parseInt(arguments[1]);
            IntStream.range(0, stepsAmount)
                    .forEach(i -> makeStep());
        }
    }

    private void makeStep(){
        elevatorSystem.step();
        System.out.println(elevatorSystem);
        List<Passenger> passengersToRemove = waitingPassengers.stream()
                .filter(Passenger::isElevatorOnPickupFloor).toList();
        passengersToRemove.forEach(p -> elevatorSystem.update(p.getElevator().getId(), p.getPickupFloor(), p.getTargetFloor()));
        waitingPassengers.removeAll(passengersToRemove);
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
        elevatorSystem.pickup(pickupFloor, orderDirection)
                .ifPresentOrElse(id -> System.out.println("Elevator " + id + "ordered on floor" + pickupFloor),
                        () -> System.out.println("Wrong pickup floor number"));
    }

    private void handleUpdate(String[] arguments) {
        if (arguments.length != 4) {
            System.out.println("Wrong number of arguments");
            return;
        }
        int elevatorId = Integer.parseInt(arguments[1]);
        int currentFloor = Integer.parseInt(arguments[2]);
        int targetFloor = Integer.parseInt(arguments[3]);
        elevatorSystem.update(elevatorId, currentFloor, targetFloor)
                .ifPresentOrElse(id ->
                        System.out.println("Elevator " + id + " updated"),
                        () -> System.out.println("Wrong update parameters"));
    }
}
