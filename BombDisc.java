/**
 * Represents a Bomb Disc, which is a subclass of the Disc class.
 * This disc signifies the "💣" type and is associated with a specific player.
 */
public class BombDisc extends Disc {

    /**
     * Constructs a BombDisc for a specific player.
     *
     * @param player The player to whom this BombDisc is associated.
     */
    public BombDisc(Player player){
        super(player);
    }

    /**
     * Returns the type of the disc, which in this case is "💣".
     *
     * @return A string representing the type of the disc ("💣").
     */
    @Override
    public String getType() {
        return "💣";
    }

}