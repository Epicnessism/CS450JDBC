/**
 * Login Screen Controller FXML
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */
package cs450JDBC.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import cs450JDBC.MainApplication;
import cs450JDBC.JDBC_Controller; //main controller that does all the DB calling

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
    private void search_ManagerSSN() {
    	boolean verify = JDBC_Controller.check_Manager_SSN(managerSSN.getText());
    	if(verify) {
    		LoginStatusLabel.setText("Welcome!"); //TODO too fast, do somewhere else
    		//TODO load the new screen for inputting a new employee...
    		MainApplication.showInsertNewEmployeeOverview(); //this should show new scene
    	} else {
    		LoginStatusLabel.setText("SSN denied, please try again.");
    	}
    }
    
    
    @FXML
    private void onEnter(ActionEvent actionEvent) {
    	System.out.println("On Enter"); 
    	search_ManagerSSN(); //UX quality of consuer experience, enter procs searchMgrSSN
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
