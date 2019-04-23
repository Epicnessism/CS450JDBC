/**
 * Login Screen Controller FXML
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */
package cs450JDBC;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import cs450JDBC.MainApplication;
import cs450JDBC.JDBC_Controller;

public class LoginLayoutController {
	
	@FXML
	private TextField managerSSN;
	
	@FXML
	private Button searchManagerSSN;
	
	@FXML
	private Label LoginStatusLabel;
	
	private MainApplication MainApplication;
	
	private JDBC_Controller JDBC_Controller = new JDBC_Controller(); 
	
	/**
     * The constructor is called before the initialize() method.
     */
    public LoginLayoutController() {
    }
    
    @FXML
    public void search_ManagerSSN() {
    	boolean verify = JDBC_Controller.check_Manager_SSN(managerSSN.getText());
    	if(verify) {
    		LoginStatusLabel.setText("Welcome!");
    		//TODO load the new screen for inputting a new employee...
    	} else {
    		LoginStatusLabel.setText("SSN denied, please try again.");
    	}
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	LoginStatusLabel.setText("");
//    	searchManagerSSN.setOnAction(e -> testButtonLabel.setText(managerSSN.getText()));
//    	searchManagerSSN.setOnAction(e -> JDBC_Controller.check_Manager_SSN(managerSSN.getText()));
//        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApplication mainApplication) {
        this.MainApplication = mainApplication;

//        // Add observable list data to the table
//        personTable.setItems(mainApp.getPersonData());
    }
	
}