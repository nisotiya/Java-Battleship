import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testGridInitialization() {
        Grid grid = new Grid();
        assertEquals(10, grid.numRows());
        assertEquals(10, grid.numCols());
        for (int row = 0; row < grid.numRows(); row++) {
            for (int col = 0; col < grid.numCols(); col++) {
                assertTrue(grid.get(row, col).isUnguessed(), "Expected initial location to be unguessed");
            }
        }
    }

    @Test
    void testMarkHit() {
        Grid grid = new Grid();
        grid.markHit(2, 3);
        assertTrue(grid.get(2, 3).checkHit(), "Expected location (2, 3) to be marked as hit");
        assertEquals(1, grid.getStatus(2, 3));
    }

    @Test
    void testMarkMiss() {
        Grid grid = new Grid();
        grid.markMiss(4, 5);
        assertTrue(grid.get(4, 5).checkMiss(), "Expected location (4, 5) to be marked as miss");
        assertEquals(2, grid.getStatus(4, 5));
    }

    @Test
    void testSetShipAndHasShip() {
        Grid grid = new Grid();
        grid.setShip(2, 3, true);
        assertTrue(grid.hasShip(2, 3), "Expected ship to be at (2, 3)");
        assertFalse(grid.hasShip(4, 5), "Expected no ship at (4, 5)");
    }

    @Test
    void testAlreadyGuessed() {
        Grid grid = new Grid();
        assertFalse(grid.alreadyGuessed(1, 1), "Expected location (1, 1) to be unguessed");
        grid.markHit(1, 1);
        assertTrue(grid.alreadyGuessed(1, 1), "Expected location (1, 1) to be guessed after marking hit");
    }

    @Test
    void testHasLost() {
        Grid grid = new Grid();
        for (int i = 0; i < 17; i++) {
            grid.markHit(i / 10, i % 10); // Mark 17 points as hits
        }
        assertTrue(grid.hasLost(), "Expected grid to indicate loss after 17 hits");
    }
}