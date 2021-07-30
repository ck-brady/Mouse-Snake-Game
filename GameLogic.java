import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;
import java.lang.Math.*;

//This class implements the interface ActionListener so that it is called when the player makes a move. 
//It calculates the next step of the game
//It updates state and userInterface.

public class GameLogic implements ActionListener {

	// Instance variables.
	private int boardSize;
	private GameState gameState;
	private GameUserInterface gameUI;

    public GameLogic(int size) { // The parameter size is the size of the board on which the game will be played
		boardSize = size;
    }
  
	// Starts the game.
    public void start(){
		gameState = new GameState(boardSize);
		gameUI = new GameUserInterface(gameState, this, "MOUSE GAME: Catch the mouse!");
    }

	// Resets the game.
    public void reset(){ 
		gameUI.dispose();   
		start();
    }
	
	// Method to test if a Cube is neighbouring a snake (status = SELECTED).
	private boolean hasSnakeNeighbour(int i, int j){ 
		boolean hasSnakeNeighbour = false;
		if(i%2 == 0){
			if(tryStatus(i-1,j) || tryStatus(i-1, j+1) || tryStatus(i, j-1) || tryStatus(i, j+1) || tryStatus(i+1, j) || tryStatus(i+1, j+1)){   // For even row, try all 6 neighbouring Cube coordinates (different from odd row)
				hasSnakeNeighbour = true;
			}
		} else {
			if(tryStatus(i-1,j-1) || tryStatus(i-1,j) || tryStatus(i,j-1) || tryStatus(i,j+1) || tryStatus(i+1,j-1) || tryStatus(i+1,j)){        // For odd row, try all 6 neighbouring Cube coordinates
				hasSnakeNeighbour = true;
			}
		}
		return hasSnakeNeighbour;
	}
	
	// Method to try checking for status of neighbouring snakes (catches exception if out of bounds).
	private boolean tryStatus(int i, int j){    
		boolean status = false;
		try{
			if(gameState.getCurrentStatus(i,j) == gameState.SELECTED){
				status = true;
			}
		}
		catch(Exception e){}
		return status;
	}


	// This method moves the mouse with a bit of strategy (not randomly) 
	private void moveMouse(){
		int x = gameState.getMousePoint().getX();
		int y = gameState.getMousePoint().getY();
		double[] move_rankings = {100,100,100,100,100,100};   // ranking score for each move
		double center = boardSize/2;
		Point[] possible_moves = new Point[6];         // all 6 neighbouring cubes (potential moves) 
		possible_moves[0] = new Point(x, y-1);
		possible_moves[1] = new Point(x, y+1);
		if(x%2 == 0){    
			y = y+1;       // correcting for y coordinate varying by one between even and odd rows
		}
		possible_moves[2] = new Point(x-1, y-1);
		possible_moves[3] = new Point(x-1, y);
		possible_moves[4] = new Point(x+1, y-1);
		possible_moves[5] = new Point(x+1, y);
		for(int i = 0; i < 6; i++){
			if(tryStatus(possible_moves[i].getX(), possible_moves[i].getY())){   // if the possible move is a snake, remove from ranking
				possible_moves[i] = null;    
				move_rankings[i] = -100;
			} else {
				if(hasSnakeNeighbour(possible_moves[i].getX(), possible_moves[i].getY())){       // if the possible move is a free cube but has snake neighbours, lower ranking
					move_rankings[i] = 1;
				}
				move_rankings[i] = move_rankings[i] + Math.pow((center)-possible_moves[i].getX(), 2) + Math.pow((center)-possible_moves[i].getY(), 2);      // for all possible moves, increase ranking if closer to edge
			}
		}
		// find highest ranking move
		double max = -100;
		int max_index = -1;
		for(int i = 0; i < 6; i++){
			if(move_rankings[i] > max){
				max = move_rankings[i];
				max_index = i;
			}
		}
		try{ // move mouse using best move coordinates
			gameState.setCube(possible_moves[max_index].getX(), possible_moves[max_index].getY());      
		} // catches the exception if mouse has no where to go (cornered by snakes), then stay in current position
		catch(ArrayIndexOutOfBoundsException e){
			gameState.setCube(gameState.getMousePoint().getX(), gameState.getMousePoint().getY());      
		}
	}


    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gameUI.getReset()){        // case: clicked reset
			reset();
		} else if(e.getSource() == gameUI.getQuit()){  // case: clicked quit
			System.exit(0);
		} else if(e.getSource() instanceof Cube){      
			Cube clicked = (Cube) e.getSource();
			if(hasSnakeNeighbour(clicked.getRow(), clicked.getColumn())){            // case: clicked a cube that has a snake neighbour
				if(clicked.getType() == gameState.FREE_CUBE){            // --- if clicked cube is free, add snake, move mouse
					gameState.select(clicked.getRow(), clicked.getColumn());
					moveMouse();
					gameUI.getBoardUserInterface().update();
					if(gameState.getMousePoint().getX() == 0 || gameState.getMousePoint().getY() == 0 || gameState.getMousePoint().getX() == (boardSize-1) ||  gameState.getMousePoint().getY() == (boardSize-1)){    // is edge point if either x or y is 0 or (boardSize - 1)
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Nice try! Play again. ");    // if above condition is true, then mouse reached an edge (game over)
						reset();						
					}
				} else if(clicked.getType() == gameState.RED_CUBE){     // --- if clicked cube is the mouse, they won
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "You Won!");
					reset();
				}
			}
		}
    }

}

