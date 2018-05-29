package fireprevention;

import java.awt.*;

public class Position {
    private double location;
    private double[][] locations;
    public Point position;
    int heuristicCost = 0;

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

    int finalCost = 0;

    public Position() {
    }

    public Position(double location) {
        this.location = location;
    }

    public Position(double[][] locations) {
        this.locations = locations;
    }

    public Position(Point position) {
        this.position = position;
    }

    public Position(double location, double[][] locations, Point position) {
        this.location = location;
        this.locations = locations;
        this.position = position;
    }

    public double getLocation() {
        return location;
    }

    public Point getPosition() {
        return position;
    }

    public double[][] getLocations() {
        return locations;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    public void setLocations(double[][] locations) {
        this.locations = locations;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
