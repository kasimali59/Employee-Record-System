import javax.swing.JFrame;

public class ControllerGUI {
	/**
	 * 
	 * @author Kasim Ali
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmployeeDAO.selectLastID();
		//Creates the gui
		MainForm f = new MainForm();
		//Sets the size of the GUI
		f.setSize(1100, 800);
		//Makes the Gui Visible
		f.setVisible(true);
		//Decides what the exit button does
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
