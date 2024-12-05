import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testInitialState() {
        Location location = new Location();
        assertEquals(Location.UNGUESSED, location.getStatus());
        assertFalse(location.hasShip());
        assertTrue(location.isUnguessed());
        assertFalse(location.checkHit());
        assertFalse(location.checkMiss());
        assertEquals(-1, location.getLengthOfShip());
        assertEquals(-1, location.getDirectionOfShip());
    }

    @Test
    public void testMarkHitAndCheckStatus() {
        Location location = new Location();
        location.markHit();
        assertEquals(Location.HIT, location.getStatus());
        assertTrue(location.checkHit());
        assertFalse(location.checkMiss());
        assertFalse(location.isUnguessed());
    }

    @Test
    public void testMarkMissAndCheckStatus() {
        Location location = new Location();
        location.markMiss();
        assertEquals(Location.MISSED, location.getStatus());
        assertTrue(location.checkMiss());
        assertFalse(location.checkHit());
        assertFalse(location.isUnguessed());
    }

    @Test
    public void testSetAndGetShip() {
        Location location = new Location();
        location.setShip(true);
        assertTrue(location.hasShip());

        location.setShip(false);
        assertFalse(location.hasShip());
    }

    @Test
    public void testSetAndGetLengthOfShip() {
        Location location = new Location();
        location.setLengthOfShip(3);
        assertEquals(3, location.getLengthOfShip());
    }

    @Test
    public void testSetAndGetDirectionOfShip() {
        Location location = new Location();
        location.setDirectionOfShip(Location.UNGUESSED);
        assertEquals(Location.UNGUESSED, location.getDirectionOfShip());

        location.setDirectionOfShip(Location.HIT);
        assertEquals(Location.HIT, location.getDirectionOfShip());
    }

    @Test
    public void testSetAndGetStatus() {
        Location location = new Location();

        location.setStatus(Location.UNGUESSED);
        assertEquals(Location.UNGUESSED, location.getStatus());
        assertTrue(location.isUnguessed());
        assertFalse(location.checkHit());
        assertFalse(location.checkMiss());

        location.setStatus(Location.HIT);
        assertEquals(Location.HIT, location.getStatus());
        assertFalse(location.isUnguessed());
        assertTrue(location.checkHit());
        assertFalse(location.checkMiss());

        location.setStatus(Location.MISSED);
        assertEquals(Location.MISSED, location.getStatus());
        assertFalse(location.isUnguessed());
        assertFalse(location.checkHit());
        assertTrue(location.checkMiss());
    }

    @Test
    public void testTransitionBetweenStatuses() {
        Location location = new Location();

        // Test unguessed to hit
        location.setStatus(Location.UNGUESSED);
        location.markHit();
        assertEquals(Location.HIT, location.getStatus());
        assertTrue(location.checkHit());

        // Test hit to miss
        location.setStatus(Location.HIT);
        location.markMiss();
        assertEquals(Location.MISSED, location.getStatus());
        assertTrue(location.checkMiss());

        // Reset to unguessed
        location.setStatus(Location.UNGUESSED);
        assertTrue(location.isUnguessed());
    }
}
