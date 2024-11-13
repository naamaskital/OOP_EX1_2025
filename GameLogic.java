import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic{
    private final int boardSize= 8;
    public Disc[][] gameBoard = new Disc[boardSize][boardSize];
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
    public Disc getDiscAtPosition(Position position)
    {
        return gameBoard[position.col()][position.row()];
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public List<Position> ValidMoves() {
        Player player = getCurrentPlayer();  // קבלת השחקן הנוכחי
        List<Position> validMoves = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (possibleMove(i, j, player)) {
                    validMoves.add(new Position(i, j));  // הוספת מיקום חוקי לרשימה
                }
            }
        }
        return validMoves;
    }

    private boolean possibleMove(int i, int j, Player player) {
        Position[] arrDirections= getArrDiractions();
        for (int k=0; k<8; k++) {
            if (isGoodDirection(new Position(i, j), arrDirections[k], player)) {
                return true;  // אם מצאנו כיוון חוקי, מחזירים true
            }
        }
        return false;  // לא מצאנו כיוון חוקי
    }
    private Position[] getArrDiractions() {
        // הכיוונים האפשריים: ימינה, שמאלה, למעלה, למטה וארבעה כיוונים אלכסוניים
        Position[] arrDirections = new Position[8];
        arrDirections[0] = new Position(1, 1);   // כיוון אלכסון ימינה-למטה
        arrDirections[1] = new Position(1, 0);   // כיוון ימינה
        arrDirections[2] = new Position(-1, 0);  // כיוון שמאלה
        arrDirections[3] = new Position(-1, 1);  // כיוון אלכסון שמאלה-למטה
        arrDirections[4] = new Position(1, -1);  // כיוון אלכסון ימינה-למעלה
        arrDirections[5] = new Position(-1, -1); // כיוון אלכסון שמאלה-למעלה
        arrDirections[6] = new Position(0, 1);   // כיוון למעלה
        arrDirections[7] = new Position(0, -1);  // כיוון למטה
        return arrDirections;
    }

    private boolean isGoodDirection(Position position, Position direction, Player player) {
        boolean wasOtherPlayer = false;  // דגל לבדוק אם מצאנו דיסק של היריב
        int xDirection = direction.col();  // כיוון העמודה
        int yDirection = direction.row();  // כיוון השורה

        // נבדוק אם ניתן להניח דיסק בכיוון הזה
        for (int x = position.col() + xDirection, y = position.row() + yDirection;
             x >= 0 && x < boardSize && y >= 0 && y < boardSize;
             x += xDirection, y += yDirection) {

            if (gameBoard[x][y] == null) {
                return false;  // מצאנו מקום פנוי, לא ניתן לשים דיסק כאן
            } else if (gameBoard[x][y].getOwner().equals(player)) {
                return wasOtherPlayer;  // אם מצאנו דיסק של השחקן אחרי דיסק של היריב
            } else {
                wasOtherPlayer = true;  // מצאנו דיסק של היריב, נמשיך לבדוק
            }
        }
        return false;  // אם לא מצאנו דיסק של השחקן אחרי דיסק של היריב
    }


    public Player getCurrentPlayer(){
        Player player;
        if(turn)
            player=player1;
        else
            player=player2;
        return player;
    }
    @Override
    public int countFlips(Position a) {
        Position[] arrDirections = getArrDiractions();
        int otherPlayerCount=0;
        int count = 0;
        Player player = getCurrentPlayer();
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            for (int x = a.col() + xDirection, y = a.row() + yDirection;
                 x >= 0 && x < boardSize && y >= 0 && y < boardSize;
                 x += xDirection, y += yDirection) {
                if (gameBoard[x][y] == null) {
                    break;
                } else if (gameBoard[x][y].getOwner().equals(player)) {
                    count += otherPlayerCount;
                    break;
                } else {
                    otherPlayerCount++;
                }
            }
            otherPlayerCount = 0;
        }
        return count;
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
