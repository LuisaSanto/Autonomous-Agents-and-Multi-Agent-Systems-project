package fireprevention;

public class Position {
    int row;
    int column;
    int heuristicCost = 0;
    Position parent = null;
    int finalCost = 0;

    public Position() {
    }

    public Position(int row, int column, int heuristicCost, Position parent, int finalCost) {
        this.row = row;
        this.column = column;
        this.heuristicCost = heuristicCost;
        this.parent = parent;
        this.finalCost = finalCost;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Position getParent() {
        return parent;
    }

    public void setParent(Position parent) {
        this.parent = parent;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }
}
