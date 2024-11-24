import java.util.*;

public class GreedyAI extends AIPlayer{


    public GreedyAI(boolean isPlayerOne) {

        super(isPlayerOne);
    }

    /**
     * makeMove - Determines the best move for the AI player.
     * @param gameStatus - The current state of the game (PlayableLogic interface).
     * @return Move - The move selected by the AI.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // Cast the general game status to a specific GameLogic instance
        GameLogic gameLogic = (GameLogic) gameStatus;

        // Retrieve the current player
        Player player = gameLogic.getCurrentPlayer();

        // Get a list of all valid moves for the current player
        List<Position> valid = gameLogic.ValidMoves();

        // Disc object to represent the player's disc
        Disc disc;

        // List to store the count of flips for each valid move
        List<Integer> countValid = new ArrayList<>();

        // Variables to track the maximum flips and its corresponding index
        int max = 0;
        int indexMax = 0;

        // List to store all positions with the maximum flip count
        List<Position> sameCount = new ArrayList<>();

        // Iterate through all valid moves
        for (int i = 0; i < valid.size(); i++) {
            Position p = valid.get(i); // Get the current position
            int currentCount = gameLogic.countFlips(p); // Count flips for the current position
            countValid.add(currentCount); // Add the flip count to the list

            // Update max and track positions with the highest flip count
            if (currentCount > max) {
                max = currentCount; // Update maximum flips
                indexMax = i; // Store the index of this move
                sameCount.clear(); // Clear the previous list of max positions
                sameCount.add(valid.get(i)); // Add the new max position
            } else if (currentCount == max) {
                // Add position to the list if it ties with the current maximum
                sameCount.add(valid.get(i));
            }
        }

        // Create a disc for the current player
        disc = new SimpleDisc(player);

        // If there is only one move with the maximum flip count, return it
        if (sameCount.size() == 1) {
            return new Move(disc, valid.get(indexMax), null);
        } else {
            // Sort positions with the same flip count using the custom comparator
            Collections.sort(sameCount, new Position.MaxRelevantComparator());

            // Return the last position in the sorted list
            return new Move(disc, sameCount.get(sameCount.size() - 1), null);
        }
    }


}

