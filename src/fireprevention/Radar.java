package fireprevention;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Radar extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel boardPanel;
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
					p.setBackground(new Color(128,128,128));
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

	public void displayBoard(double[][] board) {
		for(int i=0; i<nX; i++){
			for(int j=0; j<nY; j++){
				double value = board[i][j];
				if (value != -1) {
					if(value == 5)
					{
						JPanel p = ((JPanel)boardPanel.getComponent(i*nY+j));
						p.setBackground(Color.black);
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
		}
		boardPanel.invalidate();
	}
}