import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
class GameLogicTest {
    GameLogic gameLogic = new GameLogic();

    Position position1 = new Position(3, 3);
    Position position2 = new Position(4, 4);
    Player player1;
    Player player2;


    @Test
    void locate_disc() {
        player1 = new HumanPlayer(true);
        Disc disc = new SimpleDisc(player1, position1);
        gameLogic.locate_disc(position1, disc);
        Disc disc1 = new SimpleDisc();
        assertFalse(gameLogic.locate_disc(position1, disc1));
        assertEquals(player1, gameLogic.getDiscAtPosition(position2).getOwner());
        assertTrue(gameLogic.locate_disc(position2, disc1)); //need to do valid.
    }

    @Test
    void getDiscAtPosition() {
        player1 = new HumanPlayer(true);
        Disc disc = new SimpleDisc(player1, position1);
        gameLogic.locate_disc(position1, disc);
        assertEquals(disc.position, gameLogic.getDiscAtPosition(position1));
    }

    @Test
    void getBoardSize() {
    }

    @Test
    void validMoves() {
        Disc disc = new SimpleDisc(player1, position1);
        Disc disc1 = new SimpleDisc(player1, position2);
        List<Position> validMoves = new ArrayList<>();
        validMoves.add(position1);
        validMoves.add(position2);



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