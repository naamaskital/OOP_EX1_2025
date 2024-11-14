import java.util.Objects;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col){
        this.row=row;
        this.col=col;
    }
    public int row(){ return this.row;}
    public int col(){ return this.col;}

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return   row == position.row && col == position.col;
    }
}
