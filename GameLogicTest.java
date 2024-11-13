import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GameLogicTest {
    GameLogic gameLogic = new GameLogic();

    Position position1 = new Position(2, 3);
    Position position2 = new Position(3, 3);
    Player player1;
    Player player2;


    @Test
    void locate_disc() {
        player1 = new HumanPlayer(true);
        Disc disc = new SimpleDisc(player1, position1);
        gameLogic.locate_disc(position1, disc);
        Disc disc1 = new SimpleDisc();
        assertFalse(gameLogic.locate_disc(position1, disc1));
        assertTrue(gameLogic.locate_disc(position2, disc1)); //need to do valid.
    }

    @Test
    void getDiscAtPosition() {
        player1 = new HumanPlayer(true);
        Disc disc = new SimpleDisc(player1, position1);
        gameLogic.locate_disc(position1, disc);
        assertEquals(disc, gameLogic.getDiscAtPosition(position1));
    }

    @Test
    void getBoardSize() {
    }

    @Test
    void validMoves() {
    }

    @Test
    void countFlips() {
    }

    @Test
    void getFirstPlayer() {
    }

    @Test
    void getSecondPlayer() {
    }

    @Test
    void setPlayers() {
    }

    @Test
    void isFirstPlayerTurn() {
    }

    @Test
    void isGameFinished() {
    }

    @Test
    void reset() {
    }

    @Test
    void undoLastMove() {
    }
}