public class SimpleDisc extends Disc{

    public SimpleDisc(Player player, Position position) {
        this.owner = player;
        this.POSITION = position;
    }
    public SimpleDisc(Player player) {
        this.owner = player;
    }
    @Override
    public String getType() {
        return "⬤";
    }
}
