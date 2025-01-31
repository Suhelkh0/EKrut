package clientGUI;

import java.util.ArrayList;
import java.util.Random;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.TimerTwoMinutes;

/**
 * 
 * @author suhel
 * @author amne
 *
 */
public class ChooseOrderTypeController {
	ObservableList<String> type = FXCollections.observableArrayList("Remote Order", "Immediate Order");
	@FXML
	private ComboBox<String> TypeBox;
	@FXML
	private Label errorMsgToProceed;
	@FXML 
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML 
	private ImageView immediateOrder;
	Image immediateOrderImage = new Image(getClass().getResourceAsStream("OrderTypeMachine.png"));
	@FXML 
	private ImageView remoteOrder;
	Image remoteOrderImage = new Image(getClass().getResourceAsStream("orderTypeOnline.png"));
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	
	String id = ChatClient.u1.getId();
	String username = ChatClient.u1.getUserName();
	String phoneNumber = ChatClient.u1.getPhoneNumber();
	public static String ordertype;
	public static ArrayList<String> orderDetails = new ArrayList<>();
	
	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@FXML
	private void initialize() {
		TypeBox.setItems(type);
		logo.setImage(logoImage);
		immediateOrder.setImage(immediateOrderImage);
		remoteOrder.setImage(remoteOrderImage);
	}
	
	/**
	 * The start method is used to initialize the ChooseOrderType window in the client GUI.
     *  It adds the necessary information to the orderDetails list, loads the ChooseOrderType.fxml file, sets the scene and shows the window.
	 * It also starts a new thread for the TimerTwoMinutes class, assigns the primary stage and sets an event handler for the close request.
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		orderDetails.add("InsertNewOrder");
		orderDetails.add(id);
		orderDetails.add(username);
		orderDetails.add(phoneNumber);
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/ChooseOrderType.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		ChatClient.primaryStage = primaryStage;
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
	}
	
	/**
	 * The chosenType method is used to handle the choice of order type in the ChooseOrderType window in the client GUI.
	 *It gets the value of the chosen order type, hides the current window, stops the thread, creates a new stage and performs different actions based on the chosen type.
     * If the type is "Remote Order", it initializes the MainPageRemoteOrder window.
     * If the type is "Immediate Order", it loads the CreateNewOrder.fxml file, gets customer information from the server, loads catalog items from the store, sets the scene and shows the window.
     *  Additionally, it also sets an event handler for the close request.
	 * @param event
	 * @throws Exception
	 */
	public void chosenType(ActionEvent event) throws Exception {
		ordertype = TypeBox.getValue();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.thread.stop();
		Stage primaryStage = new Stage();
		if(ordertype.equals("Remote Order")) {
			MainPageRemoteOrderController remote = new MainPageRemoteOrderController();
			remote.start(primaryStage);
		}
		if(ordertype.equals("Immediate Order")) {
			ChatClient.primaryStage = primaryStage;
			ArrayList<String> requestCostomerInfo = new ArrayList<>();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/clientGUI/CreateNewOrder.fxml").openStream());
			requestCostomerInfo.add("costumerInfo");
			username = "'" + username + "'";
			requestCostomerInfo.add(username);
			ClientUI.chat.accept(requestCostomerInfo);
			CreateNewOrderController createNewOrderController = loader.getController();
			ArrayList<String> stores = new ArrayList<>();
			stores.add("haifa");
			stores.add("karmiel");
			stores.add("eilat");
			stores.add("beersheva");
			stores.add("abudhabi");
			stores.add("dubai");
			Random rand = new Random(); 
			int storeIndex = rand.nextInt(6);
			String tableName = stores.get(storeIndex);
			createNewOrderController.loadCatalogItemsFromDB(tableName);
			orderDetails.add(tableName);
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
	
	/**
	 * logOut method is used to log out the current user from the chat application.
	 *It stops the thread running the chat client and redirects the user to the logout page.
	 * @param event
	 * @throws Exception
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}
	
	/**
	 * homeBtn method is used to redirect the user to the home page of the chat application.
     *  It stops the thread running the chat client and redirects the user to the home page.
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
}
