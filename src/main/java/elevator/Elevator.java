package elevator;

import java.util.TreeSet;

/**
 * Model class for one elevator.
 * It stores data about direction, current floor and uses tree sets to store orders.
 */
public class Elevator {
    private final int id;
    private ElevatorDirection direction;
    private int currentFloor;

    private final TreeSet<Integer> upwardsOrders = new TreeSet<>();
    private final TreeSet<Integer> downwardsOrders = new TreeSet<>();

    Elevator(int id) {
        this.id = id;
        this.direction = ElevatorDirection.NONE;
        this.currentFloor = 0;
    }

    void addOrder(int orderedFloor) {
        if (orderedFloor > currentFloor) {
            upwardsOrders.add(orderedFloor);
        } else if (orderedFloor < currentFloor) {
            downwardsOrders.add(orderedFloor);
        }
    }

    void removeClosestOrder() {
        if (direction == ElevatorDirection.UPWARDS) {
            upwardsOrders.remove(upwardsOrders.first());
        } else if (direction == ElevatorDirection.DOWNWARDS) {
            downwardsOrders.remove(downwardsOrders.last());
        }
    }

    void move() {
        if (isHeadedUpwards()) {
            this.currentFloor++;
        } else if (isHeadedDownwards()) {
            this.currentFloor--;
        }
    }

    /**
     * @return id of elevator.
     */
    public int getId() {
        return id;
    }

    /**
     * @return current floor of elevator.
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    boolean isIdle() {
        return direction == ElevatorDirection.NONE;
    }

    boolean isHeadedUpwards() {
        return direction == ElevatorDirection.UPWARDS;
    }

    boolean isHeadedDownwards() {
        return direction == ElevatorDirection.DOWNWARDS;
    }

    boolean isHeadedTowardsOrder(Integer orderedFloor) {
        return (orderedFloor >= currentFloor && direction == ElevatorDirection.UPWARDS) ||
                (orderedFloor <= currentFloor && direction == ElevatorDirection.DOWNWARDS);
    }

    boolean isSameDirection(OrderDirection direction) {
        return this.direction == ElevatorDirection.UPWARDS && direction == OrderDirection.UPWARDS ||
                this.direction == ElevatorDirection.DOWNWARDS && direction == OrderDirection.DOWNWARDS;
    }

    boolean hasOrders() {
        return !upwardsOrders.isEmpty() || !downwardsOrders.isEmpty();
    }

    int upwardsOrdersAmount() {
        return upwardsOrders.size();
    }

    int downwardOrdersAmount() {
        return downwardsOrders.size();
    }

    int ordersAmount() {
        return upwardsOrders.size() + downwardsOrders.size();
    }

    boolean hasDownwardsOrders() {
        return !downwardsOrders.isEmpty();
    }

    boolean hasUpwardsOrders() {
        return !upwardsOrders.isEmpty();
    }

    int nextDownwardsOrder() {
        return downwardsOrders.last();
    }

    int nextUpwardsOrder() {
        return upwardsOrders.first();
    }

    int getTargetFloor() {
        if (isHeadedUpwards() && hasUpwardsOrders()) {
            return nextUpwardsOrder();
        }
        if (isHeadedDownwards() && hasDownwardsOrders()) {
            return nextDownwardsOrder();
        }
        return getCurrentFloor();
    }

    /**
     * @return human-readable data of elevator.
     */
    @Override
    public String toString() {
        return id + "," + currentFloor + "," + getTargetFloor();
    }
}
