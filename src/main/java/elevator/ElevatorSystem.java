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
    private final List<ElevatorStatus> elevatorStatusList;

    public ElevatorSystem(int floorsAmount, int elevatorsAmount) {
        this.floorsAmount = floorsAmount;
        this.elevatorsAmount = elevatorsAmount;
        elevatorsList = IntStream.range(0, elevatorsAmount)
                .mapToObj(Elevator::new)
                .collect(Collectors.toList());
        this.elevatorSelector = new ElevatorSelector(elevatorsList, floorsAmount);
        this.elevatorEngine = new ElevatorEngine();
        this.elevatorStatusList = elevatorsList.stream()
                .map(ElevatorStatus::new)
                .collect(Collectors.toList());
    }

    public Optional<Integer> pickup(Integer floor, OrderDirection direction) {
        if (floor < 0 || floor > floorsAmount) {
            return Optional.empty();
        }

        Optional<Elevator> elevator = elevatorSelector.findBestElevator(floor, direction);
        if (elevator.isPresent()) {
            elevator.get().addOrder(floor);

            return Optional.of(elevator.get().getId());
        }

        return Optional.empty();
    }

    public Optional<Integer> update(int elevatorId, int currentFloor, int targetFloor) {
        if (elevatorId >= 0 && elevatorId < elevatorsAmount) {
            Elevator elevator = elevatorsList.get(elevatorId);
            elevator.setCurrentFloor(currentFloor);
            elevator.addOrder(targetFloor);
            return Optional.of(elevatorId);
        }

        return Optional.empty();
    }

    public void step() {
        elevatorsList.forEach(elevatorEngine::moveElevator);
    }

    public List<Elevator> getElevatorsList() {

        return elevatorsList;
    }

    public List<ElevatorStatus> status() {
        elevatorStatusList.forEach(e -> e.updateStatus(elevatorsList.get(e.getElevatorId())));
        return elevatorStatusList;
    }

    @Override
    public String toString() {
        return status().stream().map(ElevatorStatus::toString).collect(Collectors.joining(":", "[", "]"));
    }
}