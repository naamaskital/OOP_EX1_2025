import javax.swing.*;
import java.util.List;
import java.util.Stack;

public class Move {
    private Stack<Position> flips=new Stack<>();
    private Position position;
    private Disc disc;

    /**
     * Constructor to initialize a Move object.
     * @param d - The disc placed by the player (Disc object).
     * @param p - The position on the board where the disc is placed (Position object).
     * @param f - A stack of positions representing the flips caused by the move.
     */
    public Move(Disc d,Position p, Stack<Position> f) {
        this.position=p;
        this.disc=d;
        this.flips=f;
    }
    /**
     * Getter for the disc.
     * @return Disc - The disc associated with this move.
     */
    public Disc disc(){
        return disc;
    }

    /**
     * Getter for the stack of positions to flip.
     * @return Stack<Position> - A stack of positions flipped by this move.
     */
    public Stack<Position> getFlips(){
        return flips;
    }

    /**
     * Setter for the stack of positions to flip.
     * @param flips - A stack of positions flipped by this move.
     */
    public void setFlips(Stack <Position> flips){
        this.flips=flips;
    }



    /**
     * Getter for the position of the move.
     * @return Position - The position where the disc is placed.
     */
    public Position position() {
        return this.position;
    }
}
