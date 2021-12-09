package ui;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PasswordGenerator;

public class Main extends Application{
	
    public static Stage stage;

    /**
     *  The main function
     *
     */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
		launch(args);
        
    }

	/**
     *  Function that opens the main view of the program
     *
     */
    @Override
    public void start(Stage stage) throws Exception {
    	
    	Main.stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);
        Main.stage.setTitle("Platform Login Security");
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}