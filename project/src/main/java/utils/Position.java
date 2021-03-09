package utils;

public class Position {
	
	public final int row;
	public final int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", row, col);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Position other = (Position) obj;
        if (this.row != other.row || this.col != other.col) {
        	return false;
        }

        return true;
    }
}
