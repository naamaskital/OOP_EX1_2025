public class UnflippableDisc extends Disc{

    public UnflippableDisc(Player player, Position position) {
        this.owner = player;
        this.position = position;
    }
    public UnflippableDisc(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return "⭕";
    }
    public Position getPosition() {return this.position;}

    @Override
    public void setOwner(Player player) {
        return;
    }
}
