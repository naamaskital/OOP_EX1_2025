import java.util.*;

public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    public Disc[][] gameBoard = new Disc[boardSize][boardSize];
    private Player player1;
    private Player player2;
    private boolean turn = true;
    Position[] arrDirections = new Position[8];
    Stack<Move> historyMoves = new Stack<>();
    {
        arrDirections[0] = new Position(1, 1);
        arrDirections[1] = new Position(1, 0);
        arrDirections[2] = new Position(-1, 0);
        arrDirections[3] = new Position(-1, 1);
        arrDirections[4] = new Position(1, -1);
        arrDirections[5] = new Position(-1, -1);
        arrDirections[6] = new Position(0, 1);
        arrDirections[7] = new Position(0, -1);
    }



    @Override
    public boolean locate_disc(Position a, Disc disc) {
        String str;
        Player player=getCurrentPlayer();
        if(player.equals(player1))
            str="Player 1";
        else str="Player 2";
        if (ValidMoves().contains(a)) {
            if(disc instanceof BombDisc) {
                if(player.getNumber_of_bombs()==0)
                    return false;
                else
                    player.reduce_bomb();
            }
            if(disc instanceof UnflippableDisc) {
                if(player.getNumber_of_unflippedable()==0) return false;
                else
                    player.reduce_unflippedable();
            }
            Stack<Position> flips = new Stack<>();
            gameBoard[a.row()][a.col()] = disc;
            System.out.println(str+" placed a "+disc.getType()+" in "+a.toString() );
            if(disc instanceof BombDisc){flips = flipsForBomb(a,new Stack<Position>());}
            else {flips = flipsForSimple(a);}
            Stack<Position> temp = new Stack<>();
            // אם יש דיסקים להפוך
            while (!flips.isEmpty()) {
                Position pos = flips.pop(); // שליפת דיסק להפיכה
                temp.push(pos);
                Disc currentDisc=gameBoard[pos.row()][pos.col()];
                currentDisc.setOwner(getCurrentPlayer());
                System.out.println(str+" flipped the "+currentDisc.getType()+" in "+pos.toString() );
            }
            Move move=new Move(disc,a,temp);
            historyMoves.push(move);
            turn = !turn;
            return true;
        }
        return false;
    }

    private Stack<Position> flipsForBomb(Position a, Stack<Position> flipNeighbors) {
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
                        flipsForBomb(new Position(x,y),flipNeighbors );
                    }
                }
            }
        }

    return flipNeighbors;
}

    private Stack<Position> flipsForSimple(Position a) {

        Stack<Position> flippableDiscs = new Stack<>();
        Stack<Position> temp = new Stack<>();
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
        for (int k = 0; k < 8; k++) {
            if (isGoodDirection(new Position(i, j), arrDirections[k], player)) {
                return true;  // אם מצאנו כיוון חוקי, מחזירים true
            }
        }
        return false;  // לא מצאנו כיוון חוקי
    }



    private boolean isGoodDirection(Position position, Position direction, Player player) {
        boolean wasOtherPlayer = false;
        int xDirection = direction.col();
        int yDirection = direction.row();

        for (int x = position.row() + xDirection, y = position.col() + yDirection;
             x >= 0 && x < boardSize && y >= 0 && y < boardSize;
             x += xDirection, y += yDirection) {
            Disc disc=gameBoard[x][y];

            if (disc == null) {
                return false;
            } else if (disc.getOwner().equals(player)) {
                return wasOtherPlayer;
            } else {
                if(!(disc instanceof UnflippableDisc)  ){
                    wasOtherPlayer = true;
                }
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
        int otherPlayerCount = 0;
        int count = 0;
        Player player = getCurrentPlayer();
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            for (int x = a.row() + xDirection, y = a.col() + yDirection;
                 x >= 0 && x < boardSize && y >= 0 && y < boardSize;
                 x += xDirection, y += yDirection) {
                Disc disc=gameBoard[x][y];
                if (disc == null) {
                    break;
                } else if (disc.getOwner().equals(player)) {
                    count += otherPlayerCount;
                    break;
                } else {
                    if(!(disc instanceof UnflippableDisc)){
                        otherPlayerCount++;
                    }
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
        if(ValidMoves().isEmpty()){
            int winner=getWinner();
            if(winner==1)
                player1.addWin();
            else if(winner==2)
                player2.addWin();
            return true;
        }
        return false;
    }

    private int getWinner() {
        int count1=0;
        int count2=0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Disc disc=gameBoard[i][j];
                if(disc != null){
                    if(gameBoard[i][j].getOwner().equals(player1)) {
                        count1++;
                    }
                    else if(gameBoard[i][j].getOwner().equals(player2)) {
                        count2++;
                    }
                }
            }
        }
        if(count1>count2) return 1;
        else if (count2>count1) {
            return 2;
        }
        return 0;
    }

    @Override
    public void reset() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = null;
            }
        }
        gameBoard[3][3] = new SimpleDisc(player1);
        gameBoard[3][4] = new SimpleDisc(player2);
        gameBoard[4][3] = new SimpleDisc(player2);
        gameBoard[4][4] = new SimpleDisc(player1);
        turn=true;
        player1.reset_bombs_and_unflippedable();
        player2.reset_bombs_and_unflippedable();
    }

    @Override
    public void undoLastMove() {
        System.out.println("Undoing last move:");
        if(historyMoves.empty()) {
            System.out.println("\tNo previous move available to undo");
            return;
        }
        Move lastMove = historyMoves.pop();
        Disc disc= lastMove.disc();
        Position position= lastMove.position();
        System.out.println("\tUndo: removing "+disc.getType()+" from "+position.toString());
        gameBoard[lastMove.position().row()][lastMove.position().col()] = null;
        Stack<Position> historyFlips = lastMove.getFlips();
        while(!historyFlips.isEmpty()){
            Position p = historyFlips.pop();
            gameBoard[p.row()][p.col()].setOwner(getCurrentPlayer());
            System.out.println("\tUndo: flipping back "+disc.getType()+" in "+p.toString());
        }
        turn = !turn;

    }
}