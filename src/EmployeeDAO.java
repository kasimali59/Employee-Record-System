import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EmployeeDAO {
	public static int MainFormID;
	public static int NumEmployee, NumOfEmployee;
	public static int id;
	public static String name; 
	public static String gender;
	public static String dob; 
	public static String address; 
	public static String postcode; 
	public static String NIN;
	public static String salary; 
	public static String startDate;
	public static String jobTitle; 
	public static String email;
	
	//Code to create the database connection
	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			String dbURL = "jdbc:sqlite:assignmentdb.sqlite";
			dbConnection = DriverManager.getConnection(dbURL);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	
	//Code to close the database connection
	public static void closeConnection() throws SQLException{
		Connection dbConnection = null;
		dbConnection.close();
	}
	
	//Code that selects a record in the database by a particular ID
	public static void selectEmployeeByID() throws SQLException{
		Connection dbConnection = null;
		Statement statement = null;
		ArrayList<Employee> allEmployee = new ArrayList<Employee>();
		Employee temp = null;
		String selectQuery = "SELECT * from employees where ID=" + MainFormID + ";";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			byte[] image = null;
			while (rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String dob = rs.getString("dob");
				String address = rs.getString("address");
				String postcode = rs.getString("postcode");
				String NIN = rs.getString("NIN");
				String salary = rs.getString("salary");
				String startDate = rs.getString("startDate");
				String jobTitle = rs.getString("jobTitle");
				String email = rs.getString("email");
				image = rs.getBytes("picture");
				
				MainForm.tfText0.setText(name);
				if(gender.equals("M")){
					MainForm.rbMaleRButton.setSelected(true);
				} else if (gender.equals("F")) {
					MainForm.rbFemaleRButton.setSelected(true);
				}
				
				String[] DOBparts = dob.split("-");
				String DOBpart1 = DOBparts[0]; 
				String DOBpart2 = DOBparts[1]; 
				String DOBpart3 = DOBparts[2];
				
				String[] startDateparts = startDate.split("-");
				String startDatepart1 = startDateparts[0]; 
				String startDatepart2 = startDateparts[1]; 
				String startDatepart3 = startDateparts[2];
				
				MainForm.cmbDateCombo.setSelectedItem(DOBpart1);
				MainForm.cmbMonthCombo.setSelectedItem(DOBpart2);
				MainForm.cmbYearCombo.setSelectedItem(DOBpart3);
				
				MainForm.cmbDateCombo1.setSelectedItem(startDatepart1);
				MainForm.cmbMonthCombo1.setSelectedItem(startDatepart2);
				MainForm.cmbYearCombo1.setSelectedItem(startDatepart3);
				
				MainForm.tfText1.setText(salary);
				MainForm.tfText2.setText(NIN);
				MainForm.tfText3.setText(email);
				MainForm.tfText4.setText(jobTitle);
				MainForm.tfText5.setText(address);
				MainForm.tfText6.setText(postcode);
				MainForm.lbIDLabel.setText("ID: " + id );
				
				Image img = Toolkit.getDefaultToolkit().createImage(image);
				Image icon =(new ImageIcon(img).getImage().getScaledInstance(145, 145, Image.SCALE_DEFAULT));
				MainForm.lbPhoto.setIcon(new ImageIcon(icon));
				
				
			}
			rs.close();
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Code used in the Console Controller for Search by ID
	public static void selectEmployeeByIDConsole() throws SQLException{
		Connection dbConnection = null;
		Statement statement = null;
		String selectQuery = "SELECT * from employees where ID=" + MainFormID + ";";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(selectQuery);
			while (rs.next()){
				System.out.println(rs.getString("ID")+ "  "+ rs.getString("Name")+ "  "+ rs.getString("Gender").charAt(0)
						+ "  "+ rs.getString("DOB")+ "  "+ rs.getString("Address")+ "  "+ rs.getString("Postcode")+ "  "+ rs.getString("NIN")
						+ "  "+ rs.getString("JobTitle")+ "  "+ rs.getString("StartDate")+ "  "+ rs.getString("Salary")+ "  "+ rs.getString("Email"));
						
			}
			rs.close();
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Code that prints all Employees in the database
	public static ArrayList<Employee> selectAllEmployees() throws SQLException{
		Connection dbConnection = null;
		Statement statement  = null;
		ArrayList<Employee> allEmployee = new ArrayList<Employee>();
		Employee temp = null;
		String showQuery = "SELECT * FROM employees;";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery(showQuery);
			while (rs.next()){
				System.out.println(rs.getString("ID")+ "  "+ rs.getString("Name")+ "  "+ rs.getString("Gender").charAt(0)
				+ "  "+ rs.getString("DOB")+ "  "+ rs.getString("Address")+ "  "+ rs.getString("Postcode")+ "  "+ rs.getString("NIN")
				+ "  "+ rs.getString("JobTitle")+ "  "+ rs.getString("StartDate")+ "  "+ rs.getString("Salary")+ "  "+ rs.getString("Email"));
				
				String id = rs.getString("ID");
				String name = rs.getString("Name");
				char gender = rs.getString("Gender").charAt(0);
				String dob = rs.getString("Dob");
				String address = rs.getString("Address");
				String postcode = rs.getString("Postcode");
				String NIN = rs.getString("NIN");
				String salary = rs.getString("Salary");
				String startDate = rs.getString("startDate");
				String jobTitle = rs.getString("jobTitle");
				String email = rs.getString("Email");
				
				temp = new Employee(id, name, gender, dob, address, postcode, NIN, salary, startDate, jobTitle, email);
				allEmployee.add(temp);
			}
			rs.close();
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Operation done successfully");
		return allEmployee;
	}

	//Code that figures out the Last ID
	public static void selectLastID(){
		Connection dbConnection = null;
		Statement statement  = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM employees ORDER BY ID DESC LIMIT 1;");
			while (rs.next()){
				int id = rs.getInt("id");
				NumEmployee = id;
				MainFormID = id;
			}
			rs.close();
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Code to insert a new record in the database
	public static void insertEmployee(){
		Connection dbConnection = null;
		PreparedStatement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement("INSERT INTO employees (id, name, gender, dob, address, postcode, NIN, salary, startDate, jobTitle, email, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			statement.setInt(1,  MainForm.id);
			statement.setString(2, MainForm.textName);
			statement.setString(3, MainForm.textGenderButton);
			statement.setString(4, MainForm.textDate0 + "-" + MainForm.textMonth0 + "-" + MainForm.textYear0 );
			statement.setString(5, MainForm.textAddress);
			statement.setString(6, MainForm.textPostcode);
			statement.setString(7, MainForm.textNIN);
			statement.setString(8, MainForm.textSalary);
			statement.setString(9, MainForm.textDate1 + "-" + MainForm.textMonth1 + "-" + MainForm.textYear1);
			statement.setString(10, MainForm.textJobTitle);
			statement.setString(11, MainForm.textEmail);
			statement.setBytes(12, MainForm.pic);
			statement.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Records successfully created");
		System.out.println(MainForm.textName);
	}
	
	//Code to update a record already in the database
	public static void updateEmployee(){
		Connection dbConnection = null;
		Statement statement = null;
		PreparedStatement sql = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			String picquery = "UPDATE employees set picture = ?"+"WHERE ID = '"+ MainForm.id +"'" ;
			sql = dbConnection.prepareStatement(picquery);
			sql.setBytes(1, MainForm.pic);
			sql.execute();
			String updatequery = "UPDATE employees set name='"+ MainForm.textName +"', gender= '" + MainForm.textGenderButton + "', dob= '" + MainForm.textDate0 + "-" + MainForm.textMonth0 + "-" + MainForm.textYear0 + "', address= '" + MainForm.textAddress + "', postcode= '" + MainForm.textPostcode + "', NIN= '" + MainForm.textNIN + "', salary= '" + MainForm.textSalary + "', startDate= '" + MainForm.textDate1 + "-" + MainForm.textMonth1 + "-" + MainForm.textYear1 + "', jobTitle= '"+ MainForm.textJobTitle +"', email= '"+ MainForm.textEmail +"' where ID='"+ MainForm.id +"'" ;
			statement.executeUpdate(updatequery);
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Records successfully updated");
		selectLastID();
	}
	
	//Code that deletes a record in the database(This is done by ID)
	public static void deleteEmployeeByID() {
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			String deletequery = "DELETE from employees where ID=" + MainFormID + ";";
			statement.executeUpdate(deletequery);
			statement.close();
			dbConnection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Delete operation successfully done");
	}
//	
//	public static void insertEmployeeAtID() {
//		Connection dbConnection = null;
//		Statement statement = null;
//		int ID = 5;
//		try {
//			dbConnection = getDBConnection();
//			statement = dbConnection.createStatement();
//			String addquery = "INSERT INTO employees (id, name, gender, dob, address, postcode, NIN, salary, startDate, jobTitle, email)" + "VALUES(" + ID + ", 'Zara Javed', 'F', '19/02/1994', '171 Millhouses Lane', 'S7 2HD', 'DBA12F25AN', '25000', '11/06/2015', 'Pharmacist', 'z.j02@gmail.com' )";
//			statement.executeUpdate(addquery);
//			statement.close();
//			dbConnection.close();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println("Records successfully created");
//	}
}
	
