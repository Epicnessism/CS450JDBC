/**
 * Tasks 1 & 2
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */

package cs450JDBC; //name of my project package

import java.sql.*; //needed to do sql and jdbc related stuff
import java.io.IOException; //probably to throw when errors in db connection/etc stuff

// use this link to solve 'No Suitable Driver Found for <dbUrl>' issues:
// https://stackoverflow.com/questions/14941936/unable-to-connect-to-oracle-database-using-jdbc-thin-drivers

public class MainJDBC {
	
	public static void main(String[] args) throws SQLException, IOException {
		String DRIVERNAME = "oracle.jdbc.driver.OracleDriver"; //oracle jdbc library driver thingy?
		String dbUrl    = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";  //url for that specific db
        String username = "twang9";
        String password = "eeptip";
		
//        Connection connection = null;
        
		try {
			Class.forName(DRIVERNAME); //first attempt to get the drivers loaded
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		try { //master try/catch block that creates the connection to the db
//			connection = DriverManager.getConnection(dbUrl, username, password);
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			//Task 1: Write a program segment that retrieves the employees who work in the Research department and print the employee’s last name and their SSN.
	        try {
	        	Statement statement = connection.createStatement();
	        	String sql = "select Lname, ssn from Employee where dno = (select Dnumber from Department where Dname = 'Research')";
	    		ResultSet queryResult = statement.executeQuery(sql); //actually sends the sql statement to the db
	    		
	        	while(queryResult.next()) { //while there is still a result left, iterate through the results
		            String Lname = queryResult.getString("Lname");
		            long ssn  = queryResult.getLong("SSN");
		            
		            System.out.println("Lname: " + Lname + "  SSN: " + ssn);
	        	}
	        	queryResult.close();
	        	statement.close();
	        }
	        catch (SQLException e) {
	        	System.out.println("Error executing Task 1: ");
	            e.printStackTrace();
	        }
	        
	        //Task 2:
	        try {
	        	Statement statement = connection.createStatement();
	        	String sql = "select lname, ssn, hours from employee e join works_on w on e.ssn = w.essn join project p on w.pno = p.pnumber join dept_locations l on e.dno = l.dnumber where p.pname = 'ProductZ' and l.dlocation = 'Houston'";
	    		ResultSet queryResult = statement.executeQuery(sql); //actually sends the sql statement to the db
	    		
	        	while(queryResult.next()) { //while there is still a result left, iterate through the results
		            String Lname = queryResult.getString("Lname");
		            long ssn  = queryResult.getLong("SSN");
		            int hours_worked = queryResult.getInt("hours");
		            
		            System.out.println("Lname: " + Lname + "  SSN: " + ssn + "  Hours Worked: " + hours_worked);
	        	}
	        	queryResult.close();
	        	statement.close();
	        }
	        catch(SQLException e) {
	        	System.out.println("Error executing Task 2: ");
	            e.printStackTrace();
	        }
	        
	        
	        connection.close(); //only 1 connection close statement needed   
		}
		catch(Exception e) {
			System.out.println("DB connection not made.");
		}
		
		
    }

}
