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
        if (elevator.upwardsOrdersAmount() > elevator.downwardOrdersAmount()) {
            elevator.setDirection(ElevatorDirection.UPWARDS);
        } else {
            elevator.setDirection(ElevatorDirection.DOWNWARDS);
        }
    }
}
