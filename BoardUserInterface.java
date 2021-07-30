import javax.swing.*;
import java.awt.*;

public class BoardUserInterface extends JPanel {

    // Instance vairables.
	private GameLogic gameLogic;
	private GameState gameState;
	private int boardSize;
	private Cube[][] cubes;
	private Dimension panelSize;
	private int iconPixels = 40;
	private int spacing = 5;
	
	// Constructor.
	public BoardUserInterface(GameState gameState, GameLogic gameLogic){
		this.gameLogic = gameLogic;
		this.gameState = gameState;
		this.boardSize = gameState.getSize();
		this.cubes = new Cube[boardSize][boardSize];
		this.setLayout(new GridLayout(boardSize,1));  	
		this.panelSize = new Dimension((boardSize*iconPixels + (boardSize+1)*spacing + iconPixels/2),iconPixels+spacing);   // formula to dynamically size frame based on board size, icon size, and spacing of icons
		this.setBackground(Color.white);
		this.setVisible(true);  
		
		// Creates an nxn grid of panels containing Cube objects (JButtons). Cube types are taken from GameState 2D array.
		initializeCubes();
	}
	
	private void initializeCubes(){
		for(int i = 0; i < boardSize; i++){
			JPanel temp = new JPanel();
			temp.setPreferredSize(panelSize);
			temp.setLayout(new FlowLayout());
			temp.setBackground(Color.white);
			for(int j = 0; j < boardSize; j++){
				cubes[i][j] = new Cube(i,j,gameState.getCurrentStatus(i,j));  // Cube constructor 
				cubes[i][j].addActionListener(gameLogic);   // add listener to each Cube button.
				temp.add(cubes[i][j]);
			}
			if(i%2 == 0){    // alternate row indentation
				temp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			} else { 
				temp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
			}
			this.add(temp); 
		}		
	}
	
    // Updates the status of the board's cubes instances following the game state, calls setType() from the class Cube, as needed.
    public void update(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				cubes[i][j].setType(gameState.getCurrentStatus(i,j));
			}
		}			
    }	

	public int getBoardSize(){
		return boardSize;
	}
	
}
