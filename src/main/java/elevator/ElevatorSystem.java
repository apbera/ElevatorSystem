package elevator;

import utils.ElevatorDirection;
import utils.OrderDirection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystem {

    private final int floorsAmount;
    private final int elevatorsAmount;

    List<Elevator> elevatorsList;

    public ElevatorSystem(int floorsAmount, int elevatorsAmount) {
        this.floorsAmount = floorsAmount;
        this.elevatorsAmount = elevatorsAmount;
        elevatorsList = IntStream.range(0, elevatorsAmount)
                .mapToObj(Elevator::new)
                .collect(Collectors.toList());
    }

    public int pickUp(Integer floor, OrderDirection direction) {
        if (floor >= 0 && floor <= floorsAmount) {
            Elevator elevator = findBestElevator(floor, direction);
            elevator.addOrder(floor);

            return elevator.getId();
        }
        return -1;
    }

    private Elevator findBestElevator(Integer floor, OrderDirection direction) {
        Comparator<Elevator> byFitness = Comparator.comparing(e -> getElevatorFitness(e, floor, direction));

        return elevatorsList.stream().min(byFitness).orElse(null);
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

    public void step() {
        elevatorsList.forEach(this::moveElevator);
    }

    private void moveElevator(Elevator elevator) {
        if (elevator.isIdle()) {
            changeDirectionFromIdle(elevator);
        } else if (elevator.isHeadedUpwards()) {
            moveElevatorUpwards(elevator);
        } else {
            moveElevatorDownwards(elevator);
        }
    }

    private void moveElevatorDownwards(Elevator elevator) {
        if (elevator.hasDownwardsOrders()) {
            elevator.move();
            if (elevator.getCurrentFloor() == elevator.nextDownwardsOrder()) {
                elevator.removeClosestOrder();
            }
        } else {
            if (elevator.hasUpwardsOrders()) {
                elevator.setDirection(ElevatorDirection.UPWARDS);
            } else {
                elevator.setDirection(ElevatorDirection.NONE);
            }
        }
    }

    private void moveElevatorUpwards(Elevator elevator) {
        if (elevator.hasUpwardsOrders()) {
            elevator.move();
            if (elevator.getCurrentFloor() == elevator.nextUpwardsOrder()) {
                elevator.removeClosestOrder();
            }
        } else {
            if (elevator.hasDownwardsOrders()) {
                elevator.setDirection(ElevatorDirection.DOWNWARDS);
            } else {
                elevator.setDirection(ElevatorDirection.NONE);
            }
        }
    }

    private void changeDirectionFromIdle(Elevator elevator) {
        if (elevator.hasOrders()) {
            if (elevator.upwardsOrdersAmount() > elevator.downwardOrdersAmount()) {
                elevator.setDirection(ElevatorDirection.UPWARDS);
            } else {
                elevator.setDirection(ElevatorDirection.DOWNWARDS);
            }
        }
    }

    public List<Elevator> getElevatorsList() {

        return elevatorsList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Elevators: [");
        for (Elevator elevator : elevatorsList) {
            sb.append(elevator.getId())
                    .append(",")
                    .append(elevator.getCurrentFloor())
                    .append(",")
                    .append(elevator.getTargetFloor())
                    .append(";");
        }
        sb.append("]");
        return sb.toString();
    }
}