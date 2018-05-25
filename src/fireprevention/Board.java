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
		UAVs = new ArrayList<Agent>();
		for(int i=0; i<nUAVs && i<nY; i++) UAVs.add(new Agent(new Point(0,i)));
		
		Random r = new Random();
		board = new double[nX][nY];
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				board[i][j] = Math.abs(r.nextGaussian());
	}

	
	/** B: Elicit agent actions */
	
	RunThread runThread;

	public class RunThread extends Thread {
		
		int time;
		
		public RunThread(int time){
			this.time = time;
		}
		
	    public void run() {
	    	while(true){
		    	removeAgents();
				for(Agent a : UAVs) a.go();
				displayAgents();
				try {
					sleep(time*10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    }
	}
	
	public void run(int time) {
		runThread = new RunThread(time);
		runThread.start();
		displayAgents();
	}

	public void reset() {
		initialize();
		displayBoard();
		displayAgents();	
	}

	public void step() {
		removeAgents();
		for(Agent a : UAVs) a.go();
		displayAgents();
	}

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
