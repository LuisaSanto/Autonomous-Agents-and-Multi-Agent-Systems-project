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
	public double varFire;
	private boolean protecting;
	private int steps;
	
	public Radar radar;
	
	public Agent(Point position, Board board){ //extend for the parameterized instantiation of agents
		this.position = position;
		this.board = board;
		this.protecting = false;
		this.steps = 0;
		this.varFire = 3;
	}
	
	public void go(int steps){
		if(!isProtecting())
		{
			if(getLocation() == 5)
				protect(2*steps);
			else if(getLocation() > 3)
				protect(steps);
			else {
				Algorithm();
			}
		}
		else
			protecting();
		
		setLocation();
		System.out.println();
		System.out.println("Ganho de mover para UP : " + (unknownGain("UP") + fireRisk("UP")));
		System.out.println("Ganho de mover para DOWN : " + (unknownGain("DOWN") + fireRisk("DOWN")));
		System.out.println("Ganho de mover para LEFT : " + (unknownGain("LEFT") + fireRisk("LEFT")));
		System.out.println("Ganho de mover para RIGHT : " + (unknownGain("RIGHT") + fireRisk("RIGHT")));
	}
	
	public void updateRadar() 
	{
		displayBoard();
		displayAgents();
	}
	
	public void displayBoard() { this.radar.displayBoard(this.locations);}
	public void removeAgents() { this.radar.removeAgents(this.board.UAVs);	}
	public void displayAgents()	{ this.radar.displayAgents(this.board.UAVs); }
	public void setLocations(double[][] locations) { this.locations = locations; }
	public void setRadar(Radar radar) {	this.radar = radar;	}

	/** A: actuators */
	
	private void protecting() {
		if(this.steps == 0) {
			Random r = new Random();
			double value = Math.abs(r.nextGaussian());
			this.board.board[this.position.y][this.position.x] = value;
			this.protecting = false;
			this.board.safe++;
			GraphicalInterface.whites.setText(""+this.board.safe);
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
		
		if(!wallUp()) 
		{
			this.locations[this.position.y-1][this.position.x] = this.board.board[this.position.y-1][this.position.x];
			if(!wallLeft())
				this.locations[this.position.y-1][this.position.x-1] = this.board.board[this.position.y-1][this.position.x-1];
			if(!wallRight())
				this.locations[this.position.y-1][this.position.x+1] = this.board.board[this.position.y-1][this.position.x+1];
		}
		if(!wallDown()) 
		{
			this.locations[this.position.y+1][this.position.x] = this.board.board[this.position.y+1][this.position.x];
			if(!wallLeft())
				this.locations[this.position.y+1][this.position.x-1] = this.board.board[this.position.y+1][this.position.x-1];
			if(!wallRight())
				this.locations[this.position.y+1][this.position.x+1] = this.board.board[this.position.y+1][this.position.x+1];
		}
		if(!wallLeft())
			this.locations[this.position.y][this.position.x-1] = this.board.board[this.position.y][this.position.x-1];
		if(!wallRight())
			this.locations[this.position.y][this.position.x+1] = this.board.board[this.position.y][this.position.x+1];
	}
	
	public void Algorithm()
	{
		double UP = unknownGain("UP") + fireRisk("UP");
		double DOWN = unknownGain("DOWN") + fireRisk("DOWN");
		double LEFT = unknownGain("LEFT") + fireRisk("LEFT");
		double RIGHT = unknownGain("RIGHT") + fireRisk("RIGHT");
		
		if(UP >= DOWN)
			if(UP>=LEFT)
				if(UP>=RIGHT)
					this.position.y--;
				else
					this.position.x++;
			else if(LEFT>=RIGHT)
				this.position.x--;
			else
				this.position.x++;
		else if(DOWN>=LEFT)
			if(DOWN>=RIGHT)
				this.position.y++;
			else
				this.position.x++;
		else if(LEFT>=RIGHT)
			this.position.x--;
		else
			this.position.x++;
		
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
			if(!objectLeft() & !wallLeft())
				this.position.x--;
			else {
				moveRandom();
			}
			break;
		case 1 : 
			if(!objectUp() & !wallUp())
				this.position.y--;
			else {
				moveRandom();
			}
			break;
		case 2 : 
			if(!objectRight() & !wallRight())
				this.position.x++;
			else {
				moveRandom();
			}
			break;
		case 3 : 
			if(!objectDown() & !wallDown())
				this.position.y++;
			else {
				moveRandom();
			}
			break;
		}
	}


	/** B: perceptors */
	
	
	public Point getPosition() { return this.position; }
	public double getLocation() { return this.location; }
	public boolean isProtecting() {	return protecting;	}
	public boolean wallRight() { if(this.position.x == this.board.nX - 1) return true; return false; }
	public boolean wallLeft() { if(this.position.x == 0) return true; return false; }
	public boolean wallUp() { if(this.position.y == 0) return true; return false; }
	public boolean wallDown() { if(this.position.y == this.board.nY - 1) return true; return false; }
	
	private boolean objectRight() 
	{
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x + 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	private boolean objectLeft() 
	{
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x - 1 && a.getPosition().y == this.position.y)
				return true;
		}
		return false;
	}
	
	private boolean objectUp() 
	{
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y - 1)
				return true;
		}
		return false;
	}
	
	private boolean objectDown() 
	{
		for(Agent a : this.board.UAVs)
		{
			if(a.getPosition().x == this.position.x && a.getPosition().y == this.position.y + 1)
				return true;
		}
		return false;
	}
	
	
	/** C: decision process */
	

	public double unknownGain(String direction)
	{
		double result = 3;
		double factor = 0;
		Point tempPosition;
		
		switch(direction)
		{
		case "UP" : 
			if (!wallUp() & !objectUp()) {
				tempPosition = new Point(this.position.x, this.position.y-1);
					if(tempPosition.y-1 >= 0){
						if(this.locations[tempPosition.y-1][tempPosition.x] == -1)
							factor += 0.33;
						if(tempPosition.x-1 >= 0)
							if(this.locations[tempPosition.y-1][tempPosition.x-1] == -1)
								factor += 0.33;
						if(tempPosition.x+1 <= this.board.nX-1)
							if(this.locations[tempPosition.y-1][tempPosition.x+1] == -1)
								factor += 0.33;
					}
			}
			break;
		case "DOWN" :
			if (!wallDown() & !objectDown()) {
				tempPosition = new Point(this.position.x, this.position.y+1);
					if(tempPosition.y+1 <= this.board.nY-1){
						if(this.locations[tempPosition.y+1][tempPosition.x] == -1.0)
						{
							factor += 0.33;
						}
						if(tempPosition.x-1 >= 0)
							if(this.locations[tempPosition.y+1][tempPosition.x-1] == -1.0)
								factor += 0.33;
						if(tempPosition.x+1 <= this.board.nX-1)
							if(this.locations[tempPosition.y+1][tempPosition.x+1] == -1.0)
								factor += 0.33;
					}
			}
			break;
		case "LEFT" :
			if (!wallLeft() & !objectLeft()) {
				tempPosition = new Point(this.position.x-1, this.position.y);
					if(tempPosition.x-1 >= 0){
						if(this.locations[tempPosition.y][tempPosition.x-1] == -1)
							factor += 0.33;
						if(tempPosition.y-1 >= 0)
							if(this.locations[tempPosition.y-1][tempPosition.x-1] == -1)
								factor += 0.33;
						if(tempPosition.y+1 <= this.board.nY-1)
							if(this.locations[tempPosition.y+1][tempPosition.x-1] == -1)
								factor += 0.33;
					}
			}
			break;
		case "RIGHT" :
			if (!wallRight() & !objectRight()) {
				tempPosition = new Point(this.position.x+1, this.position.y);
					if(tempPosition.x+1 <= this.board.nX-1){
						if(this.locations[tempPosition.y][tempPosition.x+1] == -1)
							factor += 0.33;
						if(tempPosition.y-1 >= 0)
							if(this.locations[tempPosition.y-1][tempPosition.x+1] == -1)
								factor += 0.33;
						if(tempPosition.y+1 <= this.board.nY-1)
							if(this.locations[tempPosition.y+1][tempPosition.x+1] == -1)
								factor += 0.33;
					}
			}
			break;
		}

		return result * factor;
	}
	
	public double fireRisk(String direction)
	{
		double result = 6;
		double factor = 0;
		
		switch(direction)
		{
		case "UP" : 
			if (!wallUp() & !objectUp()) {
				if (!wallLeft())
					if(this.locations[this.position.y-1][this.position.x-1] > varFire)
						factor += 0.5;
				if (!wallRight())
					if(this.locations[this.position.y-1][this.position.x+1] > varFire)
						factor += 0.5;
				if(this.locations[this.position.y-1][this.position.x] > varFire)
					factor = 1;
			}
			break;
		case "DOWN" :
			if(!wallDown() & !objectDown()) {
				if (!wallLeft())
					if(this.locations[this.position.y+1][this.position.x-1] > varFire)
						factor += 0.5;
				if (!wallRight())
					if(this.locations[this.position.y+1][this.position.x+1] > varFire)
						factor += 0.5;
				if(this.locations[this.position.y+1][this.position.x] > varFire)
					factor = 1;
			}
			break;
		case "LEFT" :
			if(!wallLeft() & !objectLeft()) {
				if (!wallUp())
					if(this.locations[this.position.y-1][this.position.x-1] > varFire)
						factor += 0.5;
				if (!wallDown())
					if(this.locations[this.position.y+1][this.position.x-1] > varFire)
						factor += 0.5;
				if(this.locations[this.position.y][this.position.x-1] > varFire)
					factor = 1;
			}
			break;
		case "RIGHT" :
			if(!wallRight() & !objectRight()) {
				if (!wallUp())
					if(this.locations[this.position.y-1][this.position.x+1] > varFire)
						factor += 0.5;
				if (!wallDown())
					if(this.locations[this.position.y+1][this.position.x+1] > varFire)
						factor += 0.5;
				if(this.locations[this.position.y][this.position.x+1] > varFire)
					factor = 1;
			}
			break;
		}

		return result * factor;
	}

}
