package elevator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for picking best suited elevators to floor where elevator was ordered.
 */
public class ElevatorSelector {
    private final int floorsAmount;

    private final List<Elevator> elevatorsList;

    ElevatorSelector(List<Elevator> elevatorsList, int floorsAmount) {
        this.elevatorsList = elevatorsList;
        this.floorsAmount = floorsAmount;
    }

    Optional<Elevator> findBestElevator(Integer floor, OrderDirection direction) {
        Comparator<Elevator> byFitness = Comparator.comparing(e -> getElevatorFitness(e, floor, direction));
        return elevatorsList.stream().min(byFitness);
    }

    private int getElevatorFitness(Elevator elevator, Integer orderedFloor, OrderDirection direction) {
        if (isElevatorBusy(elevator)) {
            return getBusyFitness();
        }
        if (elevator.isIdle()) {
            return getSameDirectionOrIdleFitness(elevator, orderedFloor);
        } else if (elevator.isHeadedTowardsOrder(orderedFloor)) {
            return getHeadedTowardsOrderFitness(elevator, orderedFloor, direction);
        } else {
            return getOppositeDirectionFitness(elevator, orderedFloor);
        }
    }

    private int getBusyFitness() {
        return Integer.MAX_VALUE;
    }

    private boolean isElevatorBusy(Elevator elevator) {
        return elevator.hasOrders() && elevator.ordersAmount() > ordersAmountForAllElevators() / elevatorsList.size();
    }

    private int ordersAmountForAllElevators() {
        return elevatorsList.stream().mapToInt(Elevator::ordersAmount).sum();
    }

    private int getHeadedTowardsOrderFitness(Elevator elevator, Integer orderedFloor, OrderDirection direction) {
        if (elevator.isSameDirection(direction)) {
            return getSameDirectionOrIdleFitness(elevator, orderedFloor);
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
