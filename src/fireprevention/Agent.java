package fireprevention;

import java.awt.Point;

/**
 * Agent behavior
 * @author Rui Henriques
 */
public class Agent {

	public Point position;
	private double location;
	private double[][] locations;
	private int nX;
	private int nY;
	public Radar radar;
	
	public Agent(Point position, int nX, int nY, double[][] locations){ //extend for the parameterized instantiation of agents
		this.position = position;
		this.locations = locations;
		this.nX = nX;
		this.nY = nY;
		this.radar = new Radar(locations, nX, nY);
		this.radar.setVisible(true);
	}
	
	public void go(){
		if(position.x>=this.nX/2) position.x--;
		else position.x++;
	}

	
	/** A: actuators */
	
	public void updateRadar() 
	{
		this.radar.displayBoard(this.locations);
	}
	
	public void setLocation(double value) 
	{ 
		this.location = value; 
		this.locations[this.position.y][this.position.x] = this.location;
	}

	

	/** B: perceptors */
	
	
	/** C: decision process */

}
