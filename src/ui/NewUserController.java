package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public class NewUserController {

    @FXML
    private PasswordField confirmPasswordText;

    @FXML
    private Button createBtn;

    @FXML
    private Label errorText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    void tryCreate(ActionEvent event) {
    	String username = usernameText.getText();
    	String password = passwordText.getText();
    	String confirmPassword = confirmPasswordText.getText();
    	String query = "SELECT id FROM public.user WHERE username = '"+username+"'";
    	if(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
    		if(password.equals(confirmPassword)) {
		    	try {
		    		Connection connection = DatabaseConnection.getConnection();
		    		PreparedStatement ps = connection.prepareStatement(query);
		    		ResultSet rs = ps.executeQuery();
		    		if (!rs.next()) {
		    			
		    			query = "SELECT id FROM public.user";
		    			ps = connection.prepareStatement(query);
			    		rs = ps.executeQuery();
			    		int mx = -1;
		    			while(rs.next()) {
		    				int id = rs.getInt("id");
		    				mx = Integer.max(mx,id);
		    			}mx++;
		    			
		    			String safePassword = PasswordGenerator.generateStrongPasswordHash(password);
		    			query = "INSERT INTO public.user (id, username, password, type) VALUES ("+mx+",'"+username+"','"+safePassword+"','normal');";                 
		    			ps = connection.prepareStatement(query);
			    		ps.executeUpdate();
			    		
			    		Main.stage.close();
			    		Main.stage= new Stage();
			            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

			            Scene scene = new Scene(root);
			            Main.stage.setTitle("Platform Login Security");
			            Main.stage.setScene(scene);
			            Main.stage.show();
		    			
		    		}else {
		    			alert();
		    		}
		    
		    	} catch (Exception ex) {
		    		System.out.println(ex.toString());
		    	}
    		}else {
    			Alert info = new Alert(AlertType.INFORMATION);
                info.setTitle("Information");
                info.setHeaderText(null);
                info.initStyle(StageStyle.UTILITY);
                info.setContentText("The passwords don't match");
                info.show();
    		}
    	}else {
    		Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("Information");
            info.setHeaderText(null);
            info.initStyle(StageStyle.UTILITY);
            info.setContentText("Please fill all the inputs");
            info.show();
    	}
    }

    void alert() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("Username is already in use");
        info.show();
    }
    
    @FXML
    void initialize() {
    	
    	
    }
    
}
