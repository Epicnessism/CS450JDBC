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
			
			//Task 1: Write a program segment that retrieves the employees who work in the Research department and print the employee’s last name and their SSN.
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
	
	public String insert_New_Employee(String firstName, 
										String middleName, 
										String lastName, 
										String ssn, 
										String birthday, 
										String address, 
										String sex, 
										String salary, 
										String superSsn, 
										String dno, 
										String email) throws SQLException, IOException, ParseException 
	{
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);

			/**
			 * insert into employee (Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email) Values ('Tony', 'Wang', 'J', 123456781, '20-JUN-97', 'Chantilly, VA', 'M', 82500, 888665555, 5, 'twang9@gmu.edu')
				select * from employee e join works_on w on e.ssn = w.essn where ssn = 123456781
				delete from employee where ssn = 123456781
			 */
			PreparedStatement statement; //maybe nextTime, I don't know how to use this atm
			String query = "insert into employee (Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email)"
					+ "Values (?,?,?,?,?,?,?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.clearParameters();
			
			//Parse date and any other values
			if(!birthday.trim().equals("")) {
				System.out.println("birthday not empty");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date javaBDate = sdf.parse(birthday);
				java.sql.Date sqlBDate = new java.sql.Date(javaBDate.getTime());
				statement.setDate(5, sqlBDate);
			} else {
				System.out.println("birthday empty");
				statement.setNull(5, java.sql.Types.DATE);
			}
			
			if(!salary.trim().equals("")) {
				statement.setInt(8, Integer.parseInt(salary));
			} else {
				statement.setNull(8, java.sql.Types.DOUBLE);
			}
			
			if(!dno.trim().equals("")) {
				statement.setInt(10, Integer.parseInt(dno));
			} else {
				statement.setNull(10, java.sql.Types.INTEGER);
			}
			
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, middleName);
			statement.setString(4, ssn);
			
			statement.setString(6, address);
			statement.setString(7, sex);
			
			statement.setString(9, superSsn);
			statement.setString(11, email);
			int attempt = statement.executeUpdate();
			if(attempt == 1) {
				System.out.println("success insert");
				return "Success Employee Insertion";
			} else {
				System.out.println("did not insert");
				return "did not insert";
			}
			
//			Statement statement = connection.createStatement();
//        	String sql = "insert into employee (Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email) "
//        			+ "Values ('Tony', 'Wang', 'J', 123456781, '20-JUN-97', 'Chantilly, VA', 'M', 82500, 888665555, 5, 'twang9@gmu.edu')";
//			System.out.println(sql);
			
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	public String insert_Employee_WorksOn_Project(String essn, int pno, String hours) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			PreparedStatement statement;
			String query = "insert into works_on (essn, pno, hours) values (?,?,?)";
			statement = connection.prepareStatement(query);
			statement.clearParameters();
			
			if(!hours.trim().equals("")) {
				System.out.println("not empty hours");
				statement.setDouble(3, Double.parseDouble(hours));
			} else {
				System.out.println("empty hours");
				statement.setNull(3, java.sql.Types.DOUBLE);
			}
			
			statement.setString(1,essn);
			statement.setInt(2,pno);
			
			
			int attempt = statement.executeUpdate();
			if(attempt == 1) {
				System.out.println("successful project insert");
				return "successful project insert";
			} else {
				System.out.println("did not insert project");
				return "Did not insert project";
			}
        	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
			
		}
		
	}
	
	public int get_Works_On_Hours(String essn) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			PreparedStatement statement;
			String query = "select sum(hours) hours from works_on where essn = ?";
			statement = connection.prepareStatement(query);
			statement.clearParameters();
			
			statement.setString(1,essn);
			
			ResultSet results = statement.executeQuery();
			results.next();
			System.out.println(results.getInt("hours"));
			return results.getInt("hours");
        	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return -1; //failed
	}
	
	public String insert_Employee_Dependent(String essn, String dependentName, String sex, String bDate, String relationship) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			
			
			
			PreparedStatement statement;
			String query = "insert into dependent (essn, dependent_name, sex, bdate, relationship) values (?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.clearParameters();
			
			//Parse date and any other values
			if(!bDate.trim().equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				java.util.Date javaBDate = sdf.parse(bDate);
				java.sql.Date sqlBDate = new java.sql.Date(javaBDate.getTime());
				statement.setDate(4,sqlBDate);
			} else {
				System.out.println("bDate empty");
				statement.setNull(4, java.sql.Types.DATE);
			}
			
			statement.setString(1,essn);
			statement.setString(2,dependentName);
			statement.setString(3,sex);
			statement.setString(5,relationship);
			
			int attempt = statement.executeUpdate();
			if(attempt == 1) {
				System.out.println("successful dependent insert");
				return "successful dependent insert";
			} else {
				System.out.println("did not insert dependent");
				return "Did not insert dependent";
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	
	public ResultSet get_GI_Report(String ssn) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			PreparedStatement statement;
			String GI_query = "select Fname, Lname, Minit, ssn, bdate, address, Sex, Salary, Superssn, dno, email from employee where ssn = ?";
			statement = connection.prepareStatement(GI_query);
			statement.clearParameters();
			statement.setString(1,ssn);
			ResultSet results = statement.executeQuery();
			return results;
        	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null; //failed
		}
		
	}
	
	
	public ResultSet get_PI_Report(String ssn) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			PreparedStatement statement;
			String PI_query = "select essn, pno, hours from works_on where essn = ?";
			
			statement = connection.prepareStatement(PI_query);
			statement.clearParameters();
			statement.setString(1,ssn);
			ResultSet results = statement.executeQuery();
			return results;
        	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet get_DI_Report(String ssn) {
		try {
			Class.forName(DRIVERNAME);
			System.out.println("Driver successfully loaded!"); //visual test to see if drivers loaded
		}
        catch(ClassNotFoundException e) {
        	System.out.println("Driver could not be loaded."); //Visual confirmation if the drivers failed
        }
		
		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			
			PreparedStatement statement;
			String DI_query = "select essn, dependent_name, sex, bdate, relationship from dependent where essn = ?";
			statement = connection.prepareStatement(DI_query);
			statement.clearParameters();
			statement.setString(1,ssn);
			ResultSet results = statement.executeQuery();
			return results;
        	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null; //failed
		}
		
	}
	
}
