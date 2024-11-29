import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    @Test
    public void testConstructorInitialization() {
        // Covers constructor
        Ship ship = new Ship(3);

        assertEquals(-1, ship.getRow(), "Initial row should be -1");
        assertEquals(-1, ship.getCol(), "Initial col should be -1");
        assertEquals(3, ship.getLength(), "Length should be initialized to 3");
        assertEquals(Ship.UNSET, ship.getDirection(), "Initial direction should be UNSET");
    }

    @Test
    public void testIsLocationSet() {
        // Covers both branches of isLocationSet()
        Ship ship = new Ship(3);

        assertFalse(ship.isLocationSet(), "isLocationSet should return false when row and col are -1");

        ship.setLocation(2, 3);
        assertTrue(ship.isLocationSet(), "isLocationSet should return true when row and col are set");
    }

    @Test
    public void testIsDirectionSet() {
        // Covers both branches of isDirectionSet()
        Ship ship = new Ship(3);

        assertFalse(ship.isDirectionSet(), "isDirectionSet should return false when direction is UNSET");

        ship.setDirection(Ship.HORIZONTAL);
        assertTrue(ship.isDirectionSet(), "isDirectionSet should return true when direction is set to HORIZONTAL");

        ship.setDirection(Ship.VERTICAL);
        assertTrue(ship.isDirectionSet(), "isDirectionSet should return true when direction is set to VERTICAL");
    }

    @Test
    public void testSetLocation() {
        // Covers setLocation()
        Ship ship = new Ship(3);

        ship.setLocation(4, 5);
        assertEquals(4, ship.getRow(), "Row should be set to 4");
        assertEquals(5, ship.getCol(), "Col should be set to 5");
    }

    @Test
    public void testSetDirectionValid() {
        // Covers setDirection() with valid directions
        Ship ship = new Ship(3);

        ship.setDirection(Ship.HORIZONTAL);
        assertEquals(Ship.HORIZONTAL, ship.getDirection(), "Direction should be set to HORIZONTAL");

        ship.setDirection(Ship.VERTICAL);
        assertEquals(Ship.VERTICAL, ship.getDirection(), "Direction should be set to VERTICAL");

        ship.setDirection(Ship.UNSET);
        assertEquals(Ship.UNSET, ship.getDirection(), "Direction should be set to UNSET");
    }

    @Test
    public void testSetDirectionInvalid() {
        // Covers exception-throwing path in setDirection()
        Ship ship = new Ship(3);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ship.setDirection(999); // Invalid direction
        });

        assertEquals("Invalid direction. It must be -1, 0, or 1", exception.getMessage(), "Should throw exception for invalid direction");
    }

    @Test
    public void testGetRowAndCol() {
        // Covers getRow() and getCol()
        Ship ship = new Ship(3);

        ship.setLocation(7, 8);
        assertEquals(7, ship.getRow(), "getRow should return the correct row");
        assertEquals(8, ship.getCol(), "getCol should return the correct column");
    }

    @Test
    public void testGetLength() {
        // Covers getLength()
        Ship ship = new Ship(4);
        assertEquals(4, ship.getLength(), "getLength should return the correct length");
    }

    @Test
    public void testGetDirection() {
        // Covers getDirection()
        Ship ship = new Ship(3);

        ship.setDirection(Ship.VERTICAL);
        assertEquals(Ship.VERTICAL, ship.getDirection(), "getDirection should return the correct direction");
    }

    @Test
    public void testToString() {
        // Covers toString() with different directions
        Ship ship = new Ship(5);

        ship.setLocation(2, 3);
        ship.setDirection(Ship.HORIZONTAL);
        String expectedHorizontal = "Ship: 2, 3 with length 5 and direction HORIZONTAL";
        assertEquals(expectedHorizontal, ship.toString(), "toString should return the correct string for HORIZONTAL direction");

        ship.setDirection(Ship.VERTICAL);
        String expectedVertical = "Ship: 2, 3 with length 5 and direction VERTICAL";
        assertEquals(expectedVertical, ship.toString(), "toString should return the correct string for VERTICAL direction");

        ship.setDirection(Ship.UNSET);
        String expectedUnset = "Ship: 2, 3 with length 5 and direction UNSET";
        assertEquals(expectedUnset, ship.toString(), "toString should return the correct string for UNSET direction");
    }
}
