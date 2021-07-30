import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CubesGame{
	public static void main (String[] args){
		int size = 0;
		if (args.length==1){
			size=Integer.parseInt(args[0]);
			if(size<4 || size>20){
				System.out.println ("Invalid argument, the size needs to be between 4 and 20");
			}else{
				GameLogic game = new GameLogic(size);
				game.start();
			}
		}else{
			System.out.println ("Invalid argument, please enter a positive number. Try again.");
		}

	}
}