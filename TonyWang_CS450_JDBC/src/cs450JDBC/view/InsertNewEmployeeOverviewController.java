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
//import javafx.scene.control.Label;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import cs450JDBC.MainApplication;
import cs450JDBC.JDBC_Controller; //main controller that does all the DB calling

public class InsertNewEmployeeOverviewController {
	
	@FXML
	private Accordion mainAccordion;
	@FXML
	private TitledPane generalInformation;
	@FXML
	private TitledPane projectInformation;
	@FXML
	private TitledPane dependentInformation;
	
	//GeneralInformation Entities
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
	private Button generalInformation_Next;
	@FXML
	private Label generalInformation_errorLabel;
	
	//ProjectInformation Entities
	@FXML
	private TextField essn;
	@FXML
	private TextField pno;
	@FXML
	private TextField hours;
	@FXML
	private Button projectInformation_Next;
	@FXML
	private Button projectInformation_Submit;
	@FXML
	private Label projectInformation_errorLabel;
	
	//DependentInformation Entities
	@FXML
	private TextField d_Essn;
	@FXML
	private TextField d_DependentName;
	@FXML
	private TextField d_Sex;
	@FXML
	private TextField d_Bdate;
	@FXML
	private TextField d_Relationship;
	@FXML
	private Button DependentInformation_Next;
	@FXML
	private Label dependentInformation_errorLabel;
	
	
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
	private void check_New_Employee_Information() {
		//TODO TRY CATCH STATEMENTS...
		/**
		 * Notes for myself:
		 * do null checks to ensure not-null values in the db are enforced.
		 * describe employee; //shows null? and dataType
		 * 
		 * Note to future developers:
		 * The reason I am using independent if statements to check invalidity is so that
		 * the user may see ALL invalid information at once instead of bad websites where
		 * it would only tell you the first error it came across and you end up having to
		 * hit submit 5 or 6 times to find all the errors in your form submission.
		 */
		if(firstName.getText().isEmpty()) {
			System.out.println("Missing First Name...");
			//TODO gui feedback here...
		}
		if(lastName.getText().isEmpty()) {
			System.out.println("Missing Last Name...");
		}
		if(!ssn.getText().isEmpty()) { //if it's not empty
			try {
				int tempSsn = Integer.parseInt(ssn.getText()); //temp ssn for cleaner if statement
				//check for a valid ssn first
				if(!(tempSsn >= 100000000) || !(tempSsn < 1000000000) ) { //must be exactly 9 digits
					throw new Exception("Too many or too few digits..."); //Invalid SSN
				}
				//if all conditions satisfied, a valid employee can be inserted, move to get project info
				if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
					System.out.println("Valid information, inserting and moving to the Projects Pane");
					//if get here, everything's good to go
					JDBC_Controller.insert_New_Employee(firstName.getText(), middleName.getText(), lastName.getText(), ssn.getText(), birthday.getText(), address.getText(), sex.getText(), salary.getText(), superSsn.getText(), dno.getText(), email.getText());
					
					mainAccordion.setExpandedPane(projectInformation);
				}
			} catch(Exception e) {
				if(e.getMessage().isEmpty()) {
					System.out.println("Invalid SSN...");
				} else {
					System.out.println(e.getMessage()); //correct way of handling different custom exceptions
				}
			}
			
		} else { //otherwise it's empty
			System.out.println("Missing Social Security Number...");
		}
		//TODO do datatype checking for Bdate and other values
		//defaults to false/failure to insert
		//TODO something to display in GUI that it didn't work
//		return false;
	}
	
	/**
	 * 
	 */
	@FXML
	private void check_Project_Information() {
		if(essn.getText().isEmpty()) {
			System.out.println("Missing ESSN...");
		}
		if(pno.getText().isEmpty()) {
			System.out.println("Missing Pno...");
		}
		if(!hours.getText().isEmpty()) {
//			int hours = Integer.parseInt(this.hours.getText()); //weird line of code, but cool
			try {
				int tempHours = Integer.parseInt(hours.getText());
				if(tempHours > 9999) {
//					System.out.println("Too many hours...");
					throw new Exception("Exceeds type limit set by the database of 4 digits.");
				}
				//get sum hours of employee from db and subtract from 40
				int existingHours = JDBC_Controller.get_Works_On_Hours(essn.getText());
				if(existingHours == -1) {
					throw new Exception("Error?"); //TODO fix this later
				} else if(existingHours < 40 && (existingHours + tempHours <= 40)) {
					if(!essn.getText().isEmpty() && !pno.getText().isEmpty()) {
						//if get here, good to go, call db_insert_project
						JDBC_Controller.insert_Employee_WorksOn_Project(essn.getText(), Integer.parseInt(pno.getText()), tempHours );
						System.out.println("got after the insert");
					}
				} else {
					System.out.println("Too many hours for this employee...");
				}
			} catch(Exception e) {
				if(e.getMessage().isEmpty()) {
					System.out.println("Invalid hours value...");
				} else {
					System.out.println(e.getMessage());
				}
			}
		} else {
			System.out.println("No Value for hours inputted...");
		}
	}
	
	/**
	 * comes with startup of the code. stuff that should be upon loading.
	 */
	@FXML
	private void initialize() {
		mainAccordion.setExpandedPane(generalInformation); //sets the general information pane to expanded upon loading in
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
