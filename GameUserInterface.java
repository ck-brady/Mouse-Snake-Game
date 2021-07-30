import javax.swing.*;
import java.awt.*;

 /*the class GameUserInterface provides the user interface of the Game. It extends
 JFrame and lays out an instance of BoardUserInterface and  two instances of JButton: one to reset and the second the quit the game at any time.*/

public class GameUserInterface extends JFrame {

	// Instance variables.
	private GameState gameState;
	private GameLogic gameLogic;
	private BoardUserInterface boardUI;
	private int boardSize;
	private int iconPixels = 40;
	private int spacing = 5;
	private JButton reset;
	private JButton quit;
	private JPanel buttonPanel;
	
    /* Constructor 
	 * initializes the board
     * takes as parameters the state of the game and the gameLogic */
	public GameUserInterface(GameState gameState, GameLogic gameLogic, String title){
		super(title);
		
		// initialize instance variables
		this.gameState = gameState;		
		this.gameLogic = gameLogic;
		this.boardSize = gameState.getSize();
		
		// initialize boardUI
		this.boardUI = new BoardUserInterface(gameState, gameLogic);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((boardSize*iconPixels + (boardSize+1)*spacing + iconPixels*2),boardSize*(iconPixels+(spacing*2)) + iconPixels*3); // formula to dynamically size frame based on board size, icon size, and spacing of icons
		this.setLayout(new FlowLayout());  
		this.getContentPane().setBackground(Color.white);
		this.add(boardUI); 
		
		// initialize buttonPanel
		initializeButtonPanel();
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private void initializeButtonPanel(){
		reset = new JButton("Reset");
		reset.setBackground(Color.white);
		reset.addActionListener(gameLogic);
		
		quit = new JButton("Quit");
		quit.setBackground(Color.white);
		quit.addActionListener(gameLogic);
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		buttonPanel.add(reset);
		buttonPanel.add(quit);
	}
	
	// Getter methods. 
	public JButton getReset(){
		return reset;
	}
	
	public JButton getQuit(){
		return quit;
	}

	public BoardUserInterface getBoardUserInterface(){
		return boardUI;
	}

}
