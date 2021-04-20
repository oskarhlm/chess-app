package utils;

public final class Position {
	
	public int row;
	public int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	@Override
	public String toString() {
		String columnLetter = "abcdefgh";
		return String.format("%s%s", columnLetter.charAt(col), 8 - row);
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
