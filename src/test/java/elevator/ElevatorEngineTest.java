package elevator;

import org.junit.jupiter.api.Test;
import utils.ElevatorDirection;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorEngineTest {

    @Test
    public void shouldMoveElevatorCorrectly(){
        //Given
        ElevatorEngine elevatorEngine = new ElevatorEngine();
        Elevator elevator = new Elevator(0);

        //When
        elevator.addOrder(4);
        elevatorEngine.moveElevator(elevator);
        elevatorEngine.moveElevator(elevator);

        //Then
        assertTrue(elevator.isHeadedUpwards());
        assertEquals(1,elevator.getCurrentFloor());
    }
}
