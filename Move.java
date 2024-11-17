import javax.swing.*;
import java.util.List;
import java.util.Stack;

public class Move {
    private Stack<Position> flips=new Stack<>();
    private Position position;
    private Disc disc;


    public Move(Disc d,Position p, Stack<Position> f) {
        this.position=p;
        this.disc=d;
        this.flips=f;
    }
    public Disc disc(){
        return disc;
    }
    public Stack<Position> getFlips(){
        return flips;
    }
    public void setFlips(Stack <Position> flips){
        this.flips=flips;
    }

    public Position position() {
        return this.position;
    }
}
