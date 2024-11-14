public class BombDisc extends Disc {

    public BombDisc(Player player, Position position) {
        this.owner = player;
        this.POSITION = position;
    }
    public BombDisc(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return "💣";
    }
}
