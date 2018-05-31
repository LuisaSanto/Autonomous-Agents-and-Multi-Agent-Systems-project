package fireprevention;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Radar extends JFrame {

	private static final long serialVersionUID = 1L;

	protected JPanel boardPanel;
	private double[][] board;
	private int nX;
	private int nY;
	
	public Radar(double[][] board, int nX, int nY) {
		this.board = board;
		this.nX = nX;
		this.nY = nY;
		setTitle("FirePrevention Radar");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(640, 540);
		boardPanel = new JPanel();
		boardPanel.setSize(new Dimension(600,500));
		boardPanel.setLocation(new Point(20,20));
		boardPanel.setLayout(new GridLayout(nX,nY));
		for(int i=0; i<nX; i++)
			for(int j=0; j<nY; j++)
				boardPanel.add(new JPanel());
		initializeBoard();
		add(boardPanel);
	}
	
	public void reset() {
		
	}
	
	public void initializeBoard()
	{
		for(int i=0; i<this.nX; i++){
			for(int j=0; j<this.nY; j++){
				double value = board[i][j];
				if(value == -1) 
				{
					JPanel p = ((JPanel)boardPanel.getComponent(i*this.nY+j));
					p.setBackground(new Color(128,128,128,35));
					p.setBorder(BorderFactory.createLineBorder(Color.white));
				}
				else 
				{
					int R = (int) (255*value)/5;
					int G = (int) (255*(5-value))/5; 
					JPanel p = ((JPanel)boardPanel.getComponent(i*nY+j));
					p.setBackground(new Color(R,G,0));
					p.setBorder(BorderFactory.createLineBorder(Color.white));
				}
			}
		}
		boardPanel.invalidate();
	}
	
	public void displayAgents(List<Agent> agents) {
		JPanel p;
		for(Agent agent : agents){	
			if(!agent.wallUp()) 
			{
				p = ((JPanel)boardPanel.getComponent(agent.position.x+(agent.position.y-1)*nX));
				if(board[agent.position.y-1][agent.position.x] > agent.varFire)
					p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
				else
					p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
				if(!agent.wallLeft()) {
					p = ((JPanel)boardPanel.getComponent((agent.position.x-1)+(agent.position.y-1)*nX));
					if(board[agent.position.y-1][agent.position.x-1] > agent.varFire)
						p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
					else
						p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
				}
				if(!agent.wallRight()) {
					p = ((JPanel)boardPanel.getComponent((agent.position.x+1)+(agent.position.y-1)*nX));
					if(board[agent.position.y-1][agent.position.x+1] > agent.varFire)
						p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
					else
						p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
				}
			}
			if(!agent.wallDown()) 
			{
				p = ((JPanel)boardPanel.getComponent((agent.position.x)+(agent.position.y+1)*nX));
				if(board[agent.position.y+1][agent.position.x] > agent.varFire)
					p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
				else
					p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
				if(!agent.wallLeft()){
						p = ((JPanel)boardPanel.getComponent((agent.position.x-1)+(agent.position.y+1)*nX));
						if(board[agent.position.y+1][agent.position.x-1] > agent.varFire)
							p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
						else
							p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
					}
				if(!agent.wallRight()){
					p = ((JPanel)boardPanel.getComponent((agent.position.x+1)+(agent.position.y+1)*nX));
					if(board[agent.position.y+1][agent.position.x+1] > agent.varFire)
						p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
					else
						p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
				}
			}
			if(!agent.wallLeft()) {
				p = ((JPanel)boardPanel.getComponent((agent.position.x-1)+(agent.position.y)*nX));
				if(board[agent.position.y][agent.position.x-1] > agent.varFire)
					p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
				else
					p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
			}
			if(!agent.wallRight()){
				p = ((JPanel)boardPanel.getComponent((agent.position.x+1)+(agent.position.y)*nX));
				if(board[agent.position.y][agent.position.x+1] > agent.varFire)
					p.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
				else
					p.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));	
			}
		}
		for(Agent agent : agents)
		{
			p = ((JPanel)boardPanel.getComponent(agent.position.x+agent.position.y*nX));
			p.setBorder(BorderFactory.createLineBorder(Color.red,3));
		}
		boardPanel.invalidate();
	}
	
	public void removeAgents(List<Agent> agents) {
		for(Agent agent : agents){
			JPanel p = ((JPanel)boardPanel.getComponent(agent.position.x+agent.position.y*nX));
			p.setBorder(BorderFactory.createLineBorder(Color.white));	
		}
		boardPanel.invalidate();
	}
	
	public void displayBoard(double[][] board, int steps)
	{
		for(int i=0; i<nX; i++){
			for(int j=0; j<nY; j++){
				double value = board[i][j];
				JPanel p = ((JPanel)boardPanel.getComponent(i*nY+j));
				if(value!=0) {
					value = value/(steps);
					value*= 3;
				}
				int R = (int) (255*value)/3;
				int G = (int) (255*(3-value)/3); 
				p.setBackground(new Color(R,G,0));
				p.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
		boardPanel.invalidate();
	}

	public void displayBoard(double[][] board) {
		for(int i=0; i<nX; i++){
			for(int j=0; j<nY; j++){
				double value = board[i][j];
				if (value != -1) {
					JPanel p = ((JPanel)boardPanel.getComponent(i*nY+j));
					if(value == 5 & !p.getBackground().equals(Color.black))
					{
						p.setBackground(Color.black);
						p.setBorder(BorderFactory.createLineBorder(Color.white));
					}
					else if(value != 5)
					{
						int R = (int) (255*value)/5;
						int G = (int) (255*(5-value))/5; 
						p.setBackground(new Color(R,G,0));
						p.setBorder(BorderFactory.createLineBorder(Color.white));
					}
				}
			}
		}
		boardPanel.invalidate();
	}
}