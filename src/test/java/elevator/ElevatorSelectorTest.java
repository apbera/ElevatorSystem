package elevator;

import org.junit.jupiter.api.Test;
import utils.OrderDirection;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSelectorTest {

    @Test
    public void shouldFindBestElevator(){
        //Given
        List<Elevator> elevators = IntStream.range(0, 5)
                .mapToObj(Elevator::new).toList();
        ElevatorSelector elevatorSelector = new ElevatorSelector(elevators, 10);

        //When
        Optional<Elevator> elevator = elevatorSelector.findBestElevator(4, OrderDirection.UPWARDS);

        //Then
        assertTrue(elevator.isPresent());
        assertEquals(elevators.get(0), elevator.get());
    }
}
