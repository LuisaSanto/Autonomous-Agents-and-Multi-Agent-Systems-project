package fireprevention;

import java.util.ArrayList;

public class Path {
    ArrayList<Position> piPath = new ArrayList<Position>();
    ArrayList<ArrayList<Position>> alternatePaths = new ArrayList<>();


    public Position returnParent(Position p) {
        int pos = piPath.indexOf(p);
        if (pos  < this.piPath.size()-1 && pos >=0) return piPath.get(pos + 1);
        else return null;
    }

    public Position returnSuccessor(Position p){
        int pos = piPath.indexOf(p);
        if (pos  < this.piPath.size() && pos >0) return piPath.get(pos - 1);
        else return null;
    }

    public void addLocation(Position nextLocation, ArrayList<Position> alternatePath) {
        piPath.add(nextLocation);
        alternatePaths.add(alternatePath);
    }

    public ArrayList<Position> getAlternatePath(Position to) {
        int pos = piPath.indexOf(to);
        if (pos>0) return alternatePaths.get(pos-1);
        else return new ArrayList<Position>();

    }
}
