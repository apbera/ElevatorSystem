package elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSelectorTest {

    ElevatorSystem elevatorSystem;
    ElevatorSelector elevatorSelector;

    @BeforeEach
    public void setUp(){
        elevatorSystem = new ElevatorSystem(10, 4);
        elevatorSelector = new ElevatorSelector(elevatorSystem.getElevatorsList(), 10);
        elevatorSystem.update(0,0,5);
        elevatorSystem.update(1,9,0);
        elevatorSystem.update(2,6,9);
        elevatorSystem.update(3,5,1);
    }

    @Test
    public void shouldFindBestElevatorCorrectlyIfAllAreIdle(){
        //When
        Optional<Elevator> elevator = elevatorSelector.findBestElevator(4, OrderDirection.UPWARDS);

        //Then
        assertTrue(elevator.isPresent());
        assertEquals(3, elevator.get().getId());
    }

    @Test
    public void shouldFindBestElevatorCorrectlyIfAllAreMoving(){
        //Given
        elevatorSystem.step();

        //When
        Optional<Elevator> elevator = elevatorSelector.findBestElevator(4, OrderDirection.UPWARDS);

        //Then
        assertTrue(elevator.isPresent());
        assertEquals(0, elevator.get().getId());
    }

    @Test
    public void shouldFindBestElevatorCorrectlyIfBestIsBusy(){
        //Given
        elevatorSystem.step();
        elevatorSystem.order(0,8);

        //When
        Optional<Elevator> elevator = elevatorSelector.findBestElevator(4, OrderDirection.UPWARDS);

        //Then
        assertTrue(elevator.isPresent());
        assertEquals(3, elevator.get().getId());
    }

    @Test
    public void shouldFindBestElevatorCorrectlyIfAllAreHeadingAway(){
        //Given
        elevatorSystem.update(0,1,0);
        elevatorSystem.update(1,6,9);
        elevatorSystem.update(2,7,1);
        elevatorSystem.update(3,8,9);

        //When
        Optional<Elevator> elevator = elevatorSelector.findBestElevator(4, OrderDirection.UPWARDS);

        //Then
        assertTrue(elevator.isPresent());
        assertEquals(1, elevator.get().getId());
    }
}
