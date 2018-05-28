package fireprevention;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Environment
 * @author Rui Henriques
 */
public class Board {

	/** A: Environment */

	public int nX, nY, nUAVs;
	public List<Agent> UAVs;
	public double[][] board;
	public GraphicalInterface GUI;
	
	public Board(int nX, int nY, int nUAVs) {
		this.nX = nX;
		this.nY = nY;
		this.nUAVs = nUAVs;
		initialize();
	}

	private void initialize() {
		Random r = new Random();
		board = new double[nX][nY];
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++) 
				board[i][j] = Math.abs(r.nextGaussian());
			
		
		UAVs = new ArrayList<Agent>();
		for(int i=0; i<nUAVs && i<nY; i++) {
			double[][] locations = new double[nX][nY];
			for(int m=0; m<nX; m++)
				for(int j=0; j<nY; j++) 
					locations[m][j] = -1;
			locations[i][0] = board[i][0];
			UAVs.add(new Agent(new Point(0,i), nX, nY, locations));
		}
	}

	
	/** B: Elicit agent actions */
	
	RunThread runThread;

	public class RunThread extends Thread {
		
		int time;
		double decay;
		int steps;
		
		public RunThread(int time, double decay, int steps){
			this.time = time;
			this.decay = decay;
			this.steps = steps;
		}
		
	    public void run() {
	    	while(true){
		    	removeAgents();
		    	updateHeatMap(this.decay);
				for(Agent a : UAVs) {
					a.go();
					a.setLocation(board[a.position.y][a.position.x]); // Gives the Agent the status of the ground on his current location
					a.updateRadar(); // Updates the Agent Radar to reflect the action above
				}
				displayBoard();
				displayAgents();
				try {
					sleep(time*10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    }
	}
	
	public void run(int time, double decay, int steps) {
		runThread = new RunThread(time,decay,steps);
		runThread.start();
		displayAgents();
	}

	public void reset() {
		for(Agent a : UAVs) {
			a.radar.setVisible(false);
			a.radar.dispose(); }
		initialize();
		displayBoard();
		displayAgents();
	}

	public void step(double decay, int steps) {
		removeAgents();
		updateHeatMap(decay);
		for(Agent a : UAVs) { 
			a.go();
			a.setLocation(board[a.position.y][a.position.x]); // Gives the Agent the status of the ground on his current location
			a.updateRadar(); // Updates the Agent Radar to reflect the action above
		}
		displayBoard();
		displayAgents();
	}

	public void updateHeatMap(double decay) 
	{
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
			{
				if(board[i][j] < 5)
				{
					double value = board[i][j] * decay;
					if(value > 5)
						board[i][j] = 5;
					else
						board[i][j] = value;
				}	
			}	
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		runThread.interrupt();
		runThread.stop();
		displayAgents();
	}
	
	public void displayBoard(){
		GUI.displayBoard(this);
	}

	public void displayAgents(){
		GUI.displayAgents(this);
	}
	
	public void removeAgents(){
		GUI.removeAgents(this);
	}
}
