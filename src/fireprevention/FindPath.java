package fireprevention;

import java.util.Comparator;
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
    private Position[][] locations;
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

    public Position[][] getLocations() {
        return locations;
    }

    public void setLocations(Position[][] locations) {
        this.locations = locations;
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

    static class PositionComparator implements Comparator<Position>{
        @Override
        public int compare(Position p1, Position p2) {
            Position c1 = (Position) p1;
            Position c2 = (Position) p2;

            return c1.finalCost<c2.finalCost? -1 : c1.finalCost>c2.finalCost ? 1:0;
        }
    }

    //A STAR
    public static PathDetails findPathAstar(Board board, Position source, Position destination) {
        return null;
    }
}
