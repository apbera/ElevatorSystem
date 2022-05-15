package elevator;

import org.junit.jupiter.api.Test;
import utils.OrderDirection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemTest {


    @Test
    public void shouldOrderBestElevator() {
        //Given
        ElevatorSystem elevatorSystem = new ElevatorSystem(10, 4);

        //When
        int bestElevatorId = elevatorSystem.pickUp(4, OrderDirection.UPWARDS);

        //Then
        assertEquals(bestElevatorId, 0);
    }

    @Test
    public void checkIfStepWorking() {
        //Given
        ElevatorSystem elevatorSystem = new ElevatorSystem(10, 4);

        //When
        elevatorSystem.pickUp(4, OrderDirection.DOWNWARDS);
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        List<Elevator> elevatorList = elevatorSystem.getElevatorsList();
        Elevator elevator = elevatorList.get(0);

        //Then
        assertEquals(2, elevator.getCurrentFloor());
    }


}
