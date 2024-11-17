public class BombDisc extends Disc {

    public BombDisc(Player player){
        super(player);
    }

    @Override
    public String getType() {
        return "💣";
    }

}
