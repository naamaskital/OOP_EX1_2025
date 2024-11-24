import java.util.Comparator;
import java.util.Objects;
/**
 * The Position class represents a point in a 2D grid.
 * It contains two fields: row and col, which define the coordinates of the position.
 */

public class Position {
    private final int row; // The row index of the position
    private final int col; // The column index of the position

    /**
     * Constructor to initialize the Position object with a specific row and column.
     * @param row The row coordinate of the position.
     * @param col The column coordinate of the position.
     */
    public Position(int row, int col){
        this.row=row; // Set the row value
        this.col = col; // Set the column value
    }

    /**
     * Getter method for the row.
     * @return The row coordinate of the position.
     */
    public int row(){ return this.row;}

    /**
     * Getter method for the column.
     * @return The column coordinate of the position.
     */
    public int col(){ return this.col;}


    /**
     * Overrides the default toString method to provide a custom string representation of the position.
     * @return A string in the format "(row, col)" representing the position.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }


    /**
     * Overrides the default equals method to compare two Position objects.
     * Two Position objects are considered equal if both their row and col values are the same.
     * @param obj The object to compare with this position.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Check for null or incompatible types
        Position position = (Position) obj;  // Cast the object to Position
        return row == position.row && col == position.col; // Compare row and col values
    }

    /**
     * MaxRelevantComparator - Comparator for sorting positions.
     * Positions are sorted by column first, then by row.
     */
    public static class MaxRelevantComparator implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2) {
            int colComparison = Integer.compare(p1.col(), p2.col());
            // Compare positions by column; if equal, compare by row
            if (colComparison != 0) {
                return colComparison;
            } else {
                return Integer.compare(p1.row(), p2.row());
            }
        }
    }


}
