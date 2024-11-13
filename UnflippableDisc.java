public class UnflippableDisc extends Disc{

    public UnflippableDisc(Player player, Position position) {
        this.owner = player;
        this.POSITION = position;
    }
    public UnflippableDisc(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
