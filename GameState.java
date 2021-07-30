import java.util.Random;

public class GameState {

	// Static final variables public
	public static final int FREE_CUBE = 0;
	public static final int SELECTED = 1;
	public static final int RED_CUBE = 2;
	public static final int MAX_SELECTED = 5;
	
	// Non-final variables private
	private int boardSize;
	private Point redCube;
	private int[][] cubeStates;

    /**
     * Constructor 
	 * initializes the state to the size of board
     *  The parameter size is the size of the board
     */
    public GameState(int size) {
		boardSize = size;
		cubeStates = new int[size][size];
		initializeSnakes();
		initializeMouse();
    }

    /**
     * returns the current status (FREE_CUBE, SELECTED or RED_CUBE) of a given cube in the game
     * 
     * i is the x coordinate of the cube
     * j is the y coordinate of the cube
     * return the status of the cube at location (i,j)
     */   
    public int getCurrentStatus(int i, int j){
		int currentStatus = -1;
		if((i < boardSize) && (j < boardSize) && (i >= 0) && (j >=0)){
			currentStatus = cubeStates[i][j];
		}
		return currentStatus;
    }

    /**
     * Sets the status of the cube at coordinate (i,j) to SELECTED
     * i is the x coordinate of the cube
     * j is the y coordinate of the cube
     */   
    public void select(int i, int j){
		if(i < boardSize && j < boardSize && i >= 0 && j >=0){
			cubeStates[i][j] = SELECTED;
		}
    }

    /**
     * Puts the red cube at coordinate (i,j). Clears the previous location 
     * of the red cube.
     *
      * i is the x coordinate of the cube
     * j is the y coordinate of the cube
     */   
    public void setCube(int i, int j){
		if(i < boardSize && j < boardSize && i >= 0 && j >=0){
			cubeStates[redCube.getX()][redCube.getY()] = FREE_CUBE;
			cubeStates[i][j] = RED_CUBE;
			redCube.reset(i, j);
		}
	}

	// Called in constructor to place 1-5 initial snakes 
	private void initializeSnakes(){
		int snakeCount = 0;
		double random = 0;
		while(snakeCount == 0){
			for(int i = 0; i < boardSize; i++){              
				for(int j = 0; j < boardSize; j++){
					random = Math.random();
					if(random < 0.05 && snakeCount < MAX_SELECTED){  // 5% chance of assigning a snake (10% assigned them all near the top)
						cubeStates[i][j] = SELECTED;
						snakeCount++;
					} else {
						cubeStates[i][j] = FREE_CUBE;
					}
				}
			}
		}
	}

	// Called in constructor to place initial position of mouse.
	private void initializeMouse(){
		boolean mouseInitialized = false;
		int center;
		double random;
		redCube = new Point(0,0);
		while(mouseInitialized == false){
			if(boardSize%2 == 0){         // For an even board, randomly place mouse within four center cubes
				center = boardSize/2;
				for(int i = (center-1); i <= center; i++){              
					for(int j = (center-1); j <= center; j++){
						random = Math.random();
						if(random < 0.25 && mouseInitialized == false && cubeStates[i][j] == FREE_CUBE){
							cubeStates[i][j] = RED_CUBE;
							redCube.reset(i, j);
							mouseInitialized = true;
							break;
						}
					}
				}
			} else {                        // For an odd board, randomly place mouse within nine center cubes
				center = (boardSize-1)/2;
				for(int i = (center-1); i <= (center+1); i++){              
					for(int j = (center-1); j <= (center+1); j++){
						random = Math.random();
						if(random < 0.12 && mouseInitialized == false && cubeStates[i][j] == FREE_CUBE){
							setCube(i,j);
							mouseInitialized = true;
							break;
						}
					}
				}
			}
		}
	}
	
    // Getter method for the current position point of mouse.
	public Point getMousePoint(){
		return redCube;
	}

	public int getSize(){
		return boardSize;
	}
	
}
