package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class CustomerConfirmationController implements Initializable {
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView customer;
	Image customerImage = new Image(getClass().getResourceAsStream("inventory-management.png"));
	ArrayList<String> DBData = new ArrayList<>();
	@FXML
	private ChoiceBox<String> ordersNumbers;
	@FXML
	private Label itemNumber = new Label();
	@FXML
	private Label msgDisplay;
	@FXML
	private Label statusPending;
	@FXML
	private Label successfullMsg;
	@FXML
	private Button submitBtn=null;
	@FXML
	private Button saveBtn=null;
	@FXML
	private Button backBtn=null;
	@FXML
	private ChoiceBox<String> setStatus;
	
	private boolean oneTimeStatusDisplay=true;
	private String[] status= {"choose status","PENDING","CONFIRM"};
	private String[] availableOrders;
	private String[] noOrders= new String[1];
	
	/**
	 * The start method is responsible for displaying the Customer Confirmation scene on the primary stage.
	 *
	 * @param primaryStage the primary stage that the scene will be displayed on.
	 * @throws Exception 
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CustomerConfirmation.fxml"));
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
	/**
	 * Method to handle the back button press.
	 * 
	 * @param event - The action event that triggered the method call.
	 * @throws Exception - If there is any exception while creating the new stage.
	 */
	public void back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		CostumerHomePageController costumerHomePageController = new CostumerHomePageController();
		costumerHomePageController.start(stage);
    }
	/**
	 * Method to initialize the components of the scene.
	 * 
	 * @param url - The arg0 used to resolve relative paths for the root object, or null if the location is not known
	 * @param rb - The arg1 used to localize the root object, or null if the root object was not localized
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		logo.setImage(logoImage);
		customer.setImage(customerImage);
		getOrdersNumberFromDB();
		saveBtn.setVisible(false);
		setStatus.setVisible(false); 
		if(DBData.isEmpty()) {
			ordersNumbers.getItems().clear();
			noOrders[0]="no orders";
			ordersNumbers.getItems().addAll(noOrders);
			ordersNumbers.setValue("no orders");
		}
		else {
			ordersNumbers.setValue("choose order number");
			setStatus.getItems().addAll(status);
			display();
		}
		
	}
	/**
	 * method that controls the display 
	 */
	public void display() {
		setStatus.setValue("choose status");
		if(DBData.isEmpty()) {
			ordersNumbers.getItems().clear();
			noOrders[0]="no orders";
			ordersNumbers.getItems().addAll(noOrders);
			ordersNumbers.setValue("no orders");
			saveBtn.setVisible(false);
			setStatus.setVisible(false); 
			itemNumber.setVisible(false);
		}
		else {
			availableOrders = new String[(DBData.size()/2)+1];
			availableOrders[0] = "choose order number";
			int j=1;
			for(int i=0;i<DBData.size();i+=2) {
				availableOrders[j]=DBData.get(i);
				j++;
			}
			ordersNumbers.getItems().clear();
			ordersNumbers.getItems().addAll(availableOrders);
			ordersNumbers.setValue("choose order number");
		}
	}
	/**
	 * Handles the submit button event.
	 * 
	 * @param event the ActionEvent object that is triggered when the submit button is clicked.
	 * @throws Exception 
	 */
	public void submit(ActionEvent event) throws Exception {
		successfullMsg.setText("");
		switch (ordersNumbers.getValue()) {
	    case "no orders":
	    	statusPending.setVisible(false);
	    	msgDisplay.setText("you have no orders to confirm");
	    	break;
	    case "choose order number":
	    	statusPending.setVisible(false);
	    	msgDisplay.setText("You must fill all fields");
	    	break;
	    default:
	    	statusPending.setVisible(false);
	    	msgDisplay.setText("");
	    	saveBtn.setVisible(true);
	    	setStatus.setVisible(true);
			itemNumber.setVisible(true);
	    	itemNumber.setText("status for order number "+ ordersNumbers.getValue()+ " is: \n"+DBData.get(DBData.indexOf(ordersNumbers.getValue())+1) + " do you like to change it?");
		}
	}
	/**
	 * Handles the save button event.
	 * @param eventThe action event that triggered the method call.
	 * @throws Exception - If there is any exception while creating the new stage.
	 */
	public void save(ActionEvent event) throws Exception {
		switch (setStatus.getValue()) {
	    case "no orders":
	    	msgDisplay.setText("you have no orders to confirm");
	    	statusPending.setVisible(false);
	    	break;
	    case "choose status":
	    	msgDisplay.setText("You must fill all fields");
	    	statusPending.setVisible(false);
	    	break;
	    default:
	    	
			if(setStatus.getValue().equals("CONFIRM")) 
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Order Confirmation Alert");
				alert.setContentText("Do you want to CONFIRM receiving this order?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					saveBtn.setVisible(false);
					itemNumber.setVisible(false);
					setStatus.setVisible(false);
					int index = DBData.indexOf(ordersNumbers.getValue())+1;
					DBData.remove(index);
					DBData.add(index, setStatus.getValue());
					successfullMsg.setText("saved successfully");
					msgDisplay.setText("");
					saveDataToDB();
					getOrdersNumberFromDB();
					display();
					statusPending.setVisible(false);
				}
			}
			else {
				statusPending.setVisible(true);
				statusPending.setText("The chosen Status is already PENDING");
				}
		}
	}
	
	/**
	* This method saves data to the database by creating an ArrayList of strings called "orderStatus",
	* adding values to the ArrayList, and then sending the ArrayList to the "accept" method of the "chat" object from the ClientUI class.
	*
	* @throws Exception if there is an error when sending the ArrayList to the "accept" method
	*/
	public void saveDataToDB() {
	    ArrayList<String> orderStatus = new ArrayList<>();
	    orderStatus.add("updateOrderConfirmation");
	    orderStatus.add(setStatus.getValue());
	    orderStatus.add(ChatClient.u1.getId());
	    orderStatus.add(ordersNumbers.getValue());
	    try {
	        ClientUI.chat.accept(orderStatus);
	        DBData=ChatClient.item;
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	* This method retrieves the number of orders from the database by creating an ArrayList of strings called "sendToServer", 
	* adding values to the ArrayList, and then sending the ArrayList to the "accept" method of the "chat" object from the ClientUI class.
	* The method then sets the DBData to the value of the ChatClient's item.
	*
	* @throws Exception if there is an error when sending the ArrayList to the "accept" method
	*/
	public void getOrdersNumberFromDB() {
	    ArrayList<String> sendToServer = new ArrayList<>();
	    sendToServer.add("ordersNumber");
	    sendToServer.add(ChatClient.u1.getId());
	    try {
	        ClientUI.chat.accept(sendToServer);
	        DBData=ChatClient.item;
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

}
