package elevator;

import utils.OrderDirection;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ElevatorSelector {
    private final int floorsAmount;

    List<Elevator> elevatorsList;

    ElevatorSelector(List<Elevator> elevatorsList, int floorsAmount){
        this.elevatorsList = elevatorsList;
        this.floorsAmount = floorsAmount;
    }

    public Optional<Elevator> findBestElevator(Integer floor, OrderDirection direction) {
        Comparator<Elevator> byFitness = Comparator.comparing(e -> getElevatorFitness(e, floor, direction));

        return elevatorsList.stream().min(byFitness);
    }

    private int getElevatorFitness(Elevator elevator, Integer orderedFloor, OrderDirection direction) {

        if (elevator.isIdle()) {
            return getSameDirectionOrIdleFitness(elevator, orderedFloor);
        } else if (elevator.isHeadedTowardsOrder(orderedFloor)) {
            if (elevator.isSameDirection(direction)) {
                return getSameDirectionOrIdleFitness(elevator, orderedFloor);
            } else {
                return getOppositeDirectionFitness(elevator, orderedFloor);
            }
        } else {
            return getOppositeDirectionFitness(elevator, orderedFloor);
        }
    }

    private int getOppositeDirectionFitness(Elevator elevator, Integer orderedFloor) {
        return floorsAmount + Math.abs(elevator.getCurrentFloor() - orderedFloor);
    }

    private int getSameDirectionOrIdleFitness(Elevator elevator, Integer orderedFloor) {

        return Math.abs(elevator.getCurrentFloor() - orderedFloor);
    }
}
