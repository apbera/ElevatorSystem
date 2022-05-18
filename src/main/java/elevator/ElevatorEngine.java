package elevator;

/**
 * Class responsible for moving and changing direction of elevators.
 */
public class ElevatorEngine {

    void moveElevatorOrChangeDirection(Elevator elevator) {
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
            moveElevatorAndRemoveOrderIfCompleted(elevator, elevator.nextDownwardsOrder());
        } else {
            changeDirection(elevator, elevator.hasUpwardsOrders(), ElevatorDirection.UPWARDS);
        }
    }

    private void moveElevatorUpwards(Elevator elevator) {
        if (elevator.hasUpwardsOrders()) {
            moveElevatorAndRemoveOrderIfCompleted(elevator, elevator.nextUpwardsOrder());
        } else {
            changeDirection(elevator, elevator.hasDownwardsOrders(), ElevatorDirection.DOWNWARDS);
        }
    }

    private void moveElevatorAndRemoveOrderIfCompleted(Elevator elevator, int nextOrder) {
        elevator.move();
        if (elevator.getCurrentFloor() == nextOrder) {
            elevator.removeClosestOrder();
        }
    }

    private void changeDirection(Elevator elevator, boolean hasOrders, ElevatorDirection direction) {
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
