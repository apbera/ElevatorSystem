package elevator;

import utils.ElevatorDirection;

public class ElevatorEngine {

    public void moveElevator(Elevator elevator) {
        if (elevator.isIdle() && elevator.hasOrders()) {
            changeDirectionFromIdle(elevator);
        } else if (elevator.isHeadedUpwards()) {
            moveElevatorUpwards(elevator);
        } else {
            moveElevatorDownwards(elevator);
        }
    }

    private void moveElevatorDownwards(Elevator elevator) {
        if (elevator.hasDownwardsOrders()) {
            moveElevatorAndRemoveOrder(elevator, elevator.nextDownwardsOrder());
        } else {
            changeDirectionIfHasOrders(elevator, elevator.hasUpwardsOrders(), ElevatorDirection.UPWARDS);
        }
    }

    private void moveElevatorUpwards(Elevator elevator) {
        if (elevator.hasUpwardsOrders()) {
            moveElevatorAndRemoveOrder(elevator, elevator.nextUpwardsOrder());
        } else {
            changeDirectionIfHasOrders(elevator, elevator.hasDownwardsOrders(), ElevatorDirection.DOWNWARDS);
        }
    }

    private void moveElevatorAndRemoveOrder(Elevator elevator, int nextOrder) {
        elevator.move();
        if (elevator.getCurrentFloor() == nextOrder) {
            elevator.removeClosestOrder();
        }
    }

    private void changeDirectionIfHasOrders(Elevator elevator, boolean hasOrders, ElevatorDirection direction) {
        if (hasOrders) {
            elevator.setDirection(direction);
        } else {
            elevator.setDirection(ElevatorDirection.NONE);
        }
    }

    private void changeDirectionFromIdle(Elevator elevator) {
        if (elevator.upwardsOrdersAmount() > elevator.downwardOrdersAmount()) {
            elevator.setDirection(ElevatorDirection.UPWARDS);
        } else {
            elevator.setDirection(ElevatorDirection.DOWNWARDS);
        }
    }
}
