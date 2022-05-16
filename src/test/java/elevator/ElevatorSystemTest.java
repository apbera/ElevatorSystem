package elevator;

import org.junit.jupiter.api.Test;
import utils.OrderDirection;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemTest {


    @Test
    public void shouldOrderBestElevator() {
        //Given
        ElevatorSystem elevatorSystem = new ElevatorSystem(10, 4);

        //When
        Optional<Integer> bestElevatorId = elevatorSystem.pickup(4, OrderDirection.UPWARDS);

        //Then
        assertEquals(0, bestElevatorId.get());
    }

    @Test
    public void checkIfStepWorking() {
        //Given
        ElevatorSystem elevatorSystem = new ElevatorSystem(10, 4);

        //When
        elevatorSystem.pickup(4, OrderDirection.DOWNWARDS);
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        List<Elevator> elevatorList = elevatorSystem.getElevatorsList();
        Elevator elevator = elevatorList.get(0);

        //Then
        assertEquals(2, elevator.getCurrentFloor());
    }


}
