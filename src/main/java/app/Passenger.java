package app;

import elevator.Elevator;

/**
 * Model class which contains data about passenger like pickup floor, target floor
 * and elevator assigned to him.
 */
public class Passenger {
    private final int pickupFloor;
    private final int targetFloor;
    private final Elevator elevator;

    Passenger(int pickupFloor, int targetFloor, Elevator elevator) {
        this.pickupFloor = pickupFloor;
        this.targetFloor = targetFloor;
        this.elevator = elevator;
    }

    int getTargetFloor() {
        return targetFloor;
    }

    Elevator getElevator() {
        return elevator;
    }

    boolean isElevatorOnPickupFloor() {
        return elevator.getCurrentFloor() == pickupFloor;
    }
}
