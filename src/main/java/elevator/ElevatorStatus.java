package elevator;

public class ElevatorStatus {
    private final int elevatorId;
    private int currentFloor;
    private int targetFloor;

    public ElevatorStatus(Elevator elevator) {
        this.elevatorId = elevator.getId();
        this.currentFloor = elevator.getCurrentFloor();
        this.targetFloor = elevator.getTargetFloor();
    }

    public void updateStatus(Elevator elevator) {
        currentFloor = elevator.getCurrentFloor();
        targetFloor = elevator.getTargetFloor();
    }

    public int getElevatorId() {
        return elevatorId;
    }

    @Override
    public String toString() {
        return elevatorId + "," + currentFloor + "," + targetFloor;
    }
}
