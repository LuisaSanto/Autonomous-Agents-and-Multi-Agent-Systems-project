package fireprevention;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


/**
 * Graphical interface
 * @author Rui Henriques
 */
public class GraphicalInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static JTextField speed, clean, decay;
	static JPanel boardPanel;
	static JButton run, reset, step;
	
	public Board board;
	
	public GraphicalInterface(Board board) {
		this.board = board;
		setTitle("FirePrevention");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(670, 580);
		add(createButtonPanel(board));
		boardPanel = new JPanel();
		boardPanel.setSize(new Dimension(630,500));
		boardPanel.setLocation(new Point(20,60));
		boardPanel.setLayout(new GridLayout(board.nX,board.nY));
		for(int i=0; i<board.nX; i++)
			for(int j=0; j<board.nY; j++)
				boardPanel.add(new JPanel());
		displayBoard(board);
		board.GUI = this;
		add(boardPanel);
		displayAgents(board);
	}

	public void displayBoard(Board board) {
		for(int i=0; i<board.nX; i++){
			for(int j=0; j<board.nY; j++){
				double value = board.board[i][j];
				if(value == 5)
				{
					JPanel p = ((JPanel)boardPanel.getComponent(i*board.nY+j));
					p.setBackground(Color.black);
					p.setBorder(BorderFactory.createLineBorder(Color.white));
				}
				else 
				{
					int R = (int) (255*value)/5;
					int G = (int) (255*(5-value))/5; 
					JPanel p = ((JPanel)boardPanel.getComponent(i*board.nY+j));
					p.setBackground(new Color(R,G,0));
					p.setBorder(BorderFactory.createLineBorder(Color.white));
				}
			}
		}
		boardPanel.invalidate();
	}
	
	public void removeAgents(Board board) {
		for(Agent agent : board.UAVs){
			JPanel p = ((JPanel)boardPanel.getComponent(agent.position.x+agent.position.y*board.nX));
			p.setBorder(BorderFactory.createLineBorder(Color.white));			
		}
		boardPanel.invalidate();
	}

	public void displayAgents(Board board) {
		for(Agent agent : board.UAVs){
			JPanel p = ((JPanel)boardPanel.getComponent(agent.position.x+agent.position.y*board.nX));
			if(agent.isProtecting())
				p.setBorder(BorderFactory.createLineBorder(Color.orange,3));	
			else
				p.setBorder(BorderFactory.createLineBorder(Color.blue,3));			
		}
		boardPanel.invalidate();
	}

	private Component createButtonPanel(Board board) {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(650,50));
		panel.setLocation(new Point(10,10));
		
		clean = new JTextField(" Steps to clean ");
		clean.setMargin(new Insets(5,5,5,5));
		panel.add(clean);
		
		decay = new JTextField(" Speed for decay ");
		decay.setMargin(new Insets(5,5,5,5));
		panel.add(decay);
		
		step = new JButton("Step");
		panel.add(step);
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")) 
				{
					int steps;
					double decayTime;
					try
					{
						decayTime = Double.valueOf(decay.getText());
					}
					catch(Exception e)
					{
						decayTime = 1.001; // Default values for decay Time
					}
					try
					{
						steps = Integer.valueOf(clean.getText());
					}
					catch(Exception e)
					{
						steps = 5; // Default value of steps that take to cleanse a spot
					}
					board.step(decayTime, steps);
				}
				else board.stop();
			}
		});
		reset = new JButton("Reset");
		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.reset();
			}
		});
		run = new JButton("Run");
		panel.add(run);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(run.getText().equals("Run")){
					int time = -1;
					try {
						time = Integer.valueOf(speed.getText());
					} catch(Exception e){
						JTextPane output = new JTextPane();
						output.setText("Please insert an integer value to set the time per step\nValue inserted = "+speed.getText());
						JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.PLAIN_MESSAGE);
					}
					if(time>0){
						int steps;
						double decayTime;
						try
						{
							decayTime = Double.valueOf(decay.getText());
						}
						catch(Exception e)
						{
							decayTime = 1.001; // Default values for decay Time
						}
						try
						{
							steps = Integer.valueOf(clean.getText());
						}
						catch(Exception e)
						{
							steps = 5; // Default value of steps that take to cleanse a spot
						}
						board.run(time, decayTime, steps);
	 					run.setText("Stop");						
					}
 				} else {
					board.stop();
 					run.setText("Run");
 				}
			}
		});
		speed = new JTextField(" Time per step in [1,100] ");
		speed.setMargin(new Insets(5,5,5,5));
		panel.add(speed);
		
		return panel;
	}
}
