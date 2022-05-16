package elevator;

import utils.OrderDirection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Optional<Integer> pickup(int floor, OrderDirection direction) {
        if (checkIfPickupArgumentsCorrect(floor)) {
            return elevatorSelector
                    .findBestElevator(floor, direction)
                    .map(bestElevator -> {
                        bestElevator.addOrder(floor);
                        return bestElevator.getId();
                    });
        }
        return Optional.empty();
    }

    private boolean checkIfPickupArgumentsCorrect(int floor){
        return floor >= 0 && floor < floorsAmount;
    }

    public Optional<Integer> update(int elevatorId, int currentFloor, int targetFloor) {
        if (checkIfUpdateArgumentsCorrect(elevatorId, currentFloor, targetFloor)) {
            Elevator elevator = elevatorsList.get(elevatorId);
            elevator.setCurrentFloor(currentFloor);
            elevator.addOrder(targetFloor);
            return Optional.of(elevatorId);
        }
        return Optional.empty();
    }

    private boolean checkIfUpdateArgumentsCorrect(int elevatorId, int currentFloor, int targetFloor) {
        return elevatorId >= 0 && elevatorId < elevatorsAmount
                && currentFloor >= 0 && currentFloor <= floorsAmount
                && targetFloor >= 0 && targetFloor <= floorsAmount;
    }

    public void step() {
        elevatorsList.forEach(elevatorEngine::moveElevator);
    }

    public List<Elevator> getElevatorsList() {

        return elevatorsList;
    }

    public List<ElevatorStatus> status() {
        return elevatorsList.stream()
                .map(ElevatorStatus::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return status().stream().map(ElevatorStatus::toString).collect(Collectors.joining(",", "[", "]"));
    }
}