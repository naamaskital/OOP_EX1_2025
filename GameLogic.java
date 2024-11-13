import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic{
    private final int sizeBoard= 8;
    public Disc[][] gameBoard = new Disc[sizeBoard][sizeBoard];
    private Player player1;
    private Player player2;
    private boolean turn=true;


    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if(ValidMoves().contains(a)){
            gameBoard[a.col()][a.row()] = disc;
            turn =!turn; // change the current player.
            return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return gameBoard[position.col()][position.row()];
    }

    @Override
    public int getBoardSize() {
        return sizeBoard;
    }

    @Override
    public List<Position> ValidMoves() {

        return List.of();
    }


    private boolean possibleLocation(Position position,Player player){
        if(gameBoard[position.col()][position.row()]!=null){
            return false;
        }
        for (int i = 0; i < sizeBoard; i++) {


        }
        return true;
    }





    @Override
    public int countFlips(Position a) {
        return 0;
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

    }

    @Override
    public boolean isFirstPlayerTurn() {
        return turn;
    }

    @Override
    public boolean isGameFinished() {
        if (!ValidMoves().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void reset() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = null;
            }
        }

        gameBoard[3][3] = new SimpleDisc(player1, new Position(3, 3));
        gameBoard[3][4] = new SimpleDisc(player2, new Position(3, 4));
        gameBoard[4][3] = new SimpleDisc(player2, new Position(4, 3));
        gameBoard[4][4] = new SimpleDisc(player1, new Position(4, 4));

    }

    @Override
    public void undoLastMove() {

    }
}
