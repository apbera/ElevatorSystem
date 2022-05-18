package app;

import elevator.Elevator;
import elevator.ElevatorSystem;
import elevator.OrderDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Class responsible for handling user instruction.
 * List of instruction: step, pickup, update, order.
 */
public class InstructionsHandler {

    private final ElevatorSystem elevatorSystem;
    private final List<Passenger> waitingPassengers = new ArrayList<>();

    InstructionsHandler(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    void handleInstruction(InstructionsParser parser) {
        int[] arguments = parser.getInstructionArguments();
        switch (parser.getInstruction()) {
            case "status" -> handleStatus();
            case "step" -> handleStep(arguments[0]);
            case "pickup" -> handlePickup(arguments[0], parser.getPickupDirection());
            case "update" -> handleUpdate(arguments[0], arguments[1], arguments[2]);
            case "order" -> handleOrder(arguments[0], arguments[1]);
        }
    }

    private void handleStatus() {
        System.out.println(elevatorSystem);
    }

    private void handleOrder(int pickupFloor, int destinationFloor) {
        OrderDirection orderDirection = getOrderDirection(pickupFloor, destinationFloor)
                .orElseThrow();
        Integer elevatorId = elevatorSystem.pickup(pickupFloor, orderDirection)
                .orElseThrow();
        Elevator elevator = elevatorSystem.getElevatorsList().get(elevatorId);
        Passenger passenger = new Passenger(pickupFloor, destinationFloor, elevator);
        waitingPassengers.add(passenger);
        System.out.println("Elevator " + elevator.getId() + " is coming for passenger");
    }

    private Optional<OrderDirection> getOrderDirection(int pickupFloor, int destinationFloor) {
        if (destinationFloor > pickupFloor) return Optional.of(OrderDirection.UPWARDS);
        if (destinationFloor < pickupFloor) return Optional.of(OrderDirection.DOWNWARDS);
        System.out.println("You are already on this floor");
        return Optional.empty();
    }

    private void handleStep(int steps) {
        IntStream.range(0, steps)
                .forEach(i -> makeStepAndHandlePassengers());
    }

    private void makeStepAndHandlePassengers() {
        elevatorSystem.step();
        System.out.println(elevatorSystem);
        List<Passenger> passengersToRemove = waitingPassengers.stream()
                .filter(Passenger::isElevatorOnPickupFloor).toList();
        passengersToRemove.forEach(p -> elevatorSystem.order(p.getElevator().getId(), p.getTargetFloor()));
        waitingPassengers.removeAll(passengersToRemove);
    }

    private void handlePickup(int pickupFloor, OrderDirection orderDirection) {
        elevatorSystem.pickup(pickupFloor, orderDirection)
                .ifPresentOrElse(id -> System.out.println("Elevator " + id + "ordered on floor" + pickupFloor),
                        () -> System.out.println("Wrong pickup floor number"));
    }

    private void handleUpdate(int elevatorId, int currentFloor, int targetFloor) {
        elevatorSystem.update(elevatorId, currentFloor, targetFloor)
                .ifPresentOrElse(id -> System.out.println("Elevator " + id + " updated"),
                        () -> System.out.println("Wrong update parameters"));
    }
}
