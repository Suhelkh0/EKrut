package client;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientHomePgaeController {
	@FXML 
	private TextField IP;
	@FXML 
	public static Text errorMSG;
	
	public void connectBTN(ActionEvent event) {
		if(IP.getText().isEmpty()) {
			errorMSG.setText("please enter the server ip");
			return;
		}
		try {
		 ClientUI.chat= new ClientController(IP.getText(),5555);
		 ArrayList<String> newClient = new ArrayList<>();
		 newClient.add("newClient");
		 ClientUI.chat.accept(newClient);}
		catch (Exception e) {
			errorMSG.setText("Error try Again");
		}
		 FXMLLoader loader = new FXMLLoader();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			Pane root=null;
			try {
				root = loader.load(getClass().getResource("/clientGUI/loginFrame.fxml").openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root);			
			
			primaryStage.setScene(scene);		
			primaryStage.show();
			
	}
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/client/ClientHomePgae.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();	
		   primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}

}
