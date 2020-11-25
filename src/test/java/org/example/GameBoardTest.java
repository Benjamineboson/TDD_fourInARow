package org.example;

import org.example.exceptions.ColumnFullException;
import org.example.game_engine.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        gameBoard.setPlayerOne(true);
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
        for (int i = 0; i < 7; i++) {
            if (i%2 == 0) gameBoard.makeAMove(0);
            else gameBoard.makeAMove(1);
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_vertical(){
        gameBoard.makeAMove(3);
        for (int i = 0; i < 7; i++) {
            if (i%2 != 0) gameBoard.makeAMove(0);
            else gameBoard.makeAMove(1);
        }
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_horizontal_left(){
        for (int i = 0; i < 4 ; i++) {
            if (i == 3) gameBoard.makeAMove(i);
            else{gameBoard.makeAMove(i); gameBoard.makeAMove(i);}
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_horizontal_right(){
        String combination = "5646362";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_horizontal_left(){
        String combination = "61625354";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_horizontal_right(){
        String combination = "64635251";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_right(){
        String combination = "22211333360";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
        assertEquals(expectedWinner,gameBoard.checkWinner());

    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_left(){
        String combination = "44455333306";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_down_right(){
        String combination = "65544043333";
        for (int i = 0; i <  combination.length(); i++) {
            gameBoard.makeAMove(Integer.parseInt(String.valueOf(combination.charAt(i))));
        }
        String expectedWinner = "Player One";
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






}
