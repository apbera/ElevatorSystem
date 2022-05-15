package elevator;

import utils.ElevatorDirection;
import utils.ElevatorDirection;
import utils.OrderDirection;

import java.util.TreeSet;

public class Elevator {
    private final int id;
    private ElevatorDirection direction;
    private int currentFloor;

    private final TreeSet<Integer> upwardsOrders = new TreeSet<>();
    private final TreeSet<Integer> downwardsOrders = new TreeSet<>();

    public Elevator(int id){
        this.id = id;
        this.direction = ElevatorDirection.NONE;
        this.currentFloor = 0;
    }

    public void addOrder(int orderedFloor){
        if(orderedFloor > currentFloor){
            upwardsOrders.add(orderedFloor);
        } else if(orderedFloor < currentFloor){
            downwardsOrders.add(orderedFloor);
        }
    }

    public void removeClosestOrder(){
        if(direction == ElevatorDirection.UPWARDS){
            upwardsOrders.remove(upwardsOrders.first());
        } else if (direction == ElevatorDirection.DOWNWARDS){
            downwardsOrders.remove(downwardsOrders.last());
        }
    }

    public int getId(){
        return id;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public TreeSet<Integer> getUpwardsOrders(){
        return upwardsOrders;
    }

    public TreeSet<Integer> getDownwardsOrders(){
        return downwardsOrders;
    }

    public boolean isIdle() {
        return direction == ElevatorDirection.NONE;
    }

    public boolean isHeadedTowardsOrder(Integer orderedFloor) {
        return (orderedFloor >= currentFloor && direction == ElevatorDirection.UPWARDS) ||
                (orderedFloor <= currentFloor && direction == ElevatorDirection.DOWNWARDS);
    }

    public boolean isSameDirection(OrderDirection direction) {
        return this.direction == ElevatorDirection.UPWARDS && direction == OrderDirection.UPWARDS ||
                this.direction == ElevatorDirection.DOWNWARDS && direction == OrderDirection.DOWNWARDS;
    }

    public void move() {

    }
}
