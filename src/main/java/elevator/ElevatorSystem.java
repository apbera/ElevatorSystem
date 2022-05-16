package elevator;

import utils.ElevatorDirection;
import utils.OrderDirection;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystem {

    private final int floorsAmount;
    List<Elevator> elevatorsList;

    ElevatorSelector elevatorSelector;
    ElevatorEngine elevatorEngine;

    public ElevatorSystem(int floorsAmount, int elevatorsAmount) {
        this.floorsAmount = floorsAmount;
        elevatorsList = IntStream.range(0, elevatorsAmount)
                .mapToObj(Elevator::new)
                .collect(Collectors.toList());
        this.elevatorSelector = new ElevatorSelector(elevatorsList, floorsAmount);
        this.elevatorEngine = new ElevatorEngine();
    }

    public Optional<Integer> pickup(Integer floor, OrderDirection direction) {
        if(floor <0 || floor > floorsAmount){
            return Optional.empty();
        }

        Optional<Elevator> elevator = elevatorSelector.findBestElevator(floor, direction);
        if(elevator.isPresent()){
            elevator.get().addOrder(floor);

            return Optional.of(elevator.get().getId());
        }

        return Optional.empty();
    }

    public void step() {
        elevatorsList.forEach(e -> elevatorEngine.moveElevator(e));
    }

    public List<Elevator> getElevatorsList() {

        return elevatorsList;
    }

    @Override
    public String toString() {
        return elevatorsList.stream().map(Elevator::toString).collect(Collectors.joining(":", "[","]"));
    }
}