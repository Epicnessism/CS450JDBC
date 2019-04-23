/**
 * JDBC Controller
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */
package cs450JDBC;

import java.sql.*; //needed to do sql and jdbc related stuff
import java.io.IOException; //probably to throw when errors in db connection/etc stuff

//communicates with the DB and the FXML Layout Controller Classes
public class JDBC_Controller {
	
	public boolean check_Manager_SSN(String managerSSN) {
		String DRIVERNAME = "oracle.jdbc.driver.OracleDriver"; //oracle jdbc library driver thingy?
		String dbUrl    = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";  //url for that specific db
        String username = "twang9";
        String password = "eeptip";
        
        //first attempt to get the drivers loaded
        try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
        //master try/catch block that creates the connection to the db
		try {
//			connection = DriverManager.getConnection(dbUrl, username, password);
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			//Task 1: Write a program segment that retrieves the employees who work in the Research department and print the employee’s last name and their SSN.
	        try {
	        	Statement statement = connection.createStatement();
	        	String sql = "select fname, lname, case when ssn = ";
	        	sql += managerSSN;
	        	sql += " then 1 else 0 end as is_Manager from employee e join department d on e.ssn = d.mgrssn";
	        	System.out.println(sql);
	        	ResultSet queryResult = statement.executeQuery(sql); //actually sends the sql statement to the db
	    		
	        	while(queryResult.next()) { //while there is still a result left, iterate through the results
		            if(queryResult.getBoolean("is_Manager")) { //if successful match found, should return 1=true
		            	System.out.println("Successful manager ssn found!");
		            	String Fname = queryResult.getString("Fname");
		            	String Lname = queryResult.getString("Lname");
		            	System.out.println("Welcome, " + Fname + " " + Lname + ".");
		            	return true;
		            }
	        	}
	        	//TODO add conditional message for if not found
	        	
	        	queryResult.close();
	        	statement.close();
	        }
	        catch (SQLException e) {
	        	System.out.println("Error executing query: ");
	            e.printStackTrace();
	        }
	        
	        
	        connection.close(); //only 1 connection close statement needed
		}
		catch(Exception e) {
			System.out.println("DB connection not made.");
		}
		return false;
	}
}
