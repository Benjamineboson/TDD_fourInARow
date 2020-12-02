package org.example;

import org.example.IO_utils.BReader;
import org.example.IO_utils.BWriter;
import org.example.exceptions.ColumnFullException;
import org.example.game_engine.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    private GameBoard gameBoard;
    private BReader bReader;
    private BWriter bWriter;

    @BeforeEach
    void setUp() throws IOException {
        gameBoard = new GameBoard();
        gameBoard.setPlayerOne(true);

        bReader = new BReader();
        bWriter = new BWriter();
        Path pathToFile = Paths.get("src/main/resources/fileName.txt");
        try {
            Files.deleteIfExists(pathToFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        gameBoard = null;
    }

    // --------------------------------------- makeAMove() ---------------------------------------

    @Test
    public void test_makeAMove_playerOne(){
        String [][] expected = {
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(6));
    }

    @Test
    public void test_makeAMove_playerTwo(){
        gameBoard.makeAMove(0);
        String [][] expected = {
                {"X","O"," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(1));
    }

    @Test
    public void test_makeAMove_columnFull(){
        for (int i = 0; i < 6 ; i++) {
            gameBoard.makeAMove(0);
        }
        assertThrows(ColumnFullException.class, () -> gameBoard.makeAMove(0));
    }

    @Test
    public void test_makeAMove_indexOutOfBounds(){
        assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.makeAMove(999));
    }

    @Test
    public void test_makeAMove_indexOutOfBounds_negative(){
        assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.makeAMove(-999));
    }

    // --------------------------------------- checkWinner() ---------------------------------------

    @Test
    public void test_checkWinner_playerOne_vertical(){
        String combination = "010101";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X","O"," "," "," "," "," "},
                {"X","O"," "," "," "," "," "},
                {"X","O"," "," "," "," "," "},
                {"X"," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player One";
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_vertical(){
        String combination = "6101010";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X","O"," "," "," "," ","X"},
                {"X","O"," "," "," "," "," "},
                {"X","O"," "," "," "," "," "},
                {" ","O"," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(1));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_horizontal_left(){
        String combination = "061626";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X","X","X","X"," "," ","O"},
                {" "," "," "," "," "," ","O"},
                {" "," "," "," "," "," ","O"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player One";
        assertArrayEquals(expected,gameBoard.makeAMove(3));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_horizontal_right(){
        String combination = "362616";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X","X","X","X"," "," ","O"},
                {" "," "," "," "," "," ","O"},
                {" "," "," "," "," "," ","O"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player One";
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_horizontal_left(){
        String combination = "6061625";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"O","O","O","O"," ","X","X"},
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(3));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_horizontal_right(){
        String combination = "6362615";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"O","O","O","O"," ","X","X"},
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_right(){
        String combination = "2221133336";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
        String [][] expected = {
                {"X","O","X","O"," "," ","O"},
                {" ","X","O","X"," "," "," "},
                {" "," ","X","O"," "," "," "},
                {" "," "," ","X"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_left(){
        String combination = "4445533330";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
        String [][] expected = {
                {"O"," "," ","O","X","O","X"},
                {" "," "," ","X","O","X"," "},
                {" "," "," ","O","X"," "," "},
                {" "," "," ","X"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(6));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_down_right(){
        String combination = "6554404333";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
        String [][] expected = {
                {"O"," "," ","O","O","O","X"},
                {" "," "," ","X","X","X"," "},
                {" "," "," ","O","X"," "," "},
                {" "," "," ","X"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(3));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_down_left(){
        String combination = "3445055666";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
        String [][] expected = {
                {"X"," "," ","X","O","O","O"},
                {" "," "," "," ","X","O","X"},
                {" "," "," "," "," ","X","O"},
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(6));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_down_right(){
        String combination = "63220111600";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X","O","X","O"," "," ","X"},
                {"O","X","O"," "," "," ","X"},
                {"X","O"," "," "," "," "," "},
                {"O"," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_down_left(){
        String combination = "03446555066";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X"," "," ","O","X","O","X"},
                {"X"," "," "," ","O","X","O"},
                {" "," "," "," "," ","O","X"},
                {" "," "," "," "," "," ","O"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(6));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_up_right(){
        String combination = "62112233336";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"O","X","O","X"," "," ","X"},
                {" ","O","X","O"," "," ","X"},
                {" "," ","O","X"," "," "," "},
                {" "," "," ","O"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(0));
        assertEquals(expectedWinner,gameBoard.checkWinner());

    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_up_left(){
        String combination = "04445533330";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String [][] expected = {
                {"X"," "," ","X","O","X","O"},
                {"X"," "," ","O","X","O"," "},
                {" "," "," ","X","O"," "," "},
                {" "," "," ","O"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player Two";
        assertArrayEquals(expected,gameBoard.makeAMove(6));
        assertEquals(expectedWinner,gameBoard.checkWinner());
    }


    // --------------------------------------- viewReplay() ---------------------------------------

    @Test
    public void test_file_created() {
        String [][] board = {
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        Path path = Paths.get("src/main/resources/fileName.txt");

        assertTrue(Files.notExists(path));

        bWriter.writeToFile(board, 1, 1);
        assertTrue(Files.exists(path));


    }

    @Test
    public void test_read_file() throws IOException {
        String [][] board = {
                {" "," "," "," "," "," ","X"},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};

        String expected = "Round: 1 Move: 1\n" +
                "┌───┬───┬───┬───┬───┬───┬───┐\n" +
                "|   |   |   |   |   |   |   |\n" +
                "├───┼───┼───┼───┼───┼───┼───┤\n" +
                "|   |   |   |   |   |   |   |\n" +
                "├───┼───┼───┼───┼───┼───┼───┤\n" +
                "|   |   |   |   |   |   |   |\n" +
                "├───┼───┼───┼───┼───┼───┼───┤\n" +
                "|   |   |   |   |   |   |   |\n" +
                "├───┼───┼───┼───┼───┼───┼───┤\n" +
                "|   |   |   |   |   |   |   |\n" +
                "├───┼───┼───┼───┼───┼───┼───┤\n" +
                "|   |   |   |   |   |   | X |\n" +
                "└───┴───┴───┴───┴───┴───┴───┘,\n";

        Path path = Paths.get("src/main/resources/fileName.txt");
        bWriter.writeToFile(board, 1, 1);

        assertEquals(expected, Files.readString(path));
    }

    @Test
    public void test_read_file_doesntExist() {
        Path path = Paths.get("src/main/resources/fileName.txt");
        try {
            Files.deleteIfExists(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assertTrue(Files.notExists(path));

        assertNotNull(bReader.readFromFile());
    }

    @Test
    public void test_file_fileNotFound() {

    }

    @Test
    public void test_file_fileEmpty() {

    }

}
