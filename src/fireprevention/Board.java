package fireprevention;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public int failed, safe, steps;
	public double[][] time;
	public Radar timeRadar;
	Path file;
	private int fileN;
	public List<String> lines;
	
	public Board(int nX, int nY, int nUAVs) {
		this.nX = nX;
		this.nY = nY;
		this.nUAVs = nUAVs;
		this.failed = 0;
		this.steps = 0;
		this.safe = 0;
		this.fileN = 0;
		initialize();
	}

	private void initialize() {
		this.file = Paths.get("graph"+this.fileN+".txt");
		lines = new ArrayList<String>();
		Random r = new Random();
		board = new double[nX][nY];
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++) 
				board[i][j] = Math.abs(r.nextGaussian());
			
		
		UAVs = new ArrayList<Agent>();
		double[][] locations = new double[nX][nY];
		time = new double[nX][nY];
		for(int m=0; m<nX; m++)
			for(int j=0; j<nY; j++) {
				time[m][j] = 0;
				locations[m][j] = -1;
			}
		for(int i=0; i<nUAVs && i<nY; i++) {
			//locations[i][0] = board[i][0];
			UAVs.add(new Agent(new Point(0,i), this));
		}
		Radar radar = new Radar(locations, nX, nY);
		radar.setVisible(true);
		timeRadar = new Radar(time, nX, nY);
		timeRadar.setVisible(true);
		for(Agent a : UAVs) { 
			a.setRadar(radar);
			a.setLocations(locations);
			a.setLocation();
		}
		radar.displayBoard(locations);
		timeRadar.displayBoard(time, steps);
		radar.displayAgents(UAVs);
		
	}

	
	/** B: Elicit agent actions */
	
	RunThread runThread;

	public class RunThread extends Thread {
		
		int time;
		double decay;
		int steps;
		int maxS;
		Board board;
		
		public RunThread(int time, double decay, int steps, int maxS, Board board){
			this.maxS = maxS;
			this.time = time;
			this.decay = decay;
			this.steps = steps;
			this.board = board;
		}
		
	    public void run() {
	    	while(true){
	    		if(board.steps < maxS | maxS == 0) {
	    			System.out.println(maxS);
	    			System.out.println(board.steps);
			    	removeAgents();
			    	updateHeatMap(this.decay);
			    	updateTime();
			    	for(Agent a : UAVs) a.radar.removeAgents(UAVs);
					for(Agent a : UAVs) { 
						a.go(this.steps);
					}
					for(Agent a : UAVs) { a.updateRadar(); break;}
					displayBoard();
					displayAgents();
					
					try {
						sleep(time*10);
					} catch (InterruptedException e) {
					}
	    		}
	    		else if(GraphicalInterface.run.getText().equals("Stop")) {
	    		{
	    				GraphicalInterface.run.setText("Run");
	    				runThread.interrupt();
	    				runThread.stop();
	    			}
	    		}
						
	    	}
	    }
	}
	
	public void run(int time, double decay, int steps, int maxS) {
		runThread = new RunThread(time,decay,steps,maxS, this);
		runThread.start();
		displayAgents();
	}

	@SuppressWarnings("deprecation")
	public void reset() {
		
		if(GraphicalInterface.run.getText().equals("Stop")) {
			GraphicalInterface.run.setText("Run");
			runThread.interrupt();
			runThread.stop();
		}
		
		this.steps = 0;
		this.failed = 0;
		this.safe = 0;
		GraphicalInterface.blacks.setText(""+this.failed);
		GraphicalInterface.steps.setText(""+this.steps);
		GraphicalInterface.whites.setText(""+this.safe);
		
		for(Agent a : UAVs) {
			a.radar.setVisible(false);
			a.radar.dispose(); 
			break;
			}
		timeRadar.setVisible(false);
		timeRadar.dispose();
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.fileN++;
		initialize();
		displayBoard();
		displayAgents();
		
	}

	public void step(double decay, int steps) {
		removeAgents();
		updateHeatMap(decay);
		updateTime();
		for(Agent a : UAVs) a.removeAgents();
		for(Agent a : UAVs) a.go(steps);
		for(Agent a : UAVs) a.updateRadar(); 
		displayBoard();
		displayAgents();
	}

	private void updateTime() {
		for(int m=0; m<nX; m++)
			for(int j=0; j<nY; j++) {
				time[m][j] = time[m][j] + 1;
			}
		timeRadar.displayBoard(time,steps);
	}

	public void updateHeatMap(double decay) 
	{
		for(int i=0; i<nX; i++)
		{
			for(int j=0; j<nY; j++)
			{
				double value = board[i][j];
				
				if(value < 5)
				{
					
					Random r = new Random();
					int low = 0;
					int high = 3;
					int signal = r.nextInt(high-low) + low;
					low = 1;
					high = 4;
					double factor = (r.nextInt(high-low) + low) * decay;
					
					if(value * (1+factor) > 5) 
					{
						board[i][j] = 5;
						this.failed++;
						GraphicalInterface.blacks.setText(""+this.failed);
						
					}
					else
					{
						if(signal == 0)
							board[i][j] = board[i][j] * (1-factor);
						else
							board[i][j] = board[i][j] * (1+factor);
					}
				}
				else if (value == 5) {
					this.failed++;
					GraphicalInterface.blacks.setText(""+this.failed);
				}
			}
		}
		this.steps++;
		GraphicalInterface.steps.setText(""+this.steps);
		lines.add(""+this.failed);
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
