package com.codersbay.gerhofer;

import java.util.Scanner;

public class GameOfLife {

    public static void main(String[] args) {
        boolean[][] currentGeneration = {
                {false, false, false, false, false, false, false, false},
                {false, false, true, false, false, false, false, false},
                {false, false, false, true, false, false, false, false},
                {false, true, true, true, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false}
        };

        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNext("[Q|q]")) {
            print(currentGeneration);
            currentGeneration = calculateNextGeneration(currentGeneration);
            scanner.nextLine();
        }

        for (int i = 0; i <= 100; i++) {
            print(currentGeneration);
            currentGeneration = calculateNextGeneration(currentGeneration);
        }

    }

    public static void print(boolean[][] currentGeneration) {
        System.out.println("-------------------");
        for (int i = 0; i <= currentGeneration.length - 1; i++) {
            for (int j = 0; j <= currentGeneration[0].length - 1; j++) {
                System.out.print(currentGeneration[i][j] ? "#" : " ");
            }
            System.out.println();
        }
    }

    public static boolean[][] calculateNextGeneration(boolean[][] currentGeneration) {
        boolean[][] nextGeneration = new boolean[currentGeneration.length][currentGeneration[0].length];
        for (int i = 1; i <= currentGeneration.length - 2; i++) {
            for (int j = 1; j <= currentGeneration[0].length - 2; j++) {
                int numberOfNeighbours = calculateNeighbours(currentGeneration, i, j);
                nextGeneration[i][j] = nextCellLives(currentGeneration[i][j], numberOfNeighbours);
            }
        }
        return nextGeneration;
    }

    public static int calculateNeighbours(boolean[][] field, int row, int column) {
        if (row == 0 || column == 0 || row == field.length - 1 || column == field[0].length - 1) {
            return -1;
        }

        boolean amIAlive = field[row][column];
        int neighbourCount = amIAlive ? -1 : 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (field[i][j]) {
                    neighbourCount++;
                }
            }
        }
        return neighbourCount;
    }

    public static boolean nextCellLives(boolean currentIsAlive, int nOfLivingNeighbours) {
        return staysAlive(currentIsAlive, nOfLivingNeighbours) ||
                isResurrected(currentIsAlive, nOfLivingNeighbours);
    }

    private static boolean isResurrected(boolean currentIsAlive, int nOfLivingNeighbours) {
        return !currentIsAlive && nOfLivingNeighbours == 3;
    }

    private static boolean staysAlive(boolean currentIsAlive, int nOfLivingNeighbours) {
        return currentIsAlive && (nOfLivingNeighbours == 2 || nOfLivingNeighbours == 3);
    }

}
