/**
 * Employee Overview Controller
 * @author Tony Wang
 * CS450 Final Project
 * 4/22/19
 */
package cs450JDBC;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import cs450JDBC.MainApplication;
import cs450JDBC.JDBC_Controller; //main controller that does all the DB calling

public class EmployeeOverviewController {
	
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
	
	
	private MainApplication MainApplication;
	private JDBC_Controller JDBC_Controller = new JDBC_Controller(); 
	
	/**
	 * Constructor called before the initialize() method
	 */
	public EmployeeOverviewController() {
	}
	
//	@FXML
//	
	
	@FXML
	private void initialize() {
		
	}
	
}
