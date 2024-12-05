import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testAddShips() {

        Ship ship = new Ship(1);
        ship.setLocation(1,1);
        ship.setDirection(1);
        player.ships = new Ship[]{ship};
        player.addShips();
    }

    @Test
    void testAddShipsWhereShipIsEmpty() {

        player.ships = new Ship[]{};
        player.addShips();
        assertEquals(0, player.ships.length);
    }

    @Test
    void testNumOfShipsLeft() {

        assertEquals(5, player.numOfShipsLeft(), "Initially, all 5 ships should be left.");

        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(1); // Assume 1 is a valid direction
        player.ships[1].setLocation(1, 1);
        player.ships[1].setDirection(0); // Assume 0 is another valid direction

        assertEquals(3, player.numOfShipsLeft(), "Three ships should be left.");
    }

    @Test
    void testNumOfShipsLeftLocationNotSet() {

        assertEquals(5, player.numOfShipsLeft(), "Initially, all 5 ships should be left.");
        player.ships[0].setDirection(1); // Assume 1 is a valid direction
        assertEquals(5, player.numOfShipsLeft(), "Three ships should be left.");
    }

    @Test
    void testNumOfShipsLeftDirectionNotSet() {
        assertEquals(5, player.numOfShipsLeft(), "Initially, all 5 ships should be left.");
        player.ships[0].setLocation(1,1);
        assertEquals(5, player.numOfShipsLeft(), "Three ships should be left.");
    }

    @Test
    void testChooseShipLocation() {
        Ship testShip = player.ships[0];
        int row = 2, col = 3, direction = 1; // Example location and direction
        player.chooseShipLocation(testShip, row, col, direction);
        assertEquals(row, testShip.getRow(), "Ship's row should be set correctly.");
        assertEquals(col, testShip.getCol(), "Ship's column should be set correctly.");
        assertEquals(direction, testShip.getDirection(), "Ship's direction should be set correctly.");

    }

}

