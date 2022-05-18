package elevator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class responsible for controlling elevators in system.
 * Stores information about floors and elevators amount.
 * Uses list to store elevator objects.
 */
public class ElevatorSystem {

    private final int floorsAmount;
    private final int elevatorsAmount;
    private final List<Elevator> elevatorsList;

    private final ElevatorSelector elevatorSelector;
    private final ElevatorEngine elevatorEngine;

    public ElevatorSystem(int floorsAmount, int elevatorsAmount) {
        this.floorsAmount = floorsAmount;
        this.elevatorsAmount = elevatorsAmount;
        elevatorsList = IntStream.range(0, elevatorsAmount)
                .mapToObj(Elevator::new)
                .toList();
        this.elevatorSelector = new ElevatorSelector(elevatorsList, floorsAmount);
        this.elevatorEngine = new ElevatorEngine();
    }

    /**
     * Method responsible for assigning best suited elevator to floor where button was pressed.
     *
     * @param floor     number of floor where button was pressed.
     * @param direction direction of pressed button.
     * @return id of assigned elevator if picked up successfully.
     */
    public Optional<Integer> pickup(int floor, OrderDirection direction) {
        if (arePickupArgumentsCorrect(floor)) {
            return elevatorSelector
                    .findBestElevator(floor, direction)
                    .map(bestElevator -> {
                        bestElevator.addOrder(floor);
                        return bestElevator.getId();
                    });
        }
        return Optional.empty();
    }

    private boolean arePickupArgumentsCorrect(int floor) {
        return floor >= 0 && floor < floorsAmount;
    }

    /**
     * Method responsible for setting up starting and target floor for Idle elevators.
     *
     * @param elevatorId   id of elevator to update.
     * @param currentFloor number of floor to change elevator's current floor.
     * @param targetFloor  number of target floor to assign to elevator's orders.
     * @return id of elevator if update was successful.
     */
    public Optional<Integer> update(int elevatorId, int currentFloor, int targetFloor) {
        if (!areUpdateArgumentsCorrect(elevatorId, currentFloor, targetFloor)) {
            return Optional.empty();
        }
        Elevator elevator = elevatorsList.get(elevatorId);
        if (elevator.isIdle()) {
            elevator.setCurrentFloor(currentFloor);
            elevator.addOrder(targetFloor);
            return Optional.of(elevatorId);
        }
        System.out.println("You can update only Idle elevators");
        return Optional.empty();
    }

    private boolean areUpdateArgumentsCorrect(int elevatorId, int currentFloor, int targetFloor) {
        return elevatorId >= 0 && elevatorId < elevatorsAmount
                && currentFloor >= 0 && currentFloor <= floorsAmount
                && targetFloor >= 0 && targetFloor <= floorsAmount;
    }

    /**
     * Method responsible for assigning order to elevator.
     *
     * @param elevatorId  id of elevator to assign order.
     * @param targetFloor number of target floor
     */
    public void order(int elevatorId, int targetFloor) {
        if (areOrderArgumentsCorrect(elevatorId, targetFloor)) {
            Elevator elevator = elevatorsList.get(elevatorId);
            elevator.addOrder(targetFloor);
            return;
        }
        System.out.println("Order error");
    }

    private boolean areOrderArgumentsCorrect(int elevatorId, int targetFloor) {
        return elevatorId >= 0 && elevatorId < elevatorsAmount
                && targetFloor >= 0 && targetFloor <= floorsAmount;
    }

    /**
     * Method responsible for taking single step, for each elevator.
     * Step is moving elevator or changing its direction.
     */
    public void step() {
        elevatorsList.forEach(elevatorEngine::moveElevatorOrChangeDirection);
    }

    /**
     * @return list of elevators.
     */
    public List<Elevator> getElevatorsList() {
        return elevatorsList;
    }

    /**
     * Method responsible for creating a list of elevator's status.
     *
     * @return list of statuses [(id, current floor, target floor), ...].
     */
    public List<ElevatorStatus> status() {
        return elevatorsList.stream()
                .map(ElevatorStatus::new)
                .toList();
    }

    /**
     * @return human-readable data about status of elevators.
     */
    @Override
    public String toString() {
        return status().stream().map(ElevatorStatus::toString).collect(Collectors.joining(",", "[", "]"));
    }
}