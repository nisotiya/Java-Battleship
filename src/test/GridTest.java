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
    @Test
    void testHasLostWithPointLessThan17() {
        Grid grid = new Grid();
        for (int i = 0; i < 16; i++) {
            grid.markHit(i / 10, i % 10); // Mark 17 points as hits
        }
        assertFalse(grid.hasLost(), "Expected grid returns false");
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal65() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(65),0);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal66() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(66),1);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal67() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(67),2);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal68() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(68),3);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal69() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(69),4);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal70() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(70),5);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal71() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(71),6);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal72() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(72),7);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal73() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(73),8);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal74() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(74),9);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal75() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(75),10);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal76() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(76),11);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal77() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(77),12);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal78() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(78),13);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal79() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(79),14);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal80() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(80),15);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal81() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(81),16);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal82() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(82),17);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal83() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(83),18);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal84() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(84),19);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal85() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(85),20);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal86() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(86),21);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal87() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(87),22);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal88() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(88),23);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal89() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(89),24);
    }

    @Test
    void testSwitchCounterToIntegerForArrayWithVal90() {
        Grid grid = new Grid();
        assertEquals(grid.switchCounterToIntegerForArray(90),25);
    }

    @Test
    void testGeneralPrintMethodWithType0() {
        Grid grid = new Grid();
        grid.setStatus(0,0,1);
        grid.printStatus();
        assertEquals(1, grid.getStatus(0, 0));

    }

    @Test
    void testGeneralPrintMethodWithType0AndHit() {
        Grid grid = new Grid();
        grid.setStatus(0,0,-1);
        grid.printStatus();
        assertEquals(-1, grid.getStatus(0, 0));

    }

    @Test
    void testGeneralPrintMethodWithType0AndMiss() {
        Grid grid = new Grid();
        grid.setStatus(0,0,2);
        grid.printStatus();
        assertEquals(2, grid.getStatus(0, 0));

    }

    @Test
    void testGeneralPrintMethodWithType0AndHitAndMiss() {
        Grid grid = new Grid();
        grid.setStatus(0,0,3);
        grid.printStatus();
        assertEquals(3, grid.getStatus(0, 0));

    }

    @Test
    void testGeneralPrintMethodWithType1WithShipLength2() {
        Grid grid = new Grid();
        Ship ship = new Ship(2);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printShips();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(2, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType1WithShipLength3() {
        Grid grid = new Grid();
        Ship ship = new Ship(3);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printShips();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(3, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType1WithShipLength4() {
        Grid grid = new Grid();
        Ship ship = new Ship(4);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printShips();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(4, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType1WithShipLength5() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printShips();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(5, grid.get(1, 1).getLengthOfShip());

    }

    @Test
    void testGeneralPrintMethodWithType1WithShipLength6() {
        Grid grid = new Grid();
        Ship ship = new Ship(6);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printShips();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(6, grid.get(1, 1).getLengthOfShip());
    }
    @Test
    void testGeneralPrintMethodWithType2WithShipLength2() {
        Grid grid = new Grid();
        Ship ship = new Ship(2);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printCombined();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(2, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType2WithShipLength3() {
        Grid grid = new Grid();
        Ship ship = new Ship(3);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printCombined();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(3, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType2WithShipLength4() {
        Grid grid = new Grid();
        Ship ship = new Ship(4);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printCombined();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(4, grid.get(1, 1).getLengthOfShip());

    }
    @Test
    void testGeneralPrintMethodWithType2WithShipLength5() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setLocation(1,1);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        grid.addShip(ship);
        grid.printCombined();
        assertTrue(grid.hasShip(1, 1));
        assertEquals(5, grid.get(1, 1).getLengthOfShip());

    }

    @Test
    void testAddShipWithDirectionNotSetThrowsException() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setDirection(0);
        grid.setShip(1,1,true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            grid.addShip(ship); // Adding ship without setting a valid location
        });

        assertEquals("ERROR! Direction or Location is unset/default", exception.getMessage());

    }

    @Test
    void testAddShipWithLocationNotSetThrowsException() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setLocation(1,1);
        grid.setShip(1,1,true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            grid.addShip(ship); // Adding ship without setting a valid location
        });

        assertEquals("ERROR! Direction or Location is unset/default", exception.getMessage());

    }

    @Test
    void testAddShipWithDirectionEqualTo1() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setLocation(1,1);
        grid.setShip(1,1,true);
        ship.setDirection(1);
        grid.addShip(ship);
        for (int i = 1; i < 6; i++) {
            assertTrue(grid.hasShip(i, 1));
        }


    }

    @Test
    void testAddShipWithDirectionGreaterThan1ThrowsException() {
        Grid grid = new Grid();
        Ship ship = new Ship(5);
        ship.setLocation(1,1);
        ship.setDirection(-1);
        grid.setShip(1,1,true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> grid.addShip(ship));
        assertEquals("ERROR! Direction or Location is unset/default", exception.getMessage());

    }
}