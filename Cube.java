import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;

public class Cube extends JButton {

    // Instance variables.
	private int row;
	private int column;
	private int type;
	private int iconPixels = 40;

    /**
     * Constructor*/
    public Cube(int row, int column, int type) {
		this.row = row;
		this.column = column;
		this.setPreferredSize(new Dimension(iconPixels,iconPixels));
		this.setType(type);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }
	
	 // Set Cube type and corresponding icon.
    public void setType(int type) {
		this.type = type;
		
		if(type == 0){	
			try {
				this.setIcon(new ImageIcon("icons/square-0.png"));
			} 
			catch (Exception e) {
				System.out.println(e);
			}
		} else if(type == 1){	
			try {
				this.setIcon(new ImageIcon("icons/square-1.png"));
			} 
			catch (Exception e) {
				System.out.println(e);
			}
		} else if(type == 2){	
			try {
				this.setIcon(new ImageIcon("icons/square-2.png"));
			} 
			catch (Exception e) {
				System.out.println(e);
			}
		}		
    }

    // Getter methods for attribute row, column and type.
    
	public int getRow() {
		return row;
    }

    public int getColumn() {
		return column;
    }
	
	public int getType(){
		return type;
	}
	
}
