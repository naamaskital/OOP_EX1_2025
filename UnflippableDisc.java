public class UnflippableDisc extends Disc{

    public UnflippableDisc(Player player){
        super(player);
    }
    @Override
    public String getType() {
        return "⭕";
    }

    @Override
    public void setOwner(Player player) {
        return;
    }
}
