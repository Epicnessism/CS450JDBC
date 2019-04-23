/**
 * Employee Overview Controller
 * @author Tony Wang
 * CS450 Final Project
 * 4/22/19
 */
package cs450JDBC.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import cs450JDBC.MainApplication;
import cs450JDBC.JDBC_Controller; //main controller that does all the DB calling

public class InsertNewEmployeeOverviewController {
	
	@FXML
	private TextField firstName;
	@FXML
	private TextField middleName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField ssn;
	@FXML
	private TextField birthday;
	@FXML
	private TextField address;
	@FXML
	private TextField sex;
	@FXML
	private TextField salary;
	@FXML
	private TextField superSsn;
	@FXML
	private TextField dno;
	@FXML
	private TextField email;
	
	@FXML
	private Button insertNewEmployee;
	
	private MainApplication MainApplication;
	private JDBC_Controller JDBC_Controller = new JDBC_Controller(); 
	
	/**
	 * Constructor called before the initialize() method
	 */
	public InsertNewEmployeeOverviewController() {
	}
	
	/**
	 * inserts the new employee with the given information
	 * should throw label messages if the required not-null parameters are null
	 */
	@FXML
	private void insert_New_Employee() {
		//TODO TRY CATCH STATEMENTS...
		//do null checks to ensure not-null values in the db are enforced.
		// describe employee; //shows null? and dataType
		if(firstName.getText().isEmpty()) {
			System.out.println("Missing First Name...");
		}
		if(lastName.getText().isEmpty()) {
			System.out.println("Missing Last Name...");
		}
		if(ssn.getText().isEmpty()) {
			System.out.println("Missing Social Security Number...");
		} else {
			//temp ssn to make if statement more readable
			int tempSsn = Integer.parseInt(ssn.getText());
			//if all conditions satisfied, a valid employee can be inserted
			if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty() && !ssn.getText().isEmpty() && tempSsn >= 100000000 && tempSsn < 1000000000 ) {
				System.out.println("Valid information, Inserting into DB...");
				//TODO call JDBC_Controller for db insert call
			}
		}
		
		
		
		
		//defaults to false/failure to insert
		//TODO something to display in GUI that it didn't work
//		return false;
	}
	
//	@FXML
//	
	
	@FXML
	private void initialize() {
		
	}
	
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApplication mainApplication) {
        this.MainApplication = mainApplication;
    }
	
	
}
