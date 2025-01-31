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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author suhel
 * @author amne
 *
 */
public class CostumerHomePageController implements Initializable {
	@FXML
	private Button makeOrder;
	@FXML
	private Button confirmReceivingDeliveryOrder;
	@FXML
	private Button pickUpOrder;
	@FXML
	private Button logOutBtn;
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView makeNewOrder;
	Image makeOrderImage = new Image(getClass().getResourceAsStream("makeorder.png"));
	@FXML
	private ImageView receivingOrder;
	Image receivingOrderImage = new Image(getClass().getResourceAsStream("receiveorder.png"));
	@FXML
	private ImageView pickUp;
	Image pickUpImage = new Image(getClass().getResourceAsStream("pickUp.jpg"));

	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CostumerHomePageForm.fxml"));
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
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logo.setImage(logoImage);
		makeNewOrder.setImage(makeOrderImage);
		receivingOrder.setImage(receivingOrderImage);
		pickUp.setImage(pickUpImage);
		CreateNewOrderController.newTotal = 0;
	}

	/**
	 * The makeOrder method is used to handle the "Make Order" button press in the client GUI.
	 *It hides the current window, creates a new stage, loads the ChooseOrderType.fxml file,
	 *sets the scene and shows the new window. It also calls the start method in the
	 *ChooseOrderTypeController class to initialize the new window
	 * @param event
	 * @throws Exception
	 */
	public void makeOrder(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGUI/ChooseOrderType.fxml").openStream());
		ChooseOrderTypeController chooseOrderTypeController = loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		chooseOrderTypeController.start(primaryStage);
	}

	/**
	 * The confirmReceivingDeliveryOrderBtn method is used to handle the "Confirm Order" button press in the client GUI.
	 *It hides the current window, creates a new stage, creates a new instance of the CustomerConfirmationController,
	 *and calls the start method in the CustomerConfirmationController class to initialize the new window.
	 * @param event
	 * @throws Exception
	 */
	public void confirmReceivingDeliveryOrderBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		CustomerConfirmationController customerConfirmationController = new CustomerConfirmationController();
		customerConfirmationController.start(primaryStage);
	}

	/**
	 * The pickUpBtn method is used to handle the "Pick-Up" button press in the client GUI.
     * It hides the current window, creates a new stage, creates a new instance of the PickUpController,
     * and calls the start method in the PickUpController class to initialize the new window.
	 * @param event
	 * @throws Exception
	 */
	public void pickUpBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		PickUpController pickUpController = new PickUpController();
		pickUpController.start(primaryStage);
	}

	/**
	 * The logOutBtn method is used to handle the "Log Out" button press in the client GUI.
	 *  It calls the logOut method to log out the current user.
	 * @param event
	 * @throws Exception
	 */
	public void logOutBtn(ActionEvent event) throws Exception {
		logOut(event);
	}
	
	/**
	 * The logOut method is used to handle log out process in the client GUI.
	 *	It clears the order details, hides the current window, sends a log out request to the server,
	 *	and opens the login window.
	 * @param event
	 * @throws Exception
	 */
	public static void logOut(ActionEvent event) throws Exception {
		ChooseOrderTypeController.orderDetails.clear();
		((Node) event.getSource()).getScene().getWindow().hide();		
		ArrayList<String> list = new ArrayList<>();
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		logInController logIn = new logInController(); // create EkrutFrame
		try {
			logIn.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The home method is used to handle the "Home" button press in the client GUI.
	 *	It clears the order details, hides the current window, creates a new stage, creates a new instance of the CostumerHomePageController,
	 *	and calls the start method in the CostumerHomePageController class to initialize the new window.
	 * @param event
	 * @throws Exception
	 */
	public static void home(ActionEvent event) throws Exception {
		ChooseOrderTypeController.orderDetails.clear();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		CostumerHomePageController costumerHomePageController = new CostumerHomePageController();
		Stage primaryStage = new Stage();
		ChatClient.primaryStage = primaryStage;
		costumerHomePageController.start(primaryStage);
	}

}
