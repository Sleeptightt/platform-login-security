package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.StageStyle;
import model.DatabaseConnection;

public class AdminController {

    @FXML
    private Button deleteBtn;

    @FXML
    private Button setPasswordBtn;

    @FXML
    private ComboBox<String> comboBoxUsers;
    
    private Connection connection;
    
    private PreparedStatement ps;


    @FXML
    void deleteUser(ActionEvent event) {
    	try {
    		if(comboBoxUsers.getValue().equals("")) {
    			throw new NullPointerException();
    		}
    		String delete = "DELETE FROM public.user WHERE username = '" + comboBoxUsers.getValue() + "'";
    		Statement st = connection.createStatement();
            st.execute(delete);
            getComboBox();
    	} catch (NullPointerException npe){
    		alert();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }


    @FXML
    void setBlankUserPassword(ActionEvent event) {
    	try {
        		if(comboBoxUsers.getValue().equals("")) {
        			throw new NullPointerException();
        		}
    		String query = "Update public.user set password = " + "''" + " where username= '" + comboBoxUsers.getValue() + "'";
    		Statement st = connection.createStatement();
            st.execute(query);
    	} catch (NullPointerException npe){
    		alert();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    void alert() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("No user was selected");
        info.show();
    }
    
    void getComboBox() {
    	String consulta = "SELECT username FROM public.user WHERE type = 'normal'";
    	comboBoxUsers.getItems().clear();
    	try {
    		if(connection == null) connection = DatabaseConnection.getConnection();
			ps = connection.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String user = rs.getString("username");
				comboBoxUsers.getItems().add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
    
 
    @FXML
    void initialize() {
		getComboBox();
    	
    }
    

}