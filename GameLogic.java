import java.util.*;

public class GameLogic implements PlayableLogic {
    private final int boardSize = 8;
    private Disc[][] gameBoard = new Disc[boardSize][boardSize];
    private Player player1;
    private Player player2;
    private boolean turn = true;
    private Stack<Move> historyMoves = new Stack<>();
    private boolean onlyHumen;
    private Position[] arrDirections = new Position[8];
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

    // Locating a disc on the board and handling flips or bomb placement
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        String str;
        Player player = getCurrentPlayer();

        // Determine which player is making the move
        if (player.equals(player1))
            str = "Player 1";
        else
            str = "Player 2";

        // Check if the move is valid
        if (ValidMoves().contains(a)) {

            // Handle special discs (Bomb or Unflippable)
            if (disc instanceof BombDisc) {
                if (player.getNumber_of_bombs() == 0)
                    return false;  // No bombs left
                else
                    player.reduce_bomb();  // Deduct bomb usage
            }
            if (disc instanceof UnflippableDisc) {
                if (player.getNumber_of_unflippedable() == 0)
                    return false;  // No unflippable discs left
                else
                    player.reduce_unflippedable();  // Deduct unflippable disc usage
            }

            gameBoard[a.row()][a.col()] = disc;  // Place the disc on the board
            System.out.println(str + " placed a " + disc.getType() + " in " + a.toString());
            Stack<Position> historyCurrent=new Stack<>();
            flipsForLocation(a,historyCurrent,new HashSet<Disc>());
            Stack<Position> history=new Stack<>();
            for (Position pos : historyCurrent) {
                history.add(pos);
                Disc dTemp=gameBoard[pos.row()][pos.col()];
                dTemp.setOwner(player);
                System.out.println(str + " flipped the " + dTemp.getType() + " in " + pos.toString());
            }
            Move move = new Move(disc, a, history);
            historyMoves.push(move);
            turn = !turn;  // Toggle player turn
            System.out.println();
            return true;
        }
        return false;
    }
    private void flipsForLocation(Position pos,List<Position> history, HashSet<Disc> setFlip) {
        Player player = getCurrentPlayer();
        Stack<Disc> temp = new Stack<>();
        Stack<Position> tempHistory = new Stack<>();
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();

            // Iterate in the current direction until an invalid or owned disc is encountered
            for (int x = pos.row() + xDirection, y = pos.col() + yDirection;
                 x >= 0 && x < boardSize && y >= 0 && y < boardSize;
                 x += xDirection, y += yDirection) {
                if (gameBoard[x][y] == null) {
                    break;
                }
                else {
                    Disc disc = gameBoard[x][y];
                    if (disc.getOwner().equals(player)) {
                        // If an owned disc is found, record the flips
                        while (!temp.empty()) {
                            Disc dTemp= temp.pop();
                            if(!setFlip.contains(dTemp)){
                                if(!(dTemp instanceof UnflippableDisc)){
                                    history.add(tempHistory.pop());
                                    setFlip.add(dTemp);
                                    if(dTemp instanceof BombDisc) addFlipsForBomb(history.getLast(),setFlip,history);
                                }

                            }
                        }
                        break;
                    } else {
                        tempHistory.push(new Position(x, y));
                        temp.push(disc);
                    }
                }
            }
            temp.clear();
        }
    }
    private void addFlipsForBomb(Position bomb, Set<Disc> setFlip,List<Position> history) {
        Player player= getCurrentPlayer();
        // Iterate over all 8 possible directions
        for (Position arrDirection : arrDirections) {
            int xDirection = arrDirection.col();
            int yDirection = arrDirection.row();
            int x = bomb.row() + xDirection, y = bomb.col() + yDirection;

            // Check bounds and if there's a disc to flip
            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                if (gameBoard[x][y] != null) {
                    Disc disc = gameBoard[x][y];
                    Position current = new Position(x, y);
                    // Avoid duplicate positions in the flip list
                    if (setFlip.contains(disc)) {
                        continue;
                    }

                    // Check if the disc is owned by the opponent and add it to flip list
                    if (!disc.getOwner().equals(player)) {
                        if(!(disc instanceof UnflippableDisc)){
                            setFlip.add(disc);
                            history.add(new Position(x,y));
                        }

                    }

                    // If it's a bomb, recurse to check surrounding discs
                    if (disc instanceof BombDisc) {
                        if (setFlip.contains(disc)) addFlipsForBomb(new Position(x, y), setFlip,history);
                    }
                }
            }
        }
    }


    // Return the disc at a specific position on the board
    @Override
    public Disc getDiscAtPosition(Position position) {
        return gameBoard[position.row()][position.col()];
    }

    // Return the size of the game board
    @Override
    public int getBoardSize() {
        return boardSize;
    }

    // Find and return all valid moves for the current player
    @Override
    public List<Position> ValidMoves() {
        Player player = getCurrentPlayer();
        List<Position> validMoves = new ArrayList<>();

        // Check each board position for valid moves
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == null) {
                    if (possibleMove(i, j, player)) {
                        validMoves.add(new Position(i, j));  // Add valid position
                    }
                }
            }
        }
        return validMoves;
    }

    // Check if a move at position (i, j) is possible for the current player
    private boolean possibleMove(int i, int j, Player player) {
        for (int k = 0; k < 8; k++) {
            if (isGoodDirection(new Position(i, j), arrDirections[k], player)) {
                return true;  // If any direction is valid, return true
            }
        }
        return false;  // No valid directions found
    }

    // Check if a direction from a given position is a valid move for the player
    private boolean isGoodDirection(Position position, Position direction, Player player) {
        boolean wasOtherPlayer = false;
        int xDirection = direction.col();
        int yDirection = direction.row();

        for (int x = position.row() + xDirection, y = position.col() + yDirection;
             x >= 0 && x < boardSize && y >= 0 && y < boardSize;
             x += xDirection, y += yDirection) {
            Disc disc = gameBoard[x][y];

            if (disc == null) {
                return false;  // Hit an empty space, invalid direction
            } else if (disc.getOwner().equals(player)) {
                return wasOtherPlayer;  // Found a player's disc, if there was an opponent's disc before, return true
            } else {
                if (!(disc instanceof UnflippableDisc)) {
                    wasOtherPlayer = true;  // Found an opponent's disc
                }
            }
        }
        return false;
    }

    // Get the current player whose turn it is
    public Player getCurrentPlayer() {
        Player player;
        if (turn)
            player = player1;
        else
            player = player2;
        return player;
    }

    public int countFlips(Position a) {
        HashSet<Disc> flips= new HashSet<>();
        flipsForLocation(a,new Stack<Position>(),flips);
        return flips.size();
    }


    // Return the first player
    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    // Return the second player
    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    // Set the two players for the game
    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    // Check if it's the first player's turn
    @Override
    public boolean isFirstPlayerTurn() {
        return turn;
    }

    // Check if the game has finished (no valid moves left)
    @Override
    public boolean isGameFinished() {
        if (ValidMoves().isEmpty()) {
            int winner = getWinner();
            if (winner == 1)
                player1.addWin();  // Player 1 wins
            else if (winner == 2)
                player2.addWin();  // Player 2 wins
            return true;
        }
        return false;
    }

    // Determine the winner based on the number of discs each player has
    private int getWinner() {
        int count1 = 0;
        int count2 = 0;

        // Count the discs owned by each player
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Disc disc = gameBoard[i][j];
                if (disc != null) {
                    if (gameBoard[i][j].getOwner().equals(player1)) {
                        count1++;
                    } else if (gameBoard[i][j].getOwner().equals(player2)) {
                        count2++;
                    }
                }
            }
        }

        // Return the winner (1 or 2), or 0 for a tie
        if (count1 > count2) {
            System.out.println("Player 1 wins with " + count1 + " discs! Player 2 had " + count2 + " discs.");
            return 1;
        }
        else if (count2 > count1) {
            System.out.println("Player 2 wins with " + count2 + " discs! Player 1 had " + count1 + " discs.");
            return 2;
        }
        return 0;
    }

    // Reset the game board to its initial state
    @Override
    public void reset() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = null;
            }
        }
        // Place the initial discs in the center
        gameBoard[3][3] = new SimpleDisc(player1);
        gameBoard[3][4] = new SimpleDisc(player2);
        gameBoard[4][3] = new SimpleDisc(player2);
        gameBoard[4][4] = new SimpleDisc(player1);
        turn = true;  // Player 1 starts
        player1.reset_bombs_and_unflippedable();
        player2.reset_bombs_and_unflippedable();
        onlyHumen=player1.isHuman()&&player2.isHuman();
        historyMoves=new Stack<>();
    }

    // Undo the last move made
    @Override
    public void undoLastMove() {
        if(!onlyHumen)return;
        System.out.println("Undoing last move:");
        if (historyMoves.empty()) {
            System.out.println("\tNo previous move available to undo");
            return;
        }

        Move lastMove = historyMoves.pop();
        Disc disc = lastMove.disc();
        Player player = disc.getOwner();

        // Restore the resources (bombs or unflippable discs)
        if (disc instanceof BombDisc) player.increase_bomb();
        else if (disc instanceof UnflippableDisc) player.increase_unflippedable();

        Position position = lastMove.position();
        System.out.println("\tUndo: removing " + disc.getType() + " from " + position.toString());
        gameBoard[lastMove.position().row()][lastMove.position().col()] = null;

        // Flip back the discs to their previous state
        Stack<Position> historyFlips = lastMove.getFlips();
        while (!historyFlips.isEmpty()) {
            Position p = historyFlips.pop();
            gameBoard[p.row()][p.col()].setOwner(getCurrentPlayer());
            System.out.println("\tUndo: flipping back " + disc.getType() + " in " + p.toString());
        }
        turn = !turn;  // Switch player turn
        System.out.println();
    }
}

