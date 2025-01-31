package clientGUI;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.TimerTwoMinutes;

/**
 * 
 * @author Suhel
 * @author Amne
 *
 */
public class DroneDeliveryController {

	@FXML
	private ComboBox<String> methodBox;
	@FXML
	private TextField city = new TextField();
	@FXML
	private TextField street;
	@FXML
	private TextField houseNumber;
	@FXML
	private TextField receiverName;
	@FXML
	private TextField receiverPhone;
	@FXML
	private Button ProceedToPayment;
	@FXML
	private Label errorOneFieldIsEmpty;
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView address;
	Image addressImage = new Image(getClass().getResourceAsStream("addressssssss.png"));
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	
	ArrayList<String> requestCostomerInfo = new ArrayList<>();
	String username = ChatClient.u1.getUserName();
	
	/**
	 * initializing all the required data for this class
	 */
	@FXML
	private void initialize() {
		logo.setImage(logoImage);
		address.setImage(addressImage);
		
		requestCostomerInfo.add("costumerInfo");
		username = "'" + username + "'";
		requestCostomerInfo.add(username);
		ClientUI.chat.accept(requestCostomerInfo);
		city.setText(ChatClient.costumerInfo.get(0));
		street.setText(ChatClient.costumerInfo.get(1));
		houseNumber.setText(ChatClient.costumerInfo.get(2));
		receiverName.setText(ChatClient.costumerInfo.get(3));
		receiverPhone.setText(ChatClient.costumerInfo.get(4));
	}
	
	/**
	 * start method is used to start the UsersFrame window and to close 
	 * the program when the window is closed it also starts the timer
	 *
	 * @param primaryStage the stage where the UsersFrame window will be displayed
	 * @throws Exception if there is an error while loading the UsersFrame.fxml or setting the scene
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/DroneDelivery.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}
	
	/**
	 * change the page to the payment after testing the validity of the inputs
	 * @param event
	 * @throws Exception
	 */
	public void ProceedToPaymentBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		if(city.getText().trim().isEmpty() || street.getText().trim().isEmpty() || houseNumber.getText().trim().isEmpty() || receiverName.getText().trim().isEmpty() || receiverPhone.getText().trim().isEmpty()) {
			errorOneFieldIsEmpty.setText("One field is empty!");
		}
		else if (!(city.getText().trim().matches("[a-zA-Z\\s]+"))) {
			errorOneFieldIsEmpty.setText("Unvalid city!");
		}
		else if (!(street.getText().trim().matches("[a-zA-Z\\s]+"))) {
			errorOneFieldIsEmpty.setText("Unvalid street!");
		}
		else if (!(receiverName.getText().trim().matches("[a-zA-Z\\s]+"))) {
			errorOneFieldIsEmpty.setText("Unvalid receiver Name!");
		}
		else if (!(receiverPhone.getText().trim().matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"))) {
			errorOneFieldIsEmpty.setText("Unvalid phone number!");
		}
		else {
			try {
				Integer.parseInt(houseNumber.getText());
				FXMLLoader loader = new FXMLLoader();
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				ChatClient.thread.stop();
				Stage primaryStage = new Stage();
				ChatClient.primaryStage = primaryStage;
				Pane root = loader.load(getClass().getResource("/clientGUI/Payment.fxml").openStream());
				PaymentController paymentController = loader.getController();
				paymentController.loadCustomerInfo(ChatClient.costumerInfo);
				Scene scene = new Scene(root);			
				primaryStage.setScene(scene);
				primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				     @Override
				     public void handle(WindowEvent e) {
				      Platform.exit();
				      System.exit(0);
				     }
				   });
				primaryStage.show();
			} catch (NumberFormatException nfe) {
				errorOneFieldIsEmpty.setText("Unvalid house number!");
			}
		}
		/*if(ChooseOrderTypeController.orderDetails.size() > 7) {
			int lastIndex = ChooseOrderTypeController.orderDetails.size() - 1;
			ChooseOrderTypeController.orderDetails.remove(lastIndex);
		}*/
		String details = "";
		details=ChatClient.costumerInfo.get(0);
		details+=" "+ChatClient.costumerInfo.get(1);
		details+=" "+ChatClient.costumerInfo.get(2);
		ChooseOrderTypeController.orderDetails.add(details);
	}
	
	/**
	 * logOut method is used to log out the user by sending a logOut message to the server.
	 * It also hides the current window and opens the login window for the user.
	 *
	 * @param event The event that triggers the logOut action
	 * @throws Exception If there is an error while sending the logOut message or opening the login window
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}
	
	/**
	 * changing the page to the home page
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}

}
