package ui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DatabaseConnection;
import model.PasswordGenerator;

public class UserController {

    @FXML
    private Button saveBtn;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField lastAccessText;
    @FXML
    private Button backBtn;
    @FXML
    private PasswordField newPasswordText;
    @FXML
    private PasswordField confirmPasswordText;
    private String username;
    
    /**
     *  Function responsible of changing the password of a user that already exists in the database
     *
     */
    @FXML
    void saveNewPassword(ActionEvent event) {
    	String query = "SELECT password FROM public.user WHERE username = '"+username+"'";
    	
    	try {
    		Connection connection = DatabaseConnection.getConnection();
    		PreparedStatement ps = connection.prepareStatement(query);
        	ResultSet rs = ps.executeQuery();
    		if (rs.next()) {
    			
    			String passBD = rs.getString("password");
    			if(!newPasswordText.getText().equals(confirmPasswordText.getText())) {
    				Alert info = new Alert(AlertType.INFORMATION);
    		        info.setTitle("Information");
    		        info.setHeaderText(null);
    		        info.initStyle(StageStyle.UTILITY);
    		        info.setContentText("The passwords don't match");
    		        info.show();
    			}
    			else if(PasswordGenerator.validatePassword(newPasswordText.getText(), passBD)) {
    				Alert info = new Alert(AlertType.INFORMATION);
    		        info.setTitle("Information");
    		        info.setHeaderText(null);
    		        info.initStyle(StageStyle.UTILITY);
    		        info.setContentText("The password has to be different to the one you currently have");
    		        info.show();
    				newPasswordText.clear(); confirmPasswordText.clear();
    			}else if(newPasswordText.getText().isEmpty()) {
    				Alert info = new Alert(AlertType.INFORMATION);
    		        info.setTitle("Information");
    		        info.setHeaderText(null);
    		        info.initStyle(StageStyle.UTILITY);
    		        info.setContentText("The password can not be empty");
    		        info.show();
    			} else {
    				String hashedPassword = PasswordGenerator.createHash(newPasswordText.getText());
    				String setPassword = "Update public.user set password = " + "'"+ hashedPassword +"'" + " where username= '" + username + "'";
    	    		Statement st = connection.createStatement();
    	            st.execute(setPassword);
    	            Alert info = new Alert(AlertType.INFORMATION);
    		        info.setTitle("Information");
    		        info.setHeaderText(null);
    		        info.initStyle(StageStyle.UTILITY);
    		        info.setContentText("The user password was updated succesfully");
    		        info.show();
        		}
    		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    void setUsername(String username) {
    	this.username = username;
    }

    @FXML
    void initialize() {  	
    	lastAccessText.setText(new Date().toString());
    }
    
    @FXML
    void tryBack(ActionEvent event) {
		try {
			Main.stage.close();
			Main.stage= new Stage();
	        Parent root;
			root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
	        Main.stage.setTitle("Platform Login Security");
	        Main.stage.setScene(scene);
	        Main.stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    }

}
