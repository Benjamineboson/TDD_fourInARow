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

    @Test
    public void test_makeAMove_playerOne(){
        String [][] expected = {
                {"X"," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        assertArrayEquals(expected,gameBoard.makeAMove(0));
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
    public void testMakeAMove_winningMove_playerOne(){
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void testMakeAMove_winningMove_playerTwo(){
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_makeAMove_indexOutOfBounds(){
        assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.makeAMove(999));
    }

    @Test
    public void test_makeAMove_indexOutOfBounds_negative(){
        assertThrows(IndexOutOfBoundsException.class, () -> gameBoard.makeAMove(-999));
    }





}
