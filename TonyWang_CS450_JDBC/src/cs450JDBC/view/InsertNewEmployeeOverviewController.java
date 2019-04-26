/**
 * Employee Overview Controller
 * @author Tony Wang
 * CS450 Final Project
 * 4/22/19
 */
package cs450JDBC.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.Label;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ChoiceBox;

import cs450JDBC.MainApplication;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	private Button dependentInformation_Submit;
	@FXML
	private Label dependentInformation_errorLabel;
	@FXML 
	private ChoiceBox<String> choiceBox;
	
	
	private MainApplication MainApplication;
	private JDBC_Controller JDBC_Controller = new JDBC_Controller(); 
	
	/**
	 * Constructor called before the initialize() method
	 */
	public InsertNewEmployeeOverviewController() {
	}
	
	//generalInformation on_edits
	@FXML
	private void on_Edit_FirstName() {
		firstName.getStyleClass().remove("error");
	}
	
	@FXML
	private void on_Edit_LastName() {
		lastName.getStyleClass().remove("error");
	}
	
	@FXML
	private void on_Edit_Ssn() {
		ssn.getStyleClass().remove("error");
	}
	
	
	
	//project on_edits
	@FXML
	private void on_Edit_Essn() {
		essn.getStyleClass().remove("error");
	}
	@FXML
	private void on_Edit_Pno() {
		pno.getStyleClass().remove("error");
	}
	@FXML
	private void on_Edit_Hours() {
		hours.getStyleClass().remove("error");
	}
	
	//dependent on_edits
	@FXML
	private void on_Edit_d_Essn() {
		d_Essn.getStyleClass().remove("error");
	}
	@FXML
	private void on_Edit_d_DependentName() {
		d_DependentName.getStyleClass().remove("error");
	}
	
	
	
	//Final Report Anchor Pane
	@FXML
	private AnchorPane reportPane;
	@FXML
	private Label GI_Report;
	@FXML
	private Label PI_Report;
	@FXML
	private Label DI_Report;
	
	/**
	 * inserts the new employee with the given information
	 * should throw label messages if the required not-null parameters are null
	 */
	@FXML
	private void check_New_Employee_Information() {
		generalInformation_errorLabel.setVisible(false); //reset errorLabel in beginning
		generalInformation_errorLabel.setText(""); //reset text
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
			generalInformation_errorLabel.setText(generalInformation_errorLabel.getText() + "\nMissing First Name");
			generalInformation_errorLabel.setVisible(true);
			firstName.getStyleClass().add("error");
		}
		if(lastName.getText().isEmpty()) {
			System.out.println("Missing Last Name...");
			generalInformation_errorLabel.setText(generalInformation_errorLabel.getText() + "\nMissing Last Name");
			generalInformation_errorLabel.setVisible(true);
			lastName.getStyleClass().add("error");
		}
		if(!ssn.getText().isEmpty()) { //if it's not empty
			try {
				int tempSsn = Integer.parseInt(ssn.getText()); //temp ssn for cleaner if statement
				//check for a valid ssn first
				if(!(tempSsn >= 100000000) || !(tempSsn < 1000000000) ) { //must be exactly 9 digits
					
					throw new Exception("\nSSN has too many or too few digits..."); //Invalid SSN
				}
				//if all conditions satisfied, a valid employee can be inserted, move to get project info
				if(!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
//					System.out.println("Valid information, inserting and moving to the Projects Pane");
					//if get here, everything's good to go
					String verify = JDBC_Controller.insert_New_Employee(firstName.getText(), middleName.getText(), 
							lastName.getText(), ssn.getText(), birthday.getText(), address.getText(), 
							sex.getText(), salary.getText(), superSsn.getText(), dno.getText(), email.getText());
					
					if(verify != "Success Employee Insertion") {
						System.out.println(verify);
						throw new Exception(verify);
					} else { //successful
						essn.setText(ssn.getText()); //preset the essn value for better UX
						d_Essn.setText(ssn.getText()); //preset the d_Essn value for better UX
					}
					
					mainAccordion.setExpandedPane(projectInformation); //move to next section
				}
			} catch(Exception e) {
				if(e.getMessage().contains("ORA-00001")) {
					generalInformation_errorLabel.setText(generalInformation_errorLabel.getText() +  "\nUnique Constraint SSN violated");
					generalInformation_errorLabel.setVisible(true);
					ssn.getStyleClass().add("error");
				} else {
					System.out.println(e.getMessage()); //correct way of handling different custom exceptions
					generalInformation_errorLabel.setText(generalInformation_errorLabel.getText() + "\nInvalid SSN");
					generalInformation_errorLabel.setVisible(true);
					ssn.getStyleClass().add("error");
				}
				
			}
			
		} else { //otherwise it's empty
			System.out.println("Missing Social Security Number...");
			generalInformation_errorLabel.setText(generalInformation_errorLabel.getText() +  "\nMissing Social Security Number");
			generalInformation_errorLabel.setVisible(true);
			ssn.getStyleClass().add("error");
		}
		//TODO do datatype checking for Bdate and other values
	}
	
	/**
	 * 
	 */
	@FXML
	private void check_Project_Information() {
		projectInformation_errorLabel.setVisible(false); //reset errorLabel in beginning
		projectInformation_errorLabel.setText(""); //reset text
		try {
			if(essn.getText().isEmpty()) {
				System.out.println("Missing ESSN...");
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nMissing ESSN");
				projectInformation_errorLabel.setVisible(true);
				essn.getStyleClass().add("error");
			} else {
				Long tempEssn = Long.parseLong(essn.getText());
				if(!(tempEssn >= 100000000) || !(tempEssn < 1000000000)) {
					System.out.println("ESSN has too many or too few digits");
					projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nESSN has too many or too few digits");
					projectInformation_errorLabel.setVisible(true);
					essn.getStyleClass().add("error");
					
				}
			}
			if(pno.getText().isEmpty()) {
				System.out.println("Missing Pno...");
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nMissing Pno");
				projectInformation_errorLabel.setVisible(true);
				pno.getStyleClass().add("error");
			}
			double tempHours;
			if(hours.getText().isEmpty()) {
				tempHours = 0;
			} else {
				tempHours = Double.parseDouble(hours.getText());
			}
			if(tempHours > 9999) {
				System.out.println("\nExceeds type limit set by the database of 4 digits.");
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nExceeds type limit set by the database of 4 digits");
				projectInformation_errorLabel.setVisible(true);
				hours.getStyleClass().add("error");
			}
			//get sum hours of employee from db and subtract from 40
			int existingHours = JDBC_Controller.get_Works_On_Hours(essn.getText()); //calls db to check existing hours
			if(existingHours == -1) { //this never happens
				throw new Exception("Error?"); //TODO fix this later
			} else if(existingHours < 40 && (existingHours + tempHours <= 40)) {
				if(!essn.getText().isEmpty() && !pno.getText().isEmpty()) {
					//if get here, good to go, call db_insert_project
					String verify = JDBC_Controller.insert_Employee_WorksOn_Project(essn.getText(), Integer.parseInt(pno.getText()), hours.getText());
					if(verify != "success project insert") {
						throw new Exception(verify);
					}
					System.out.println("got after the insert");
				}
			} else {
				System.out.println("Too many hours for this employee...");
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nToo many hours for this employee");
				projectInformation_errorLabel.setVisible(true);
				hours.getStyleClass().add("error");
			}
		} catch(Exception e) {
			if(e.getMessage().contains("ORA-00001")) { //primary key duplicate
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nESSN-Pno combination already exists");
				projectInformation_errorLabel.setVisible(true);
				essn.getStyleClass().add("error");
				pno.getStyleClass().add("error");
			} else if(e.getMessage().contains("ORA-02291")) { //pno and/or essn doesn't exist
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\nPno and/or Essn do not exist");
				projectInformation_errorLabel.setVisible(true);
				essn.getStyleClass().add("error");
				pno.getStyleClass().add("error");
			} else {
				System.out.println(e.getMessage());
				projectInformation_errorLabel.setText(projectInformation_errorLabel.getText() + "\n" + e.getMessage());
				projectInformation_errorLabel.setVisible(true);
			}
		}
	}
	
	@FXML
	private void projectInformation_Next() {
		mainAccordion.setExpandedPane(dependentInformation);
	}
	
	
	@FXML
	private void yes_Dependent() {
		d_Essn.setVisible(true);
		d_DependentName.setVisible(true);
		d_Sex.setVisible(true);
		d_Bdate.setVisible(true);
		d_Relationship.setVisible(true);
		dependentInformation_Submit.setVisible(true);
	}
	
	@FXML
	private void no_Dependent() {
		d_Essn.setVisible(false);
		d_DependentName.setVisible(false);
		d_Sex.setVisible(false);
		d_Bdate.setVisible(false);
		d_Relationship.setVisible(false);
		dependentInformation_Submit.setVisible(false);
	}
	
	@FXML
	private void check_Dependent_Information() {
		dependentInformation_errorLabel.setVisible(false); //reset errorLabel in beginning
		dependentInformation_errorLabel.setText(""); //reset text
		
		if(choiceBox.getSelectionModel().getSelectedItem() == "yes") {
			if(d_Essn.getText().isEmpty()) {
				System.out.println("Missing ESSN");
				dependentInformation_errorLabel.setText(dependentInformation_errorLabel.getText() + "\nMissing ESSN");
				dependentInformation_errorLabel.setVisible(true);
				d_Essn.getStyleClass().add("error");
			}
			if(d_DependentName.getText().isEmpty()) {
				System.out.println("Missing Dependent Name");
				dependentInformation_errorLabel.setText(dependentInformation_errorLabel.getText() + "\nMissing Dependent Name");
				dependentInformation_errorLabel.setVisible(true);
				d_DependentName.getStyleClass().add("error");
			} else {
				try {
					String verify = JDBC_Controller.insert_Employee_Dependent(d_Essn.getText(), 
							d_DependentName.getText(), d_Sex.getText(), 
							d_Bdate.getText(), d_Relationship.getText());
					if(verify != "successful dependent insert") {
						throw new Exception(verify);
					}
					System.out.println("got after the insert");
				} catch(Exception e) {
					if(e.getMessage().contains("ORA-00001")) { //primary key duplicate
						dependentInformation_errorLabel.setText(dependentInformation_errorLabel.getText() + "\nESSN-DependentName combination already exists");
						dependentInformation_errorLabel.setVisible(true);
						d_Essn.getStyleClass().add("error");
						d_DependentName.getStyleClass().add("error");
					}
				}
			}
		}
		
	}
	
	@FXML
	private void finish_to_Report() {
		mainAccordion.setVisible(false);
		reportPane.setVisible(true);
		
		ResultSet GI_Results = JDBC_Controller.get_GI_Report(ssn.getText());
    	ResultSet PI_Results = JDBC_Controller.get_PI_Report(ssn.getText());
    	ResultSet DI_Results = JDBC_Controller.get_DI_Report(ssn.getText());
    	//parse through the data
    	try {
			while(GI_Results.next()) {
				GI_Report.setText(GI_Report.getText() + "Fname: " + GI_Results.getString(1) + "\n");
				GI_Report.setText(GI_Report.getText() + "Lname: " + GI_Results.getString(2) + "\n");
				GI_Report.setText(GI_Report.getText() + "Minit: " + GI_Results.getString(3) + "\n");
				GI_Report.setText(GI_Report.getText() + "Ssn: " + GI_Results.getString(4) + "\n");
				GI_Report.setText(GI_Report.getText() + "Bdate: " + GI_Results.getString(5) + "\n");
				GI_Report.setText(GI_Report.getText() + "address: " + GI_Results.getString(6) + "\n");
				GI_Report.setText(GI_Report.getText() + "Sex: " + GI_Results.getString(7) + "\n");
				GI_Report.setText(GI_Report.getText() + "Salary: " + GI_Results.getString(8) + "\n");
				GI_Report.setText(GI_Report.getText() + "Superssn: " + GI_Results.getString(9) + "\n");
				GI_Report.setText(GI_Report.getText() + "Dno: " + GI_Results.getString(10) + "\n");
				GI_Report.setText(GI_Report.getText() + "Email: " + GI_Results.getString(11) + "\n");
			}
			if(PI_Results != null) {
				while(PI_Results.next()) {
					PI_Report.setText(PI_Report.getText() + "Essn: " + PI_Results.getString(1) + "\n");
					PI_Report.setText(PI_Report.getText() + "Pno: " + PI_Results.getString(2) + "\n");
					PI_Report.setText(PI_Report.getText() + "Hours: " + PI_Results.getString(3) + "\n");
				}
			} else {
				DI_Report.setText("Nothing to show");
			}
			if(DI_Results != null) {
				while(DI_Results.next()) {
					DI_Report.setText(DI_Report.getText() + "Essn: " + DI_Results.getString(1) + "\n");
					DI_Report.setText(DI_Report.getText() + "Dependent Name: " + DI_Results.getString(2) + "\n");
					DI_Report.setText(DI_Report.getText() + "Sex: " + DI_Results.getString(3) + "\n");
					DI_Report.setText(DI_Report.getText() + "Bdate: " + DI_Results.getString(4) + "\n");
					DI_Report.setText(DI_Report.getText() + "Relationship: " + DI_Results.getString(5) + "\n");
				}
			} else {
				DI_Report.setText("Nothing to show");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * comes with startup of the code. stuff that should be upon loading.
	 */
	@FXML
	private void initialize() {
		reportPane.setVisible(false); //don't show this until ready
		mainAccordion.setExpandedPane(generalInformation); //sets the general information pane to expanded upon loading in
		
		//make dependent initially invisible
		no_Dependent();
		//make the error labels invisible initially
		generalInformation_errorLabel.setVisible(false);
		projectInformation_errorLabel.setVisible(false);
		dependentInformation_errorLabel.setVisible(false);
		
		
		//sets values of the choice box for dependent
		ObservableList<String> availableChoices = FXCollections.observableArrayList("yes", "no"); 
		choiceBox.setItems(availableChoices);
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(
				(ObservableValue<? extends Number> obval, Number old_val, Number new_val) -> {
					//these numbers = index of the selection array
					if(new_val.equals(0)) {
						yes_Dependent();
					} else if(new_val.equals(1)) {
						no_Dependent();
					}
					
				}
				);
		
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
