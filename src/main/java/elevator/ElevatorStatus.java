package elevator;

public class ElevatorStatus {
    private final int elevatorId;
    private final int currentFloor;
    private final int targetFloor;

    public ElevatorStatus(Elevator elevator) {
        this.elevatorId = elevator.getId();
        this.currentFloor = elevator.getCurrentFloor();
        this.targetFloor = elevator.getTargetFloor();
    }

    @Override
    public String toString() {
        return "{ E" + elevatorId + ", CF:" + currentFloor + ", TF:" + targetFloor + " }";
    }
}
