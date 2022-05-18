package elevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorEngineTest {

    ElevatorSystem elevatorSystem;
    ElevatorEngine elevatorEngine;

    @BeforeEach
    public void setUp(){
        elevatorSystem = new ElevatorSystem(10, 1);
        elevatorEngine = new ElevatorEngine();
    }

    @Test
    public void shouldChangeElevatorDirectionToUpwardsFromIdle(){
        //Given
        elevatorSystem.update(0,5,9);
        Elevator elevator = elevatorSystem.getElevatorsList().get(0);

        //When
        elevatorEngine.moveElevatorOrChangeDirection(elevator);

        //Then
        assertTrue(elevator.isHeadedUpwards());
    }

    @Test
    public void shouldChangeElevatorDirectionAndMoveOnCorrectFloor(){
        //Given
        elevatorSystem.update(0,5,9);
        Elevator elevator = elevatorSystem.getElevatorsList().get(0);

        //When
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        //Then
        assertTrue(elevator.isHeadedUpwards());
        assertEquals(6,elevator.getCurrentFloor());
    }

    @Test
    public void shouldChangeElevatorToDownwardsAfterLastUpwardsOrder(){
        //Given
        elevatorSystem.update(0,5,6);
        Elevator elevator = elevatorSystem.getElevatorsList().get(0);
        elevator.addOrder(2);

        //When
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);

        //Then
        assertTrue(elevator.isHeadedDownwards());
    }

    @Test
    public void elevatorShouldBecomeIdleAfterReachingLastOrder(){
        //Given
        elevatorSystem.update(0,5,6);
        Elevator elevator = elevatorSystem.getElevatorsList().get(0);

        //When
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);

        //Then
        assertTrue(elevator.isIdle());
    }

    @Test
    public void elevatorShouldNotMoveFurtherWhenReachingLastFloor(){
        //Given
        elevatorSystem.update(0,8,9);
        Elevator elevator = elevatorSystem.getElevatorsList().get(0);

        //When
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);
        elevatorEngine.moveElevatorOrChangeDirection(elevator);

        //Then
        assertTrue(elevator.isIdle());
    }
}
