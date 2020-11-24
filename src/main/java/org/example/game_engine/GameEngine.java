package org.example.game_engine;

public interface GameEngine {
    String[][] makeAMove(int col);
    String checkWinner();
    void resetGame();
}
