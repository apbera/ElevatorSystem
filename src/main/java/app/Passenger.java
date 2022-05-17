package app;

import elevator.Elevator;

public class Passenger {
    private final int pickupFloor;
    private final int targetFloor;
    private final Elevator elevator;

    public Passenger(int pickupFloor, int targetFloor, Elevator elevator) {
        this.pickupFloor = pickupFloor;
        this.targetFloor = targetFloor;
        this.elevator = elevator;
    }

    public int getPickupFloor() {
        return pickupFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public boolean isElevatorOnPickupFloor() {
        return elevator.getCurrentFloor() == pickupFloor;
    }
}
