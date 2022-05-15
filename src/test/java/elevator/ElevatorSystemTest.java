package elevator;

import org.junit.jupiter.api.Test;
import utils.OrderDirection;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemTest {


    @Test
    public void shouldOrderBestElevator(){
        //Given
        ElevatorSystem elevatorSystem = new ElevatorSystem(10,4);

        //When
        int bestElevatorId = elevatorSystem.pickUp(4, OrderDirection.UPWARDS);

        //Then
        assertEquals(bestElevatorId, 0);
    }
}
