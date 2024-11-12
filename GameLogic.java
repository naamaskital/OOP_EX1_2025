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


    }

    @Override
    public void undoLastMove() {

    }
}
