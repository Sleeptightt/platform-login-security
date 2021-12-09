package ui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
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
import javafx.stage.StageStyle;
import model.DatabaseConnection;
import model.PasswordGenerator;

public class LoginController {
	
	@FXML
	private Label errorText;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    /**
     *  Function responsible of validating the user data in order to log into the platform
     *
     */
    @FXML
    void tryLogin(ActionEvent event) {
    	
    	String username = usernameText.getText();
    	String password = passwordText.getText();
    	String query = "SELECT username, password, type FROM public.user WHERE username = '"+username+"'";
    	try {
    		Connection connection= DatabaseConnection.getConnection();
    		PreparedStatement ps = connection.prepareStatement(query);
    		ResultSet rs = ps.executeQuery();
    		if (rs.next()) {
    			
    			String userBD = rs.getString("username");
    			String passBD = rs.getString("password");
    			if(passBD.equals("")) {
    				rs.close();
					ps.close();
					connection.close();
    				throw new NullPointerException();
    			}
    			String type = rs.getString("type");
    			
    			if (PasswordGenerator.validatePassword(password, passBD)) {
    				rs.close();
					ps.close();
					connection.close();
    				if (type.equals("admin")) {
    					adminView();
					}else {
						userView(userBD);	
					}
				}else {
					errorText.setText("Wrong Password");
					errorText.setVisible(true);
					rs.close();
					ps.close();
					connection.close();
				}
    		}else {
    			errorText.setVisible(true);
    		}
    
    	} catch (NullPointerException n) {
    		alert();
    	} catch (Exception ex) {
    		System.out.println(ex.toString());
    	}

    }

    /**
     *  Function that opens the new user view
     *
     */
    @FXML
    void tryNewUser(ActionEvent event) {
    	 try {
             FXMLLoader loader= new FXMLLoader(getClass().getResource("newUser.fxml"));
             Parent root = loader.load();
             Scene scene = new Scene(root);
             Main.stage.setScene(scene);
         } catch (IOException e) {
             e.printStackTrace();
         }

    }
    
    void alert() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("Your password was erased by an administrator");
        info.show();
    }
  
    /**
     *  Function that opens the admin user view
     *
     */
    void adminView() {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
            Parent root=loader.load();
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    /**
     *  Function that opens the normal user view
     *
     */
    void userView(String user) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("usersDashBoard.fxml"));
            Parent root=loader.load();
            UserController scc= (UserController) loader.getController();         
            scc.setUsername(user);
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
