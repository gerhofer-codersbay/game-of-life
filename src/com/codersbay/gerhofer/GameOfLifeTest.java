package com.codersbay.gerhofer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {

    @Test
    @DisplayName("If a living cell has 2 or 3 neighbours it stays alive")
    public void testCellStaysAlive() {
        assertTrue(GameOfLife.nextCellLives(true, 2));
        assertTrue(GameOfLife.nextCellLives(true, 3));
    }

    @Test
    @DisplayName("If a cell is dead and has three neighbours it gets resurrected")
    public void testCellComesToLive() {
        assertTrue(GameOfLife.nextCellLives(false, 3));
    }

    @Test
    public void testCellDiesDueToLoneliness() {
        assertFalse(GameOfLife.nextCellLives(true, 0));
        assertFalse(GameOfLife.nextCellLives(true, 1));
    }

    @Test
    public void testCellDiesDueToOverpopulation() {
        assertFalse(GameOfLife.nextCellLives(true, 4));
    }

    @Test
    public void testCalculateNeighbours() {
        boolean[][] field = {
                {true, true, true},
                {false, true, false},
                {true, false, true}
        };
        assertEquals(5, GameOfLife.calculateNeighbours(field, 1, 1));
    }

    @Test
    public void testCalculateDirectNeighbours() {
        boolean[][] field = {
                {true, true, true, false},
                {false, true, false, false},
                {true, false, true, false},
                {true, true, true, false}
        };
        assertEquals(5, GameOfLife.calculateNeighbours(field, 1, 1));
    }

    @Test
    public void testCalculateNeighboursInvalidBiggerArray() {
        boolean[][] field = {
                {true, true, true, true, true},
                {false, true, false, false, false},
                {true, false, true, true, false}
        };
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 0, 0));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 0, 1));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 0, 2));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 1, 0));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 1, 4));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 2, 0));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 2, 1));
        assertEquals(-1, GameOfLife.calculateNeighbours(field, 2, 4));
    }

    @ParameterizedTest(name = "Calculating Neighbours in row {0} and column {1} returns -1")
    @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 2",
            "2, 0",
            "2, 1",
            "2, 2"
    })
    public void testCalculateNeighboursInvalidParmeterized(int row, int column) {
        boolean[][] field = {
                {true, true, true},
                {false, true, false},
                {true, false, true}
        };
        assertEquals(-1, GameOfLife.calculateNeighbours(field, row, column));
    }

    @Test
    public void testCalculateNextGeneration() {
        boolean[][] currentGeneration = {
                {true, true, true, true, true},
                {true, true, true, true, true},
                {true, true, true, true, true},
                {true, true, true, true, true},
                {true, true, true, true, true}
        };
        boolean[][] nextGeneration = {
                {true, true, true, true, true},
                {true, false, false, false, true},
                {true, false, false, false, true},
                {true, false, false, false, true},
                {true, true, true, true, true}
        };
        assertArrayEquals(nextGeneration, GameOfLife.calculateNextGeneration(currentGeneration));
    }

    @Test
    public void testCalcNeighboursOfGleiter() {
        boolean[][] gleiter = {
                {false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {false, false, false, true, false, false, false, false},
                {false, true, true, true, false, false, false, false},
        };
        assertEquals(3, GameOfLife.calculateNeighbours(gleiter, 2, 1));
    }

    @Test
    public void testGleiter() {
        boolean[][] step0 = {
                {false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {false, false, false, true, false, false, false, false},
                {false, true, true, true, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        boolean[][] expectedStep1 = {
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, true, false, true, false, false, false, false},
                {false, false, true, true, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        boolean[][] expectedStep2 = {
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, true, false, false, false, false},
                {false, true, false, true, false, false, false, false},
                {false, false, true, true, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        boolean[][] expectedStep3 = {
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {false, false, false, true, true, false, false, false},
                {false, false, true, true, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };
        boolean[][] step1 = GameOfLife.calculateNextGeneration(step0);
        boolean[][] step2 = GameOfLife.calculateNextGeneration(step1);
        boolean[][] step3 = GameOfLife.calculateNextGeneration(step2);

        assertArrayEquals(expectedStep1, step1);
        assertArrayEquals(expectedStep2, step2);
        assertArrayEquals(expectedStep3, step3);

    }

}
