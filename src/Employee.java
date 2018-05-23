import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Employee extends Person {

	private String id = null;
	private String salary;
	private String startDate;
	private String title;
	private String email;
	
	public Employee(String id, String name, char gender, String NIN, String dob, String address, String postcode, String salary, String startDate, String email, String title) {
		// TODO Auto-generated constructor stub
		super(name, gender, NIN, dob, address, postcode);
		this.id=id;
		this.salary=salary;
		this.startDate=startDate;
		this.email=email;
		this.title=title;
	}

	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getSalary(){
		return salary;
	}
	
	public void setSalary(String salary){
		this.salary = salary;
	}
	
	public String getStartDate(){
		return startDate;
	}
	
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	
	public String toString(){
		return "Employee ID: " + getId() + "Salary: " + getSalary() + "Start Date: " + getStartDate() + "Title: " + getTitle() + "Email: " + getEmail();
	}
}
