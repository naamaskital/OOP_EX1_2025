import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer {

    private static Random random=new Random();

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
      }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        if (gameStatus instanceof GameLogic) {
            GameLogic gameLogic = (GameLogic) gameStatus;
            List<Position> possibleOptions = gameLogic.ValidMoves();
            Player player = gameLogic.getCurrentPlayer();

            if (possibleOptions.isEmpty()) {
                return null;  // No valid moves available, return null or some default behavior.
            }

            boolean hasBombs = player.getNumber_of_bombs() > 0;
            boolean hasUnflip = player.getNumber_of_unflippedable() > 0;

            // Randomly select a position from the available options.
            int location = random.nextInt(possibleOptions.size());
            Position selectedPosition = possibleOptions.get(location);

            // Determine the type of disc to play based on available resources.
            Disc disc = createRandomDisc(gameLogic, hasBombs, hasUnflip);

            // Create and return the move.
            return new Move(disc, selectedPosition, null);
        }

        return null;
    }

    // Helper method to create a random disc type based on player resources.
    private static Disc createRandomDisc(GameLogic gameLogic, boolean hasBombs, boolean hasUnflip) {
        Disc disc;

        if (!(hasBombs || hasUnflip)) {
            // Only SimpleDisc is available when no bombs or unflippable discs are available.
            disc = new SimpleDisc(gameLogic.getCurrentPlayer());
        } else {
            int kind;
            if (hasBombs && hasUnflip) {
                // Randomly choose between Simple, Bomb, or Unflippable disc.
                kind = random.nextInt(3);
            } else {
                // Choose between Simple and one of the available discs.
                kind = random.nextInt(2);
            }

            switch (kind) {
                case 0:
                    disc = new SimpleDisc(gameLogic.getCurrentPlayer());
                    break;
                case 1:
                    disc = hasBombs ? new BombDisc(gameLogic.getCurrentPlayer()) : new UnflippableDisc(gameLogic.getCurrentPlayer());
                    break;
                default:
                    disc = new BombDisc(gameLogic.getCurrentPlayer());
                    break;
            }
        }
        return disc;
    }
}

