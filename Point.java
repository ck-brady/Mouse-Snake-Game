public class Point {

   // Instance variables
    private int x;
	private int y;

    /**Constructor 
     * The parameters x and y are the coordinates
     */
    public Point(int x, int y){
		this.x = x;
		this.y = y;
    }

    // Getter method, return the value of instance variable x
    public int getX(){
		return x;//REPLACE THIS LINE WITH YOUR CODE 
    }
    
    // Getter method, return the value of instance variable y
    public int getY(){
		return y;//REPLACE THIS LINE WITH YOUR CODE 
    }
    
    // Setter method, sets the values of instance variables x and y
    public void reset(int x, int y){
		this.x = x;
		this.y = y;
	}
 }
