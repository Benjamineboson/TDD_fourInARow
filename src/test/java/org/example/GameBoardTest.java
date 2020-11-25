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



    /**
    *   Should be tested in checkWinner() tests?
    */

//    @Test
//    public void testMakeAMove_winningMove_playerOne(){
//        String expected = "Player One";
//        assertEquals(expected,gameBoard.checkWinner());
//    }
//
//    @Test
//    public void testMakeAMove_winningMove_playerTwo(){
//        String expected = "Player Two";
//        assertEquals(expected,gameBoard.checkWinner());
//    }

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
    public void test_checkWinner_playerOne_vertical_down(){
        for (int i = 0; i < 7; i++) {
            if (i%2 == 0) gameBoard.makeAMove(0);
            else gameBoard.makeAMove(1);
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

//    @Test
//    public void test_checkWinner_playerOne_vertical_up(){
//        //p1
//        gameBoard.makeAMove(0);
//        //p2
//        gameBoard.makeAMove(0);
//        //p1
//        gameBoard.makeAMove(0);
//        //p2
//        gameBoard.makeAMove(1);
//        gameBoard.makeAMove(0);
//        gameBoard.makeAMove(1);
//        gameBoard.makeAMove(0);
//        gameBoard.makeAMove(1);
//        gameBoard.makeAMove(0);


//        for (int i = 0; i < 7; i++) {
//            if (i%2 == 0) gameBoard.makeAMove(0);
//            else gameBoard.makeAMove(1);
//        }
//        String expected = "Player One";
//        assertEquals(expected,gameBoard.checkWinner());
//    }

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
            if (i > 2) gameBoard.makeAMove(i);
            else{gameBoard.makeAMove(i); gameBoard.makeAMove(i);}
        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_horizontal_right(){
        gameBoard.makeAMove(5);
        gameBoard.makeAMove(6);
        gameBoard.makeAMove(4);
        gameBoard.makeAMove(6);
        gameBoard.makeAMove(3);
        gameBoard.makeAMove(6);
        gameBoard.makeAMove(2);
//        for (int i = 0; i < 4 ; i++) {
//            if (i > 2) gameBoard.makeAMove(i);
//            else{gameBoard.makeAMove(i); gameBoard.makeAMove(i);}
//        }
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_horizontal(){
        for (int i = 0; i < 4 ; i++) {
          gameBoard.makeAMove(i);
          gameBoard.makeAMove(6);
        }
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_right(){
        //p1
        gameBoard.makeAMove(2);
        //p2
        gameBoard.makeAMove(2);
        //p1
        gameBoard.makeAMove(2);
        //p2
        gameBoard.makeAMove(1);
        //p1
        gameBoard.makeAMove(1);
        //p2
        gameBoard.makeAMove(3);
        //p1
        gameBoard.makeAMove(3);
        //p2
        gameBoard.makeAMove(3);
        //p1
        gameBoard.makeAMove(3);
        //p2
        gameBoard.makeAMove(6);
        //p1
        gameBoard.makeAMove(0);
        String [][] expected = {
                {"X","O","X","O"," "," ","O"},
                {" ","X","O","X"," "," "," "},
                {" "," ","X","O"," "," "," "},
                {" "," "," ","X"," "," "," "},
                {" "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "}};
        String expectedWinner = "Player One";
        assertEquals(expectedWinner,gameBoard.checkWinner());
        assertArrayEquals(expected,gameBoard.makeAMove(0));
    }

    @Test
    public void test_checkWinner_playerOne_diagonally_up_left(){
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());

    }

    @Test
    public void test_checkWinner_playerOne_diagonally_down_right(){
        //p1
        gameBoard.makeAMove(6);
        //p2
        gameBoard.makeAMove(5);
        //p1
        gameBoard.makeAMove(5);
        //p2
        gameBoard.makeAMove(4);
        //p1
        gameBoard.makeAMove(4);
        //p2
        gameBoard.makeAMove(0);
        //p1
        gameBoard.makeAMove(4);
        //p2
        gameBoard.makeAMove(3);
        //p1
        gameBoard.makeAMove(3);
        //p2
        gameBoard.makeAMove(3);
        //p1
        gameBoard.makeAMove(3);
        String expected = "Player One";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_down_right(){
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }

    @Test
    public void test_checkWinner_playerTwo_diagonally_down_left(){
        String expected = "Player Two";
        assertEquals(expected,gameBoard.checkWinner());
    }






}
