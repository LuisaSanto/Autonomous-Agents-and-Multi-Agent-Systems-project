package fireprevention;

import java.util.Random;
import java.awt.Point;

/**
 * Agent behavior
 * @author Rui Henriques
 */
public class Agent {

	public Point position;
	private Board board;
	private double location;
	private double[][] locations;
	
	private boolean protecting;
	private int steps;
	
	public Radar radar;
	
	public Agent(Point position, double[][] locations, Board board){ //extend for the parameterized instantiation of agents
		this.position = position;
		this.locations = locations;
		this.board = board;
		this.radar = new Radar(locations, board.nX, board.nY);
		this.radar.setVisible(true);
		this.protecting = false;
		this.steps = 0;
	}
	
	public void go(int steps){
		if(!isProtecting())
		{
			if(getLocation() > 3 && getLocation() < 5)
				protect(steps);
			else {
				moveRandom();
			}
		}
		else
			protecting();
		
		setLocation();
	}
	
	public void updateRadar() 
	{
		this.radar.displayBoard(this.locations);
		this.radar.displayAgents(this.board.UAVs, this.position);
	}
	
	public void removeAgents()
	{
		this.radar.removeAgents(this.board.UAVs);
	}
	
	public void displayAgents()
	{
		this.radar.displayAgents(this.board.UAVs, this.position);
	}

	/** A: actuators */
	
	private void protecting() {
		if(this.steps == 0) {
			Random r = new Random();
			double value = Math.abs(r.nextGaussian());
			this.board.board[this.position.y][this.position.x] = value;
			this.protecting = false;
		}
		else
			this.steps--;
	}
	
	private void protect(int steps) {
		this.steps = steps;
		this.protecting = true;
	}

	public void setLocation() 
	{ 
		double value = this.board.board[this.position.y][this.position.x];
		this.location = value;
		this.locations[this.position.y][this.position.x] = value;
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
	
	public boolean isProtecting()
	{
		return protecting;
	}
	
	public double getLocation() 
	{ 
		return this.location; 
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	private boolean objectRight() 
	{
		if(this.position.x == this.board.nX - 1) return true;
		
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x + 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	private boolean objectLeft() 
	{
		if(this.position.x == 0) return true;
		
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x - 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	private boolean objectUp() 
	{
		if(this.position.y == 0) return true;
		
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y - 1)
				return true;
		}
		return false;
	}
	
	private boolean objectDown() 
	{
		if(this.position.y == this.board.nY - 1) return true;
		
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y + 1)
				return true;
		}
		return false;
	}
	
	
	
	
	/** C: decision process */

}
