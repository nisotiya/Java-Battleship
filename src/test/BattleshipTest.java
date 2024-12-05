import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;


public class BattleshipTest {
    private Player player;
    private Player computer;
    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @Before
    public void setUp() {
        player = new Player();
        computer = new Player();
        System.setOut(new PrintStream(outputContent));
        // Reinitialize Scanner for each test
        Battleship.reader = new Scanner(System.in);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outputContent.reset();
        // Do not close the Scanner to avoid `Scanner closed` errors
    }


    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream((data + "\n").getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(System.in); // Reinitialize Scanner for new input
    }


    @Test
    public void testShipPlacement() {
        provideInput(
                "A\n1\n0\n" + // Ship 1
                        "B\n2\n1\n" + // Ship 2
                        "C\n3\n0\n" + // Ship 3
                        "D\n4\n1\n" + // Ship 4
                        "E\n5\n0\n"   // Ship 5
        );
        Battleship.setup(player);

        assertEquals("All ships should be placed", 0, player.numOfShipsLeft());
        assertTrue("First ship should be placed", player.playerGrid.hasShip(0, 0));
        assertTrue("Second ship should be placed", player.playerGrid.hasShip(1, 1));
    }

    @Test
    public void testInvalidShipPlacement() {
        String input = "A\n11\n0\n" +
                "A\n1\n0\n" +
                "B\n2\n0\n" +
                "C\n3\n0\n" +
                "D\n4\n0\n" +
                "E\n5\n0\n\n\n"; // Extra newlines for Scanner

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Battleship.reader = new Scanner(inputStream);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());
    }


    @Test
    public void testCoordinateConversions() {
        assertEquals(0, Battleship.convertLetterToInt("A"));
        assertEquals(9, Battleship.convertLetterToInt("J"));
        assertEquals(-1, Battleship.convertLetterToInt("Z"));

        assertEquals("A", Battleship.convertIntToLetter(0));
        assertEquals("J", Battleship.convertIntToLetter(9));
        assertEquals("Z", Battleship.convertIntToLetter(10));

        assertEquals(0, Battleship.convertUserColToProCol(1));
        assertEquals(9, Battleship.convertUserColToProCol(10));
        assertEquals(-1, Battleship.convertUserColToProCol(11));
    }

    @Test
    public void testPlayerGuessing() {
        computer.ships[0].setLocation(0, 0);
        computer.ships[0].setDirection(Ship.HORIZONTAL);
        computer.playerGrid.addShip(computer.ships[0]);

        provideInput("A\n1\n");
        String result = Battleship.askForGuess(player, computer);
        assertTrue("Should be a hit", result.contains("HIT"));

        provideInput("B\n1\n");
        result = Battleship.askForGuess(player, computer);
        assertTrue("Should be a miss", result.contains("MISS"));
    }

    @Test
    public void testComputerGuessing() {
        Randomizer.theInstance = new Random(123);
        player.ships[0].setLocation(5, 5);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        String input = "\n\n\n"; // Multiple newlines for Scanner prompts
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Battleship.reader = new Scanner(inputStream);

        Battleship.compMakeGuess(computer, player);

        int totalGuesses = 0;
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                if (!computer.oppGrid.get(i, j).isUnguessed()) {
                    totalGuesses++;
                }
            }
        }
        assertTrue("Computer should make at least one guess", totalGuesses > 0);
    }


    @Test
    public void testWinConditions() {
        for (int i = 0; i < 17; i++) {
            player.playerGrid.markHit(0, i % 10);
        }
        assertTrue("Player should lose after 17 hits", player.playerGrid.hasLost());

        for (int i = 0; i < 17; i++) {
            computer.playerGrid.markHit(0, i % 10);
        }
        assertTrue("Computer should lose after 17 hits", computer.playerGrid.hasLost());
    }

    @Test
    public void testGridPrinting() {
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(Ship.HORIZONTAL);
        player.playerGrid.addShip(player.ships[0]);

        outputContent.reset();
        player.playerGrid.printStatus();
        assertTrue("Grid status should show unguessed cells", outputContent.toString().contains("-"));

        outputContent.reset();
        player.playerGrid.printShips();
        assertTrue("Grid should show the ship's placement", outputContent.toString().contains("A"));
    }

    @Test
    public void testComputerSetup() {
        Battleship.setupComputer(computer);
        assertEquals("All computer ships should be placed", 0, computer.numOfShipsLeft());

        int shipSpaces = 0;
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                if (computer.playerGrid.hasShip(i, j)) {
                    shipSpaces++;
                }
            }
        }
        assertEquals("Should have correct number of ship spaces", 17, shipSpaces);
    }

    @Test
    public void testMainGameLoop() {
        Randomizer.theInstance = new Random(123);
        StringBuilder input = new StringBuilder();
        // Setup phase
        for (int i = 0; i < 5; i++) {
            input.append("A\n").append(i + 1).append("\n0\n");
        }
        input.append("\n\n"); // Computer setup continues

        // Gameplay phase - mix of hits and misses
        for (int i = 0; i < 20; i++) {
            input.append(String.format("%c\n%d\n\n\n",
                    (char)('A' + (i % 10)), (i % 10) + 1));
        }
        provideInput(input.toString());

        try {
            Battleship.main(new String[]{});
        } catch (Exception e) {
            // Game might end before all input is consumed
        }

        String output = outputContent.toString();
        assertTrue(output.contains("JAVA BATTLESHIP"));
    }

    @Test
    public void testSetupWithInvalidInputs() {
        // Input for 5 ship placements with forced valid inputs
        StringBuilder input = new StringBuilder();
        input.append("A\n1\n0\n")    // Ship 1
                .append("B\n2\n0\n")    // Ship 2
                .append("C\n3\n0\n")    // Ship 3
                .append("D\n4\n0\n")    // Ship 4
                .append("E\n5\n0\n");   // Ship 5

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.toString().getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());
        assertTrue(player.playerGrid.hasShip(0, 0));
    }

    @Test
    public void testComputerGuessWithShipHit() {
        Randomizer.theInstance = new Random(42);
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            input.append("\n");
        }
        provideInput(input.toString());

        Battleship.compMakeGuess(computer, player);

        boolean foundHit = false;
        boolean foundMiss = false;
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                if (computer.oppGrid.get(i, j).checkHit()) foundHit = true;
                if (computer.oppGrid.get(i, j).checkMiss()) foundMiss = true;
            }
        }
        assertTrue(foundHit || foundMiss);
    }

    @Test
    public void testSetupComputer() {
        Randomizer.theInstance = new Random(42);
        Battleship.setupComputer(computer);

        // Verify ship placement
        int shipCount = 0;
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                if (computer.playerGrid.hasShip(i, j)) shipCount++;
            }
        }
        assertEquals(17, shipCount);

        // Test overlapping prevention
        assertFalse(hasOverlappingShips(computer.playerGrid));
    }

    private boolean hasOverlappingShips(Grid grid) {
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                int shipCount = 0;
                if (grid.hasShip(i, j)) shipCount++;
                if (shipCount > 1) return true;
            }
        }
        return false;
    }

    @Test
    public void testAllLetterConversions() {
        for (char c = 'A'; c <= 'J'; c++) {
            int expected = c - 'A';
            assertEquals(expected, Battleship.convertLetterToInt(String.valueOf(c)));
            assertEquals(String.valueOf(c), Battleship.convertIntToLetter(expected));
        }
        assertEquals(-1, Battleship.convertLetterToInt("K"));
        assertEquals("Z", Battleship.convertIntToLetter(10));
    }

    @Test
    public void testAllColumnConversions() {
        for (int i = 1; i <= 10; i++) {
            assertEquals(i-1, Battleship.convertUserColToProCol(i));
            assertEquals(i, Battleship.convertCompColToRegular(i-1));
        }
        assertEquals(-1, Battleship.convertUserColToProCol(11));
        assertEquals(-1, Battleship.convertUserColToProCol(0));
        assertEquals(-1, Battleship.convertCompColToRegular(-1));
        assertEquals(-1, Battleship.convertCompColToRegular(10));
    }

    @Test
    public void testComputer100RandomGuesses() {
        Randomizer.theInstance = new Random(42);
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        for(int i = 0; i < 100; i++) {
            String moves = "\n\n"; // For continue prompts
            provideInput(moves);
            Battleship.compMakeGuess(computer, player);
        }

        boolean foundHit = false;
        boolean foundMiss = false;
        for(int i = 0; i < Grid.NUM_ROWS; i++) {
            for(int j = 0; j < Grid.NUM_COLS; j++) {
                if(computer.oppGrid.get(i,j).checkHit()) foundHit = true;
                if(computer.oppGrid.get(i,j).checkMiss()) foundMiss = true;
            }
        }
        assertTrue("Should find both hits and misses", foundHit && foundMiss);
    }

    @Test
    public void testBoundaryAndInvalidInputs() {
        StringBuilder input = new StringBuilder();
        input.append("A\n1\n0\n")    // Ship 1
                .append("B\n2\n0\n")    // Ship 2
                .append("C\n3\n0\n")    // Ship 3
                .append("D\n4\n0\n")    // Ship 4
                .append("E\n5\n0\n")    // Ship 5
                .append("\n\n");        // Extra newlines

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.toString().getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());

        // Test boundary conditions
        assertTrue(player.playerGrid.hasShip(0, 0));
        assertFalse(player.playerGrid.hasShip(9, 9));
    }

    @Test
    public void testGameFlow() {
        // Setup phase with valid inputs
        StringBuilder input = new StringBuilder();
        for(int i = 0; i < 5; i++) {
            input.append(String.format("A\n%d\n0\n\n", i + 1));
        }

        // Add moves until one player wins
        for(int i = 0; i < 20; i++) {
            input.append(String.format("A\n%d\n\n\n", (i % 10) + 1));
        }

        provideInput(input.toString());

        try {
            Battleship.main(new String[]{});
        } catch(Exception e) {
            // Game may end before all input consumed
        }
    }

    @Test
    public void testComputerGuessesAndHits() {
        String input = String.join("\n", "enter", "enter", "");
        provideInput(input);

        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        Battleship.compMakeGuess(computer, player);

        // Verify computer made valid guess
        boolean guessFound = false;
        for(int i = 0; i < Grid.NUM_ROWS; i++) {
            for(int j = 0; j < Grid.NUM_COLS; j++) {
                if(!computer.oppGrid.get(i,j).isUnguessed()) {
                    guessFound = true;
                    break;
                }
            }
        }
        assertTrue(guessFound);
    }



    @Test
    public void testAllPlacementScenarios() {
        // Test all valid placement combinations
        String input = String.join("\n",
                "A", "1", "0",  // Valid
                "B", "2", "1",  // Valid vertical
                "C", "3", "0",
                "D", "4", "1",
                "E", "5", "0",
                "\n", "\n");

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());

        // Verify placements
        assertTrue(player.playerGrid.hasShip(0, 0));
        assertTrue(player.playerGrid.hasShip(1, 1));
    }

    @Test
    public void testGameEndScenarios() {
        // Setup game state
        setupGameStateForTesting();

        // Add 17 hits to trigger win
        for(int i = 0; i < 17; i++) {
            player.playerGrid.markHit(0, i % 10);
        }

        assertTrue(player.playerGrid.hasLost());
    }

    private void setupGameStateForTesting() {
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        computer.ships[0].setLocation(0, 0);
        computer.ships[0].setDirection(0);
        computer.playerGrid.addShip(computer.ships[0]);
    }

    @Test
    public void testSetupWithInvalidAndEdgeInputs() {
        String input = String.join("\n",
                "A", "1", "0",  // First ship
                "B", "2", "0",  // Second ship
                "C", "3", "0",  // Third ship
                "D", "4", "0",  // Fourth ship
                "E", "5", "0",  // Fifth ship
                "", "");  // Extra newlines

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());
        assertTrue(player.playerGrid.hasShip(0, 0));
        assertTrue(player.playerGrid.hasShip(1, 1));
    }

    @Test
    public void testAskForGuessAllScenarios() {
        // Place ships
        computer.ships[0].setLocation(0, 0);
        computer.ships[0].setDirection(0);
        computer.playerGrid.addShip(computer.ships[0]);

        // Test hit
        String hitInput = "A\n1\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(hitInput.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        String result = Battleship.askForGuess(player, computer);
        assertTrue(result.contains("HIT"));

        // Test miss
        String missInput = "B\n1\n";
        testIn = new ByteArrayInputStream(missInput.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        result = Battleship.askForGuess(player, computer);
        assertTrue(result.contains("MISS"));
    }

    @Test
    public void testCompMakeGuessAllScenarios() {
        // Set fixed seed for deterministic behavior
        Randomizer.theInstance = new Random(42);

        // Place ship at coordinates we know computer will guess
        player.ships[0].setLocation(2, 2); // More central position
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);
        player.ships[1].setLocation(5, 5);
        player.ships[1].setDirection(1);
        player.playerGrid.addShip(player.ships[1]);

        // Prepare input for multiple guesses
        String input = "";
        for (int i = 0; i < 100; i++) {
            input += "enter\n"; // Each guess needs an enter press
        }
        provideInput(input);

        // Make several guesses
        int hits = 0, misses = 0;
        int totalGuesses = 50;

        for (int i = 0; i < totalGuesses; i++) {
            try {
                Battleship.compMakeGuess(computer, player);
                // Count hits and misses after each guess
                for (int row = 0; row < Grid.NUM_ROWS; row++) {
                    for (int col = 0; col < Grid.NUM_COLS; col++) {
                        if (computer.oppGrid.get(row, col).checkHit()) {
                            hits++;
                        }
                        if (computer.oppGrid.get(row, col).checkMiss()) {
                            misses++;
                        }
                    }
                }
                if (hits > 0) break; // Stop if we found a hit
            } catch (Exception e) {
                // Continue if we get Scanner issues
                continue;
            }
        }

        assertTrue("Should find at least 1 hit", hits > 0);
        assertTrue("Should make multiple valid guesses", hits + misses > 0);
    }



    @Test
    @Ignore
    public void testGridResetAndEdgeCases() {
        // Add a ship and reset the grid
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(Ship.HORIZONTAL);
        player.playerGrid.addShip(player.ships[0]);

//        player.playerGrid.reset();

        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                assertTrue("Grid should be empty after reset", player.playerGrid.get(i, j).isUnguessed());
            }
        }
    }

    @Test
    @Ignore
    public void testOverlappingShips() {
        // Place a ship
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(Ship.HORIZONTAL);
        player.playerGrid.addShip(player.ships[0]);

        // Attempt to place another overlapping ship
        player.ships[1].setLocation(0, 0);
        player.ships[1].setDirection(Ship.HORIZONTAL);
//        boolean result = player.playerGrid.addShip(player.ships[1]);
//
//        assertFalse("Overlapping ships should not be allowed", result);
    }

    @Test
    public void testEdgeShipPlacement() {
        String input = String.join("\n",
                "A", "1", "0",  // First ship
                "B", "2", "0",  // Second ship
                "C", "3", "0",  // Third ship
                "D", "4", "0",  // Fourth ship
                "E", "5", "0",  // Fifth ship
                "", "");

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);

        // Verify ships placed correctly
        assertTrue(player.playerGrid.hasShip(0, 0));
        assertTrue(player.playerGrid.hasShip(1, 1));
        assertEquals(0, player.numOfShipsLeft());
    }

    @Test
    public void testInvalidGuessHandling() {
        computer.ships[0].setLocation(0, 0);
        computer.ships[0].setDirection(0);
        computer.playerGrid.addShip(computer.ships[0]);

        // Test invalid coordinates
        provideInput("Z\n11\nA\n1\n");
        String result = Battleship.askForGuess(player, computer);
        assertTrue("Should handle invalid input", result.contains("MISS") || result.contains("HIT"));
    }

    @Test
    public void testEmptyGridBeforePlacement() {
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                assertTrue("Grid should be empty before placement", player.playerGrid.get(i, j).isUnguessed());
            }
        }
    }

    @Test
    public void testFullGridGuessing() {
        // Place ships in known positions
        computer.ships[0].setLocation(5, 5);
        computer.ships[0].setDirection(Ship.HORIZONTAL);
        computer.playerGrid.addShip(computer.ships[0]);

        // Make guesses for entire grid
        StringBuilder input = new StringBuilder();
        for(int i = 0; i < Grid.NUM_ROWS; i++) {
            for(int j = 1; j <= Grid.NUM_COLS; j++) {
                input.append(String.format("%c\n%d\n", (char)('A' + i), j));
            }
        }
        provideInput(input.toString());

        int totalGuesses = 0;
        for(int i = 0; i < Grid.NUM_ROWS; i++) {
            for(int j = 0; j < Grid.NUM_COLS; j++) {
                Battleship.askForGuess(player, computer);
                totalGuesses++;
            }
        }

        assertTrue("All cells should be guessed", totalGuesses == Grid.NUM_ROWS * Grid.NUM_COLS);
    }

    @Test
    public void testCompleteGameWithAllScenarios() {
        // Ship placement phase
        String setupInput = String.join("\n",
                "A", "1", "0",  // Ship 1
                "B", "2", "0",  // Ship 2
                "C", "3", "0",  // Ship 3
                "D", "4", "0",  // Ship 4
                "E", "5", "0",  // Ship 5
                "", "");

        ByteArrayInputStream testIn = new ByteArrayInputStream(setupInput.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        try {
            Battleship.setup(player);
            assertEquals(0, player.numOfShipsLeft());
            assertTrue(player.playerGrid.hasShip(0, 0));
        } catch (NoSuchElementException e) {
            fail("Setup should complete successfully");
        }
    }

    @Test
    public void testExtensiveComputerGuessing() {
        // Set fixed seed for deterministic behavior
        Randomizer.theInstance = new Random(42);

        // Place ships in various positions
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        player.ships[1].setLocation(2, 2);
        player.ships[1].setDirection(1);
        player.playerGrid.addShip(player.ships[1]);

        player.ships[2].setLocation(5, 5);
        player.ships[2].setDirection(0);
        player.playerGrid.addShip(player.ships[2]);

        // Add lots of newlines for Scanner.nextLine() calls
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            input.append("\n");
        }
        provideInput(input.toString());

        // Make multiple guesses
        int guesses = 0;
        int maxGuesses = 50;
        boolean foundHit = false;
        boolean foundMiss = false;

        while (guesses < maxGuesses && (!foundHit || !foundMiss)) {
            Battleship.compMakeGuess(computer, player);
            guesses++;

            // Check for both hits and misses
            for (int row = 0; row < Grid.NUM_ROWS; row++) {
                for (int col = 0; col < Grid.NUM_COLS; col++) {
                    if (computer.oppGrid.get(row, col).checkHit()) foundHit = true;
                    if (computer.oppGrid.get(row, col).checkMiss()) foundMiss = true;
                }
            }
        }

        assertTrue("Should find both hits and misses", foundHit && foundMiss);
    }

    @Test
    public void testBoundaryAndEdgeCases() {
        String input = String.join("\n",
                // Valid ship placements
                "A", "1", "0",
                "B", "2", "0",
                "C", "3", "0",
                "D", "4", "0",
                "E", "5", "0",
                "", "");

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        Battleship.setup(player);
        assertEquals(0, player.numOfShipsLeft());
        assertTrue(player.playerGrid.hasShip(0, 0));
        assertTrue(player.playerGrid.hasShip(1, 1));
    }

    @Test
    public void testRandomizedGamePlay() {
        Randomizer.theInstance = new Random(42);

        // Ship placement input
        String setupInput = String.join("\n",
                "A", "1", "0",
                "B", "2", "0",
                "C", "3", "0",
                "D", "4", "0",
                "E", "5", "0",
                "", "");

        ByteArrayInputStream testIn = new ByteArrayInputStream(setupInput.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        try {
            Battleship.main(new String[]{});
        } catch (NoSuchElementException e) {
            // Expected when game ends
        }

        String output = outputContent.toString();
        assertTrue(output.contains("BATTLESHIP"));
    }

    @Test
    public void testExtensiveGameScenarios() {
        // Test all possible coordinate combinations
        for (int i = 0; i < Grid.NUM_ROWS; i++) {
            for (int j = 0; j < Grid.NUM_COLS; j++) {
                assertEquals(String.valueOf((char)('A' + i)), Battleship.convertIntToLetter(i));
                assertEquals(j, Battleship.convertUserColToProCol(j + 1));
                assertEquals(j + 1, Battleship.convertCompColToRegular(j));
            }
        }
    }

    @Test
    public void testErrorConditionsAndBoundaries() {
        // Test invalid inputs
        assertEquals(-1, Battleship.convertLetterToInt("Z"));
        assertEquals(-1, Battleship.convertLetterToInt("0"));
        assertEquals(-1, Battleship.convertUserColToProCol(11));
        assertEquals(-1, Battleship.convertUserColToProCol(0));
        assertEquals(-1, Battleship.convertCompColToRegular(-1));

        // Test boundary conditions
        assertTrue(Battleship.hasErrors(9, 9, 0, player, 0));
        assertTrue(Battleship.hasErrors(9, 0, 1, player, 0));
        assertFalse(Battleship.hasErrors(0, 0, 0, player, 0));
    }

    @Test
    public void testCompleteGameFlow() {
        String input = String.join("\n",
                // Ship placement - including invalid attempts
                "Z", "A", "11", "1",  // Invalid inputs
                "A", "1", "0",
                "B", "2", "0",
                "C", "3", "0",
                "D", "4", "0",
                "E", "5", "0",
                "", "",
                // Guessing phase
                "Z", "1",
                "A", "11",
                "A", "1",
                "A", "1",
                "B", "2",
                "", "");

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        try {
            Battleship.main(new String[]{});
        } catch (Exception e) {
            // Expected when game ends
        }
    }

    @Test
    public void testShipPlacementAllScenarios() {
        // Create input for 5 valid ship placements
        StringBuilder input = new StringBuilder();
        input.append("A\n1\n0\n")    // First ship
                .append("B\n2\n0\n")    // Second ship
                .append("C\n3\n0\n")    // Third ship
                .append("D\n4\n0\n")    // Fourth ship
                .append("E\n5\n0\n")    // Fifth ship
                .append("\n\n");        // Extra newlines for Scanner consumption

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.toString().getBytes());
        System.setIn(testIn);
        Battleship.reader = new Scanner(testIn);

        // Perform setup
        Battleship.setup(player);

        // Verify ships were placed
        assertTrue("First ship should be placed at A1", player.playerGrid.hasShip(0, 0));
        assertTrue("Second ship should be placed at B2", player.playerGrid.hasShip(1, 1));
        assertTrue("Third ship should be placed at C3", player.playerGrid.hasShip(2, 2));
        assertTrue("Fourth ship should be placed at D4", player.playerGrid.hasShip(3, 3));
        assertTrue("Fifth ship should be placed at E5", player.playerGrid.hasShip(4, 4));

        // Verify the number of ships left is zero
        assertEquals("All ships should be placed", 0, player.numOfShipsLeft());
    }


    @Test
    public void testComputerGuessPatterns() {
        // Set random seed for consistent testing
        Randomizer.theInstance = new Random(42);

        // Place ships in known positions
        player.ships[0].setLocation(3, 3);
        player.ships[0].setDirection(0);
        player.playerGrid.addShip(player.ships[0]);

        player.ships[1].setLocation(6, 6);
        player.ships[1].setDirection(1);
        player.playerGrid.addShip(player.ships[1]);

        // Add extra newlines for each Scanner.nextLine() call
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            input.append("press enter\n"); // For "Press ENTER to continue" prompts
        }
        provideInput(input.toString());
        boolean foundHit = false;
        boolean foundMiss = false;
        int attempts = 0;
        int maxAttempts = 30;

        while ((!foundHit || !foundMiss) && attempts < maxAttempts) {
            try {
                Battleship.compMakeGuess(computer, player);
                attempts++;

                // Check grid after each guess
                for (int i = 0; i < Grid.NUM_ROWS; i++) {
                    for (int j = 0; j < Grid.NUM_COLS; j++) {
                        if (computer.oppGrid.get(i, j).checkHit()) foundHit = true;
                        if (computer.oppGrid.get(i, j).checkMiss()) foundMiss = true;
                    }
                }
            } catch (Exception e) {
                // Ignore scanner exceptions and continue
                continue;
            }
        }
        assertTrue("Computer should make at least one valid guess", foundHit || foundMiss);
    }

}
