/**
 * SimpleDisc - Represents a basic type of disc in the game.
 * This class extends the abstract class Disc and provides
 * a concrete implementation for the getType method.
 *
 * The SimpleDisc is owned by a player and is represented visually
 * by the symbol "⬤".
 */

public class SimpleDisc extends Disc{
    /**
     * Constructor to create a SimpleDisc object.
     * It initializes the owner of the disc using the parent class constructor.
     *
     * @param player The player who owns this disc.
     */
    public SimpleDisc(Player player){
        super(player); // Call the constructor of the parent class (Disc).
    }

    /**
     * Get the type of the disc as a string symbol.
     * The symbol "⬤" represents a simple, standard disc in the game.
     *
     * @return A string representing the type of the disc.
     */
    @Override
    public String getType() {
        return "⬤"; // Return the symbol for a simple disc.
    }
}
