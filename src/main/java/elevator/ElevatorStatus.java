package elevator;

/**
 * Class used to store status data about each elevator.
 * Contained data are id of elevator, current floor and target floor.
 */
public class ElevatorStatus {
    private final int elevatorId;
    private final int currentFloor;
    private final int targetFloor;

    ElevatorStatus(Elevator elevator) {
        this.elevatorId = elevator.getId();
        this.currentFloor = elevator.getCurrentFloor();
        this.targetFloor = elevator.getTargetFloor();
    }

    /**
     * @return human-readable data about status of elevator.
     */
    @Override
    public String toString() {
        return "{ E" + elevatorId + ", CF:" + currentFloor + ", TF:" + targetFloor + " }";
    }
}
