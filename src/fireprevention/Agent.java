package fireprevention;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Point;

/**
 * Agent behavior
 * @author Rui Henriques
 */
public class Agent {

	public Point position;
	private double location;
	private double[][] locations;
	private List<Agent> agents;
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
		//if(position.x>=this.nX/2) position.x--;
		//else position.x++;
		moveRandom();
		//System.out.println();
		//System.out.println(this.position.x);
		//System.out.println(this.position.y);
		
	}
	
	public void updateRadar() 
	{
		this.radar.displayBoard(this.locations);
	}

	/** A: actuators */

	public void setLocation(double value) 
	{ 
		this.location = value; 
		this.locations[this.position.y][this.position.x] = this.location;
	}
	
	public void setAgents(List<Agent> agents)
	{
		this.agents = agents;
	}
	
	public void moveRandom()
	{
		Random r = new Random();
		int low = 0;
		int high = 4;
		int result = r.nextInt(high-low) + low;
		switch(result)
		{
		case 0 : 
			if(!objectLeft())
				this.position.x--;
			else {
				moveRandom();
			}
			break;
		case 1 : 
			if(!objectUp())
				this.position.y--;
			else {
				moveRandom();
			}
			break;
		case 2 : 
			if(!objectRight())
				this.position.x++;
			else {
				moveRandom();
			}
			break;
		case 3 : 
			if(!objectDown())
				this.position.y++;
			else {
				moveRandom();
			}
			break;
		}
		
	}

	/** B: perceptors */
	
	public double getLocation() 
	{ 
		return this.location; 
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public boolean objectRight() 
	{
		if(this.position.x == this.nX - 1) return true;
		
		for(Agent a : agents)
		{
			if(a.getPosition().x == this.position.x + 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	public boolean objectLeft() 
	{
		if(this.position.x == 0) return true;
		
		for(Agent a : agents)
		{
			if(a.getPosition().x == this.position.x - 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	public boolean objectUp() 
	{
		if(this.position.y == 0) return true;
		
		for(Agent a : agents)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y - 1)
				return true;
		}
		return false;
	}
	
	public boolean objectDown() 
	{
		if(this.position.y == this.nY - 1) return true;
		
		for(Agent a : agents)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y + 1)
				return true;
		}
		return false;
	}
	
	
	
	
	/** C: decision process */

}
