/**
 * BombDisc - Represents a special type of disc in the game.
 * This disc have specific rules or effects in the game logic
 *
 * The BombDisc is owned by a player and is represented visually
 * by the symbol "💣".
 */

public class BombDisc extends Disc {

    /**
     * Constructor to create a BombDisc object.
     * It initializes the owner of the disc using the parent class constructor.
     *
     * @param player The player who owns this disc.
     * */

    public BombDisc(Player player){
        super(player); // Call the constructor of the parent class (Disc).
    }

    /**
     * Get the type of the disc as a string symbol.
     * The symbol "💣" represents a bomb disc in the game.
     *
     * @return A string representing the type of the disc.
     */
    @Override
    public String getType() {
        return "💣"; // Return the symbol for a bomb disc.
    }

}
