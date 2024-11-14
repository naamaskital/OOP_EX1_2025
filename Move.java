import javax.swing.*;
import java.util.List;

public class Move {
    public ActionMap flippedDiscs;
    private Position position;
    private Disc disc;


    public Move(Position position, Disc disc){
        this.position = position;
        this.disc = disc;
    }


    public Position position(){
        return position;
    }
    public Disc disc(){
        return disc;
    }
}
