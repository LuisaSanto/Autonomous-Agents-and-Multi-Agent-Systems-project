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
import javax.swing.JLabel;
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
	
	static JTextField speed, clean, decay, maxSteps;
	static JPanel boardPanel, statsPanel;
	static JButton run, reset, step;
	static JLabel blacks, whites, steps;
	
	public Board board;
	
	public GraphicalInterface(Board board) {
		this.board = board;
		setTitle("FirePrevention");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(670, 690);
		add(createButtonPanel(board));
		JLabel label = new JLabel("Number of failed nodes : ");
		blacks = new JLabel("0");
		JLabel label1 = new JLabel("Number of safe nodes : ");
		whites = new JLabel("0");
		JLabel label2 = new JLabel("Number of steps : ");
		steps = new JLabel("0");
		boardPanel = new JPanel();
		boardPanel.setSize(new Dimension(630,500));
		boardPanel.setLocation(new Point(20,90));
		boardPanel.setLayout(new GridLayout(board.nX,board.nY));
		for(int i=0; i<board.nX; i++)
			for(int j=0; j<board.nY; j++) {
				boardPanel.add(new JPanel());
			}
		statsPanel = new JPanel();
		statsPanel.setSize(new Dimension(300,80));
		statsPanel.setLocation(new Point(180,600));
		statsPanel.add(label2);
		statsPanel.add(steps);
		statsPanel.add(label);
		statsPanel.add(blacks);
		statsPanel.add(label1);
		statsPanel.add(whites);
		add(boardPanel);
		add(statsPanel);
		displayBoard(board);		
		displayAgents(board);
		board.GUI = this;
	}

	public void displayBoard(Board board) {
		for(int i=0; i<board.nX; i++){
			for(int j=0; j<board.nY; j++){
				double value = board.board[i][j];
				JPanel p = ((JPanel)boardPanel.getComponent(i*board.nY+j));
				if(value == 5 && !p.getBackground().equals(Color.black))
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
				p.setBorder(BorderFactory.createLineBorder(Color.darkGray,3));
			
		}
		boardPanel.invalidate();
	}

	private Component createButtonPanel(Board board) {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(630,80));
		panel.setLocation(new Point(20,10));
		
		maxSteps = new JTextField(" Optional : Max steps");
		maxSteps.setMargin(new Insets(5,5,5,5));
		
		clean = new JTextField(" Optional : Steps to clean ");
		clean.setMargin(new Insets(5,5,5,5));
		
		decay = new JTextField(" Optional : Speed for decay [0,1]");
		decay.setMargin(new Insets(5,5,5,5));

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
						decayTime = .002; // Default values for decay Time
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
						int maxS;
						try
						{
							maxS = Integer.valueOf(maxSteps.getText());
						}
						catch(Exception e)
						{
							maxS = 0; // Default values for decay Time
						}
						try
						{
							decayTime = Double.valueOf(decay.getText());
						}
						catch(Exception e)
						{
							decayTime = .002; // Default values for decay Time
						}
						try
						{
							steps = Integer.valueOf(clean.getText());
						}
						catch(Exception e)
						{
							steps = 5; // Default value of steps that take to cleanse a spot
						}
						board.run(time, decayTime, steps, maxS);
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
		panel.add(clean);
		panel.add(decay);
		panel.add(maxSteps);
		
		return panel;
	}
}
