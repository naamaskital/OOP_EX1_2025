import java.util.*;

public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    public Disc[][] gameBoard = new Disc[boardSize][boardSize];
    private Player player1;
    private Player player2;
    private boolean turn = true;
    Stack<Move> history = new Stack<>();
    Stack<Stack<Position>> listsOfHistory = new Stack<>();
    Stack<Position> flipNeighbors = new Stack<>();



    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (ValidMoves().contains(a)) {
            Stack<Position> flips = new Stack<>();
            gameBoard[a.row()][a.col()] = disc;
            history.push(new Move(a, disc));
            if(disc instanceof SimpleDisc){flips = flipsForSimple(a);}
            else if(disc instanceof BombDisc ){flips = flipsForBomb(a,flipNeighbors);}
            Stack<Position> temp = new Stack<>();
            // אם יש דיסקים להפוך
            while (!flips.isEmpty()) {
                Position pos = flips.pop(); // שליפת דיסק להפיכה
                System.out.println(pos);
                temp.push(pos);
                gameBoard[pos.row()][pos.col()].setOwner(getCurrentPlayer()); // הפיכת הדיסק
            }
            listsOfHistory.push(temp);
            turn = !turn;
            return true;
        }
        return false;
    }

    private Stack<Position> flipsForBomb(Position a, Stack flipNeighbors) {
        Position[] arrDirections = getArrDiractions();
        Player player = getCurrentPlayer();


        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            int x = a.row() + xDirection, y = a.col() + yDirection;
            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                if (gameBoard[x][y] != null) {
                    Position current = new Position(x, y);

                    if (flipNeighbors.contains(current)) {
                        continue;
                    }
                    if (!gameBoard[x][y].getOwner().equals(player) ) {
                        flipNeighbors.push(new Position(x, y));
                    }
                    if (gameBoard[x][y].getType() == "💣") {
                        flipsForBomb(new Position(x,y),flipNeighbors);
                    }
                }
            }
        }

    return flipNeighbors;
}

    private Stack<Position> flipsForSimple(Position a) {

        Stack<Position> flippableDiscs = new Stack<>();
        Stack<Position> temp = new Stack<>();
        Position[] arrDirections = getArrDiractions();
        Player player = getCurrentPlayer();
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            for (int x = a.row() + xDirection, y = a.col() + yDirection;
                 x >= 0 && x < boardSize && y >= 0 && y < boardSize;
                 x += xDirection, y += yDirection) {
                if (gameBoard[x][y] == null) {
                    break;
                } else if (gameBoard[x][y].getOwner().equals(player)) {
                    while (!temp.empty()) {
                        Position p = temp.pop();
                        flippableDiscs.push(p);
                    }
                    break;
                } else {
                    temp.push(new Position(x, y));
                }
            }
            temp.clear();
        }
        return flippableDiscs;
    }

    private boolean posissionContains(List<Position> list, Position position) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(position))
                return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return gameBoard[position.row()][position.col()];
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
                if(gameBoard[i][j]==null){
                    if (possibleMove(i, j, player)) {
                        validMoves.add(new Position(i, j));  // הוספת מיקום חוקי לרשימה
                }
                }
            }
        }
        return validMoves;
    }

    private boolean possibleMove(int i, int j, Player player) {
        Position[] arrDirections = getArrDiractions();
        for (int k = 0; k < 8; k++) {
            if (isGoodDirection(new Position(i, j), arrDirections[k], player)) {
                return true;  // אם מצאנו כיוון חוקי, מחזירים true
            }
        }
        return false;  // לא מצאנו כיוון חוקי
    }

    private Position[] getArrDiractions() {
        Position[] arrDirections = new Position[8];
        arrDirections[0] = new Position(1, 1);
        arrDirections[1] = new Position(1, 0);
        arrDirections[2] = new Position(-1, 0);
        arrDirections[3] = new Position(-1, 1);
        arrDirections[4] = new Position(1, -1);
        arrDirections[5] = new Position(-1, -1);
        arrDirections[6] = new Position(0, 1);
        arrDirections[7] = new Position(0, -1);
        return arrDirections;
    }

    private boolean isGoodDirection(Position position, Position direction, Player player) {
        boolean wasOtherPlayer = false;
        int xDirection = direction.col();
        int yDirection = direction.row();

        for (int x = position.row() + xDirection, y = position.col() + yDirection;
             x >= 0 && x < boardSize && y >= 0 && y < boardSize;
             x += xDirection, y += yDirection) {

            if (gameBoard[x][y] == null) {
                return false;
            } else if (gameBoard[x][y].getOwner().equals(player)) {
                return wasOtherPlayer;
            } else {
                wasOtherPlayer = true;
            }
        }
        return false;
    }

    public Player getCurrentPlayer() {
        Player player;
        if (turn)
            player = player1;
        else
            player = player2;
        return player;
    }

    @Override
    public int countFlips(Position a) {
        Position[] arrDirections = getArrDiractions();
        int otherPlayerCount = 0;
        int count = 0;
        Player player = getCurrentPlayer();
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            for (int x = a.row() + xDirection, y = a.col() + yDirection;
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
        return ValidMoves().isEmpty();
    }

    @Override
    public void reset() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
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
        Move lastMove = history.pop();
        gameBoard[lastMove.position().row()][lastMove.position().col()] = null;
        Stack<Position> lastMoves = listsOfHistory.pop();
        while(!lastMoves.isEmpty()){
            Position p = lastMoves.pop();
            gameBoard[p.row()][p.col()].setOwner(getCurrentPlayer());
        }
        turn = !turn;

    }
}