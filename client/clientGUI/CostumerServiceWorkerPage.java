package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CostumerServiceWorkerPage implements Initializable {
	
	@FXML
	private Button makeOrderBtn = null;
	@FXML
	private Button logOutBtn = null;
	@FXML
	private Label info = new Label(); 
	@FXML
	private Button costumerRegisterBtn = null;
	
	FXMLLoader loader = new FXMLLoader();
	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView CostumerService;
	
	Image logo= new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image CostumerService1= new Image(getClass().getResourceAsStream("CostumerServiceWorker.jpg"));

	


	
	/**
	 * The start method is used to launch the application and set the primary stage of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {	
		// Load the FXML file for the Costumer Service Worker GUI
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CostumerServiceWorker.fxml"));
		
		// Create a new scene with the loaded FXML as the root node
		Scene scene = new Scene(root);
		
		// Set the title of the primary stage
		primaryStage.setTitle("Costumer service page");
		
		// Set the scene of the primary stage
		primaryStage.setScene(scene);
		
		// Show the primary stage
		primaryStage.show();	 	  
		
		// Set an event handler for when the primary stage is closed
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      // Exit the application when the primary stage is closed
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}

	/**
	 * logOut method is used to log out the user by sending a logOut message to the server.
	 * It also hides the current window and opens the login window for the user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
	    // Create a new ArrayList to store the logOut message
	    ArrayList<String> list = new ArrayList<>();
	    ChatClient.msgRecieved = ""; 
	    list.add("logOut"); // Add logOut message to the list
	    ClientUI.chat.accept(list); // Send the logOut message to the server
	    ((Node) event.getSource()).getScene().getWindow().hide(); // Hiding primary window
	    logInController aFrame = new logInController(); // Create EkrutFrame
	    aFrame.start((Stage) logOutBtn.getScene().getWindow()); // open login window
	}

	/**
	 * makeOrdertBtn method is used to check if the manager is registered as costumer (that's mean he can make order).
	 * If the manager is a registered costumer, it opens the CostumerHomePageForn window , otherwise, it will show an error message.
	 *
	 * @param event The event that triggers the makeOrdert action
	 * @throws Exception If there is an error while checking the manager's permission or opening the ManagerCostumerApproval window
	 */
	
	public void makeOrdertBtn(ActionEvent event) throws Exception {
		 if(ChatClient.u1.getPermissions()==null) {
			 info.setText("The worker is not registered as a costumer!");
			 return;
		 }
			if (ChatClient.u1.getPermissions()!=null || ChatClient.u1.getPermissions().equals("Approve")) {
				ChatClient.u1.setRole("Subscriber");
				CostumerHomePageController aFrame3 = new CostumerHomePageController();
				  aFrame3.start((Stage) costumerRegisterBtn.getScene().getWindow());
		  }
		  else {
			  info.setText("The worker is not registered as a costumer!");
			  
		  }
	}
	
	/**
	 * getCostumerRegistertBtn method is used to open the UsersFrame window for the costumer service worker  to register the users as a costumer
	 *
	 * @param event The event that triggers the getCostumerRegistert action
	 * @throws Exception If there is an error while loading the UsersFrame.fxml or opening the UsersFrame window
	 */
	public void getCostumerRegistertBtn(ActionEvent event) throws Exception {
		// Load the FXML file for the User Frame GUI
		Parent pane = FXMLLoader.load(getClass().getResource("UsersFrame.fxml"));
		((Node)event.getSource()).getScene().getWindow().hide();	
		UsersController usersController= new UsersController();
		Stage primaryStage = new Stage();
		usersController.start(primaryStage);
	}
	
	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    //Set the logo image
	    logoImage.setImage(logo);
	    //Set the CostumerService image
	    CostumerService.setImage(CostumerService1);
	}
	
	
	

}
