public class UnflippableDisc extends Disc{

    public UnflippableDisc(Player player, Position position) {
        this.player = player;
        this.position = position;
    }
    public UnflippableDisc(Player player) {
        this.player = player;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}