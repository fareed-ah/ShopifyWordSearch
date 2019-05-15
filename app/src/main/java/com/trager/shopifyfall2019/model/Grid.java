package com.trager.shopifyfall2019.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Grid {

    private static int ROWS;
    private static int COLS;

    private Tile[][] tiles;

    // private List<String> words = Arrays.asList("Swift", "Kotlin", "ObjectiveC", "Variable", "Java", "Mobile");
    private List<String> words;
    private static final String[] DIRS = {"UP", "DOWN", "LEFT", "RIGHT", "TOP_LEFT", "TOP_RIGHT", "BOTTOM_RIGHT", "BOTTOM_LEFT"};

    public Grid(int rows, int cols, List<String> words) {
        ROWS = rows;
        COLS = cols;
        tiles = new Tile[ROWS][COLS];
        this.words = words;
        for (String word : words) {
            placeWord(word);
        }
        fillEmptySpaces();
    }

    private void fillEmptySpaces() {

        Random r = new Random();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (tiles[row][col] == null) {
                    tiles[row][col] = new Tile(String.valueOf((char) (r.nextInt(26) + 'a')).toUpperCase(), row, col, true, true);
                }
            }
        }

    }

    private void placeWord(String word) {
        Random rand = new Random();

        boolean flag = false;

        while (!flag) {
            int randRow = rand.nextInt(10);
            int randCol = rand.nextInt(10);

            if (tiles[randRow][randCol] != null) {
                continue;
            }

            List<String> attemptedDirections = new ArrayList<>();
            while (attemptedDirections.size() != DIRS.length) {

                String direction = DIRS[rand.nextInt(DIRS.length)];
                if (attemptedDirections.contains(direction)) {
                    continue;
                }

                attemptedDirections.add(direction);

                if (!isValidDirection(randRow, randCol, direction, word)) {
                    continue;
                }
                addWordToGrid(randRow, randCol, direction, word);
                flag = true;
                break;
            }
        }
    }


    private void addWordToGrid(int currRow, int currCol, String direction, String word) {
        int length = word.length();
        word = word.toUpperCase();
        switch (direction) {
            case "UP":
                for (int row = currRow; row > currRow - length; row--) {
                    tiles[row][currCol] = new Tile(String.valueOf(word.charAt(0)), row, currCol, true, true);
                    word = word.substring(1);
                }
                break;
            case "RIGHT":
                for (int col = currCol; col < currCol + length; col++) {
                    tiles[currRow][col] = new Tile(String.valueOf(word.charAt(0)), currRow, col, true, true);
                    word = word.substring(1);
                }
                break;
            case "DOWN":
                for (int row = currRow; row < currRow + length; row++) {
                    tiles[row][currCol] = new Tile(String.valueOf(word.charAt(0)), row, currCol, true, true);
                    word = word.substring(1);

                }
                break;
            case "LEFT":
                for (int col = currCol; col > currCol - length; col--) {
                    tiles[currRow][col] = new Tile(String.valueOf(word.charAt(0)), currRow, col, true, true);
                    word = word.substring(1);
                }
                break;
            case "TOP_LEFT":
                for (int row = currRow, col = currCol; col > currCol - length && row > currRow - length; col--, row--) {
                    tiles[row][col] = new Tile(String.valueOf(word.charAt(0)), row, col, true, true);
                    word = word.substring(1);
                }
                break;

            case "TOP_RIGHT":
                for (int row = currRow, col = currCol; col < currCol + length && row > currRow - length; col++, row--) {
                    tiles[row][col] = new Tile(String.valueOf(word.charAt(0)), row, col, true, true);
                    word = word.substring(1);
                }
                break;

            case "BOTTOM_RIGHT":
                for (int row = currRow, col = currCol; col < currCol + length && row < currRow + length; col++, row++) {
                    tiles[row][col] = new Tile(String.valueOf(word.charAt(0)), row, col, true, true);
                    word = word.substring(1);
                }
                break;
            case "BOTTOM_LEFT":
                for (int row = currRow, col = currCol; col > currCol - length && row < currRow + length; col--, row++) {
                    tiles[row][col] = new Tile(String.valueOf(word.charAt(0)), row, col, true, true);
                    word = word.substring(1);
                }
                break;
        }
    }


    private boolean isValidDirection(int currRow, int currCol, String direction, String word) {
        boolean result = true;
        switch (direction) {
            case "UP":
                if (currRow - word.length() >= 0) {
                    for (int row = currRow; row > currRow - word.length(); row--) {
                        if (tiles[row][currCol] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;
            case "RIGHT":
                if (currCol + word.length() <= COLS) {
                    for (int col = currCol; col < currCol + word.length(); col++) {
                        if (tiles[currRow][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;
            case "DOWN":
                if (currRow + word.length() <= ROWS) {
                    for (int row = currRow; row < currRow + word.length(); row++) {
                        if (tiles[row][currCol] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;
            case "LEFT":
                if (currCol - word.length() >= 0) {
                    for (int col = currCol; col > currCol - word.length(); col--) {
                        if (tiles[currRow][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;

            case "TOP_LEFT":
                if (currCol - word.length() >= 0 && currRow - word.length() >= 0) {
                    for (int row = currRow, col = currCol; col > currCol - word.length() && row > currRow - word.length(); col--, row--) {
                        if (tiles[row][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;

            case "TOP_RIGHT":
                if (currCol + word.length() <= COLS && currRow - word.length() >= 0) {
                    for (int row = currRow, col = currCol; col < currCol + word.length() && row > currRow - word.length(); col++, row--) {
                        if (tiles[row][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;

            case "BOTTOM_RIGHT":
                if (currCol + word.length() <= COLS && currRow + word.length() <= ROWS) {
                    for (int row = currRow, col = currCol; col < currCol + word.length() && row < currRow + word.length(); col++, row++) {
                        if (tiles[row][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;

            case "BOTTOM_LEFT":
                if (currCol - word.length() >= 0 && currRow + word.length() <= ROWS) {
                    for (int row = currRow, col = currCol; col > currCol - word.length() && row < currRow + word.length(); col--, row++) {
                        if (tiles[row][col] != null) {
                            result = false;
                            break;
                        }
                    }
                } else {
                    result = false;
                }
                break;
        }
        return result;
    }

    public Tile[] getTiles() {
        Tile[] grid = new Tile[ROWS * COLS];

        int index = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[index] = tiles[row][col];
                index++;
            }
        }
        return grid;
    }

    public void resetSelectableTiles() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                tiles[row][col].setSelectable(true);
            }
        }
    }

    public void setSelectableTiles(int currRow, int currCol, String direction) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                switch (direction) {
                    case "UP":
                        if (row == currRow - 1 && col == currCol) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "RIGHT":
                        if (row == currRow && col == currCol + 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "DOWN":
                        if (row == currRow + 1 && col == currCol) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "LEFT":
                        if (row == currRow && col == currCol - 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "TOP_RIGHT":
                        if (row == currRow - 1 && col == currCol + 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "BOTTOM_RIGHT":
                        if (row == currRow + 1 && col == currCol + 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "BOTTOM_LEFT":
                        if (row == currRow + 1 && col == currCol - 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "TOP_LEFT":
                        if (row == currRow - 1 && col == currCol - 1) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                    case "NONE":
                        if ((row == currRow - 1 && col == currCol) || (row == currRow && col == currCol + 1)
                                || (row == currRow + 1 && col == currCol) || (row == currRow && col == currCol - 1)
                                || (row == currRow - 1 && col == currCol + 1) || (row == currRow + 1 && col == currCol + 1)
                                || (row == currRow + 1 && col == currCol - 1) || (row == currRow - 1 && col == currCol - 1)) {
                            tiles[row][col].setSelectable(true);
                        } else {
                            tiles[row][col].setSelectable(false);
                        }
                        break;
                }
            }
        }
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Tile[][] getGrid() {
        return tiles;
    }
}


