package fireprevention;

import java.awt.Point;

/**
 * Agent behavior
 * @author Rui Henriques
 */
public class Agent {

	public Point position;
	
	public Agent(Point position){ //extend for the parameterized instantiation of agents
		this.position = position;
	} 
	
	public void go(){
		if(position.x>10) position.x--;
		else position.x++;
	}
	
	/** A: actuators */

	/** B: perceptors */

	/** C: decision process */

}
