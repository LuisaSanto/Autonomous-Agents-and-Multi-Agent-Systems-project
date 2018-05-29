package fireprevention;

import java.util.PriorityQueue;

public class FindPath {

    public static final int V_H_COST = 1;
    static PriorityQueue<Position> open;
    static PriorityQueue<Position> alternateOpen;
    static boolean[][] alternateClosed;
    static Path path;
    static boolean closed[][];
    static Position start;
    static Position end;
    private Board board;
    static int statesExpanded = 0;

    public static int getvHCost() {
        return V_H_COST;
    }

    public static int getStatesExpanded() {
        return statesExpanded;
    }

    public static void setStatesExpanded(int statesExpanded) {
        FindPath.statesExpanded = statesExpanded;
    }

    public Board getBoard() {

        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public static Position getEnd() {

        return end;
    }

    public static void setEnd(Position end) {
        FindPath.end = end;
    }

    public static Position getStart() {

        return start;
    }

    public static void setStart(Position start) {
        FindPath.start = start;
    }

    public static boolean[][] getClosed() {

        return closed;
    }

    public static void setClosed(boolean[][] closed) {
        FindPath.closed = closed;
    }

    public static Path getPath() {

        return path;
    }

    public static void setPath(Path path) {
        FindPath.path = path;
    }

    public static boolean[][] getAlternateClosed() {

        return alternateClosed;
    }

    public static void setAlternateClosed(boolean[][] alternateClosed) {
        FindPath.alternateClosed = alternateClosed;
    }

    public static PriorityQueue<Position> getAlternateOpen() {

        return alternateOpen;
    }

    public static void setAlternateOpen(PriorityQueue<Position> alternateOpen) {
        FindPath.alternateOpen = alternateOpen;
    }

    public static PriorityQueue<Position> getOpen() {

        return open;
    }

    public static void setOpen(PriorityQueue<Position> open) {
        FindPath.open = open;
    }
}
