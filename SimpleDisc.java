public class SimpleDisc extends Disc{

    public SimpleDisc(Player player, Position position) {
        this.player = player;
        this.POSITION = position;
    }
    public SimpleDisc(Player player) {
        this.player = player;
    }
    @Override
    public String getType() {
        return "⬤";
    }
}
