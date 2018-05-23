import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Controller {
// Code for the console controller
	  public static int MainMenu() throws SQLException{
	      //Displays the main menu and handles passing off to next menu. 
	     
	        Scanner scanner = new Scanner (System.in);
	        int selection=0;
	        
	        while(true){   //changed while(1) to a value that didn't complain in netbeans
	            System.out.println("Please Make a selection:"); 
	            System.out.println("[1] View all records saved in the database"); 
	            System.out.println("[2] Search database by ID"); 
	            System.out.println("[3] exit"); 
	            System.out.println("Selection: "); 
	            selection=scanner.nextInt();     
	        
	       switch (selection){
	             
	           case 1:EmployeeDAO.selectAllEmployees();
	                break;
	             
	        
	           case 2:System.out.println("Input the ID you want to search for:");
	           Scanner scan = new Scanner(System.in);
	           int num = scan.nextInt();
	           EmployeeDAO.MainFormID=num;
	           EmployeeDAO.selectEmployeeByIDConsole();
	                break;
	             
	        
	           case 3:System.out.println("Exit Successful");
	                System.exit(0);
	                        
	           default:System.out.println("Please enter a valid selection.");
	           
	            };
	        
	        }  
	    }
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				try {
					MainMenu();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

}
