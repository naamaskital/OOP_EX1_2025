 abstract class Disc{
    protected Player player;
    protected  Position POSITION;
    /**
 * Get the player who owns the Disc.
 *
 * @return The player who is the owner of this game disc.
 */
public Player getOwner(){
    return player;
}

/**
 * Set the player who owns the Disc.
 *
 */
public void setOwner(Player player){
    this.player = player;
}

/**
 * Get the type of the disc.
 * use the:
 *          "⬤",         "⭕"                "💣"
 *      Simple Disc | Unflippedable Disc | Bomb Disc |
 * respectively.
 */
public abstract String getType();

}