package cs450JDBC;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.*;

import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import cs450JDBC.view.InsertNewEmployeeOverviewController;
import cs450JDBC.view.LoginLayoutController;


public class MainApplication extends Application {
	
	private Stage primaryStage;
    private AnchorPane LoginLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CompanyApp");
        
        initLoginLayout();
//        showPersonOverview();
	}
	
	/**
     * Initializes the Login Layout screen
     */
    public void initLoginLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/LoginLayout.fxml"));
            LoginLayout = (AnchorPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(LoginLayout); //Instantiate the a new scene
            primaryStage.setScene(scene); //load the desired scene?
            primaryStage.show(); //displays the GUI i think?
            
            LoginLayoutController controller = loader.getController(); //what does this do again??
		    controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * show the insert new employee scene
     */
    public void showInsertNewEmployeeOverview() {
    	try {
    		this.primaryStage.setTitle("Manager's Name..."); //TODO add manager's name
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApplication.class.getResource("view/InsertNewEmployeeOverview.fxml"));
    		AnchorPane InsertNewEmployeeOverview = (AnchorPane) loader.load();
    		
    		Scene scene = new Scene(InsertNewEmployeeOverview); //Instantiate the a new scene
//    		scene.getStylesheets().add("../InsertNewEmployeeOverview.css"); //this doesn't work..
    		scene.getStylesheets().add(getClass().getResource("InsertNewEmployeeOverview.css").toExternalForm());
            primaryStage.setScene(scene); //load the desired scene?
            primaryStage.show(); //displays the GUI i think?
    		
    		InsertNewEmployeeOverviewController controller = loader.getController(); //what does this do again??
		    controller.setMainApp(this);
    		
    		
    	} catch (IOException e) {
          e.printStackTrace();
      }
    }
    
	
	/**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
