package elevator;

import org.junit.jupiter.api.Test;
import utils.ElevatorDirection;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    @Test
    public void shouldAddUpwardsOrderCorrectly() {
        //Given
        Elevator elevator = new Elevator(0);

        //When
        elevator.addOrder(3);

        //Then
        assertEquals(elevator.upwardsOrdersAmount(), 1);
        assertEquals(elevator.nextUpwardsOrder(), 3);
    }

    @Test
    public void shouldNotAddOrders() {
        //Given
        Elevator elevator = new Elevator(0);

        //When
        elevator.addOrder(0);

        //Then
        assertEquals(elevator.upwardsOrdersAmount(), 0);
    }

    @Test
    public void shouldBeHeadedTowardsOrder() {
        //Given
        Elevator elevator = new Elevator(0);

        //When
        elevator.setDirection(ElevatorDirection.UPWARDS);

        //Then
        assertTrue(elevator.isHeadedTowardsOrder(4));
    }

    @Test
    public void shouldNotBeHeadedTowardsOrder() {
        //Given
        Elevator elevator = new Elevator(0);

        //When
        elevator.setDirection(ElevatorDirection.DOWNWARDS);

        //Then
        assertFalse(elevator.isHeadedTowardsOrder(4));
    }
}
