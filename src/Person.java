import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Person {

	String name;
	char gender;
	String natInscNo;
	String dob;
	String address;
	String postcode;
	
	public Person(String name, char gender, String NIN, String dob, String address, String postcode) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.gender=gender;
		this.natInscNo=NIN;
		this.dob=dob;
		this.address=address;
		this.postcode=postcode;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public char getGender(){
		return gender;
	}
	
	public void setGender(char gender){
		this.gender = gender;
	}
	
	public String getNatInscNo(){
		return natInscNo;
	}
	
	public void setNatInscNo(String natInscNo){
		this.natInscNo = natInscNo;
	}
	
	public String getDob(){
		return dob;
	}
	
	public void setDob(String dob){
		this.dob = dob;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getPostcode(){
		return postcode;
	}
	
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	
	public String toString(){
		return "Name: " + getName() + "Gender: " + getGender() + "National Insurance Number: " + getNatInscNo() + "Date of Birth: " + getDob() + "Address: " + getAddress();
	}
}
