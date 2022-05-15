package elevator;

import utils.ElevatorDirection;
import utils.OrderDirection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystem {

    private final int floorsAmount;
    private final int elevatorsAmount;

    List<Elevator> elevatorsList;

    public ElevatorSystem(int floorsAmount, int elevatorsAmount) {
        this.floorsAmount = floorsAmount;
        this.elevatorsAmount = elevatorsAmount;
        elevatorsList = IntStream.range(0,elevatorsAmount)
                .mapToObj(Elevator::new)
                .collect(Collectors.toList());
    }

    public int pickUp(Integer floor, OrderDirection direction){
        Elevator elevator = findBestElevator(floor, direction);
        elevator.addOrder(floor);

        return elevator.getId();
    }

    private Elevator findBestElevator(Integer floor, OrderDirection direction){

        Elevator bestElevator = null;
        int bestElevatorFitness = Integer.MAX_VALUE;

        for (Elevator currentElevator:elevatorsList) {
            int currentFitness = getElevatorFitness(currentElevator, floor, direction);
            if(bestElevatorFitness > currentFitness){
                bestElevatorFitness = currentFitness;
                bestElevator = currentElevator;
            }
        }

        return bestElevator;
    }

    private int getElevatorFitness(Elevator elevator, Integer orderedFloor, OrderDirection direction) {

        if (elevator.isIdle()) {

            return getSameDirectionOrIdleFitness(elevator, orderedFloor);

        } else if(elevator.isHeadedTowardsOrder(orderedFloor)){
            if(elevator.isSameDirection(direction)){
                return getSameDirectionOrIdleFitness(elevator, orderedFloor);
            }else {
                return getOppositeDirectionFitness(elevator, orderedFloor);
            }
        } else {
            return getOppositeDirectionFitness(elevator, orderedFloor);
        }
    }

    private int getOppositeDirectionFitness(Elevator elevator, Integer orderedFloor) {
        return floorsAmount + Math.abs(elevator.getCurrentFloor() - orderedFloor);
    }

    private int getSameDirectionOrIdleFitness(Elevator elevator, Integer orderedFloor) {

        return Math.abs(elevator.getCurrentFloor() - orderedFloor);
    }

    public void update(){

    }

    public void step(){
        for (Elevator elevator:elevatorsList) {
            elevator.move();
        }
    }

    public List<Elevator> status(){

        return elevatorsList;
    }

}