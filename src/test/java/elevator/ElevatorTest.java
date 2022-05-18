package elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    Elevator elevator;

    @BeforeEach
    public void setUp(){
        elevator = new Elevator(0);
        elevator.setCurrentFloor(5);
    }

    @Test
    public void shouldAddUpwardsOrderCorrectly() {
        //When
        elevator.addOrder(7);

        //Then
        assertEquals(1, elevator.upwardsOrdersAmount());
        assertEquals(7, elevator.nextUpwardsOrder());
    }

    @Test
    public void shouldAddDownwardsOrderCorrectly() {
        //When
        elevator.addOrder(3);

        //Then
        assertEquals(1, elevator.downwardOrdersAmount());
        assertEquals(3, elevator.nextDownwardsOrder());
    }

    @Test
    public void shouldAddDownwardsAndUpwardsOrdersCorrectly(){
        //When
        elevator.addOrder(7);
        elevator.addOrder(6);
        elevator.addOrder(2);

        //Then
        assertEquals(2, elevator.upwardsOrdersAmount());
        assertEquals(1, elevator.downwardOrdersAmount());
    }

    @Test
    public void shouldNotAddOrderToCurrentFloor() {
        //When
        elevator.addOrder(0);

        //Then
        assertEquals(0, elevator.upwardsOrdersAmount());
    }

    @Test
    public void shouldBeHeadedTowardsOrder() {
        //When
        elevator.setDirection(ElevatorDirection.UPWARDS);

        //Then
        assertTrue(elevator.isHeadedTowardsOrder(6));
    }

    @Test
    public void shouldNotBeHeadedTowardsOrder() {
        //When
        elevator.setDirection(ElevatorDirection.DOWNWARDS);

        //Then
        assertFalse(elevator.isHeadedTowardsOrder(6));
    }

    @Test
    public void shouldBeHeadedTowardsOrderWhenOrderIsCurrentFlor() {
        //When
        elevator.setDirection(ElevatorDirection.UPWARDS);

        //Then
        assertTrue(elevator.isHeadedTowardsOrder(5));
    }

    @Test
    public void shouldRemoveOrderWhenMovingDownwards(){
        //Given
        elevator.addOrder(7);
        elevator.addOrder(6);
        elevator.addOrder(2);
        elevator.setDirection(ElevatorDirection.DOWNWARDS);

        //When
        elevator.removeClosestOrder();

        //Then
        assertFalse(elevator.hasDownwardsOrders());
    }

    @Test
    public void shouldRemoveOrderWhenMovingUpwards(){
        //Given
        elevator.addOrder(7);
        elevator.addOrder(6);
        elevator.addOrder(2);
        elevator.setDirection(ElevatorDirection.UPWARDS);

        //When
        elevator.removeClosestOrder();

        //Then
        assertEquals(1,elevator.upwardsOrdersAmount());
    }
}
