public class BombDisc extends Disc {

    public BombDisc(Player player, Position position) {
        this.player = player;
        this.POSITION = position;
    }
    public BombDisc(Player player) {
        this.player = player;
    }

    @Override
    public String getType() {
        return "💣";
    }
}
