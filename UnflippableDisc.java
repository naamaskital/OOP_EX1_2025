/**
 * UnflippableDisc - Represents a special type of disc in the game.
 * This disc cannot be flipped to change its owner, making it a fixed disc
 * that always belongs to the initial player who placed it.
 *
 * The UnflippableDisc is visually represented by the symbol "⭕".
 */

public class UnflippableDisc extends Disc{

    /**
     * Constructor to create an UnflippableDisc object.
     * It initializes the owner of the disc using the parent class constructor.
     *
     * @param player The player who owns this disc.
     */
    public UnflippableDisc(Player player) {
        super(player); // Call the constructor of the parent class (Disc).
    }

    /**
     * Get the type of the disc as a string symbol.
     * The symbol "⭕" represents an unflippable disc in the game.
     *
     * @return A string representing the type of the disc.
     */
    @Override
    public String getType() {
        return "⭕";// Return the symbol for an unflippable disc.
    }

    /**
     * Prevent changing the owner of the disc.
     * This overrides the parent class method and ensures the disc remains
     * owned by the initial player who placed it.
     *
     * @param player The player attempting to take ownership of the disc (ignored).
     */

    @Override
    public void setOwner(Player player) {
        return; // Do nothing, ensuring the owner remains unchanged.
    }
}
