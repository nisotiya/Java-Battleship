import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testConstructorInitialization() {
        Location loc = new Location();
        assertEquals(Location.UNGUESSED, loc.getStatus(), "Initial status should be UNGUESSED");
        assertFalse(loc.hasShip(), "Initial hasShip should be false");
        assertEquals(-1, loc.getLengthOfShip(), "Initial lengthOfShip should be -1");
        assertEquals(-1, loc.getDirectionOfShip(), "Initial directionOfShip should be -1");
    }

    @Test
    public void testCheckHit() {
        Location loc = new Location();

        loc.setStatus(Location.HIT);
        assertTrue(loc.checkHit(), "checkHit should return true when status is HIT");

        loc.setStatus(Location.UNGUESSED);
        assertFalse(loc.checkHit(), "checkHit should return false when status is not HIT");
    }

    @Test
    public void testCheckMiss() {
        Location loc = new Location();

        loc.setStatus(Location.MISSED);
        assertTrue(loc.checkMiss(), "checkMiss should return true when status is MISSED");

        loc.setStatus(Location.UNGUESSED);
        assertFalse(loc.checkMiss(), "checkMiss should return false when status is not MISSED");
    }

    @Test
    public void testIsUnguessed() {
        Location loc = new Location();

        loc.setStatus(Location.UNGUESSED);
        assertTrue(loc.isUnguessed(), "isUnguessed should return true when status is UNGUESSED");

        loc.setStatus(Location.HIT);
        assertFalse(loc.isUnguessed(), "isUnguessed should return false when status is not UNGUESSED");
    }

    @Test
    public void testMarkHit() {
        Location loc = new Location();

        loc.markHit();
        assertEquals(Location.HIT, loc.getStatus(), "markHit should set status to HIT");
    }

    @Test
    public void testMarkMiss() {
        Location loc = new Location();

        loc.markMiss();
        assertEquals(Location.MISSED, loc.getStatus(), "markMiss should set status to MISSED");
    }

    @Test
    public void testHasShip() {
        Location loc = new Location();

        loc.setShip(true);
        assertTrue(loc.hasShip(), "hasShip should return true when a ship is present");

        loc.setShip(false);
        assertFalse(loc.hasShip(), "hasShip should return false when no ship is present");
    }

    @Test
    public void testSetShip() {
        Location loc = new Location();

        loc.setShip(true);
        assertTrue(loc.hasShip(), "setShip should correctly set hasShip to true");

        loc.setShip(false);
        assertFalse(loc.hasShip(), "setShip should correctly set hasShip to false");
    }

    @Test
    public void testSetAndGetStatus() {
        Location loc = new Location();

        loc.setStatus(Location.HIT);
        assertEquals(Location.HIT, loc.getStatus(), "setStatus should correctly update the status");

        loc.setStatus(Location.MISSED);
        assertEquals(Location.MISSED, loc.getStatus(), "setStatus should correctly update the status to MISSED");
    }

    @Test
    public void testSetAndGetLengthOfShip() {
        Location loc = new Location();

        loc.setLengthOfShip(3);
        assertEquals(3, loc.getLengthOfShip(), "setLengthOfShip should correctly update lengthOfShip");

        loc.setLengthOfShip(5);
        assertEquals(5, loc.getLengthOfShip(), "getLengthOfShip should return the correct value");
    }

    @Test
    public void testSetAndGetDirectionOfShip() {
        Location loc = new Location();

        loc.setDirectionOfShip(0);
        assertEquals(0, loc.getDirectionOfShip(), "setDirectionOfShip should correctly set the direction to 0");

        loc.setDirectionOfShip(1);
        assertEquals(1, loc.getDirectionOfShip(), "getDirectionOfShip should return the correct direction");
    }
}
