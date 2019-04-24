/**
 * JDBC Controller
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */
package cs450JDBC;

import java.sql.*; //needed to do sql and jdbc related stuff
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException; //probably to throw when errors in db connection/etc stuff
//import java.util.Date;
//import java.sql.Date;

//communicates with the DB and the FXML Layout Controller Classes
public class JDBC_Controller {
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver"; //oracle jdbc library driver thingy?
	private final String dbUrl    = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";  //url for that specific db
    private final String username = "twang9";
    private final String password = "eeptip";
    
	public boolean check_Manager_SSN(String managerSSN) {
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
			
			//Task 1: Write a program segment that retrieves the employees who work in the Research department and print the employee�s last name and their SSN.
	        try {
	        	Statement statement = connection.createStatement();
	        	String sql = "select fname, lname, case when ssn = ";
	        	sql += managerSSN;
	        	sql += " then 1 else 0 end as is_Manager from employee e join department d on e.ssn = d.mgrssn";
//	        	System.out.println(sql);
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
//	            e.printStackTrace();
	        }
	        
	        
	        connection.close(); //only 1 connection close statement needed
		}
		catch(Exception e) {
			System.out.println("DB connection not made.");
		}
		return false;
	}
	
	public boolean insert_New_Employee(String firstName, 
										String middleName, 
										String lastName, 
										String ssn, 
										String birthday, 
										String address, 
										String sex, 
										String salary, 
										String superSsn, 
										String dno, 
										String email) throws SQLException, IOException, ParseException {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			//Parse date and any other values
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date javaBDate = sdf.parse(birthday);
			java.sql.Date sqlBDate = new java.sql.Date(javaBDate.getTime());
			int tempSalary = Integer.parseInt(salary);
			int tempDno = Integer.parseInt(dno);
			
			PreparedStatement statement; //maybe nextTime, I don't know how to use this atm
			String query = "insert into employee (Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email)"
					+ "Values (?,?,?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.clearParameters();
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, middleName);
			statement.setString(4, ssn);
			statement.setDate(5, sqlBDate);
			statement.setString(6, address);
			statement.setString(7, sex);
			statement.setInt(8, tempSalary);
			statement.setString(9, superSsn);
			statement.setInt(10, tempDno);
			statement.setString(11, email);
			int attempt = statement.executeUpdate();
			if(attempt == 1) {
				System.out.println("success insert");
			} else {
				System.out.println("did not insert");
			}
			
//			Statement statement = connection.createStatement();
//        	String sql = "insert into employee (Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email) "
//        			+ "Values ('Tony', 'Wang', 'J', 123456781, '20-JUN-97', 'Chantilly, VA', 'M', 82500, 888665555, 5, 'twang9@gmu.edu')";
//			System.out.println(sql);
			
			
		} catch(SQLException e) {
			System.out.println("Error executing query: ");
			e.printStackTrace();
		}
		
		return false;
	}
	
}
