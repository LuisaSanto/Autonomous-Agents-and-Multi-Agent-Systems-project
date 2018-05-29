package fireprevention;

public class Utility {

    public long states;
    public static final int RIGHT=0;
    public static final int UP=1;
    public static final int LEFT=2;
    public static final int DOWN=3;

    public static int getDOWN() {
        return DOWN;
    }

    public static int getLEFT() {

        return LEFT;
    }

    public static int getUP() {

        return UP;
    }

    public static int getRIGHT() {

        return RIGHT;
    }

    public long getStates() {

        return states;
    }

    public void setStates(long states) {
        this.states = states;
    }
}
