package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Item;
import logic.MsgFromManger;
/**
 * 
 * @author danial
 *
 */
public class InventoryRefillingController implements Initializable {
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView reffilling;
	Image reffill = new Image(getClass().getResourceAsStream("inventory-management.png"));
	@FXML
	private Button submitBtn=null;
	@FXML
	private Button backBtn=null;
	@FXML
	private TableView<MsgFromManger> mailTable = new TableView<>();
	@FXML
	private TableColumn<MsgFromManger, String> mailCol;
	@FXML 
	private Label msgDisplay;
	@FXML 
	private Label noOrderSelected;
	ObservableList<MsgFromManger> msgList;

	/**
	* initialize method is used to initialize the elements of the scene 
	* when the InventoryRefilling.fxml is loaded.
	* It sets the style of the mailCol column, sets the images of the logo and reffilling ImageViews, 
	* calls the LoadColumns, loadTableFromDB and loadDataToTableView methods.
	*
	* @param arg0 the URL of the FXML file
	* @param arg1 the ResourceBundle containing localized resources 
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    mailCol.setStyle("-fx-alignment: BASELINE_CENTER");
	    logo.setImage(logoImage);
	    reffilling.setImage(reffill);
	    LoadColumns();
	    loadTableFromDB();
	    loadDataToTableView();
	}
	
	public void back(ActionEvent event) throws Exception {
	    // hide the current window
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    // Create new stage
	    Stage stage = new Stage();
	    // Create new instance of InventoryRefillingController
	    StoreEmployeeHomePageController storeEmployeeHomePageController = new StoreEmployeeHomePageController();
	    // Start the new stage
	    storeEmployeeHomePageController.start(stage);
	}

	/**
	* LoadColumns method is used to set the mailCol column of TableView mailTable
	* to be populated by the "msg" attribute of the MsgFromManger class
	*
	*/
	public void LoadColumns() {
	    mailCol.setCellValueFactory(new PropertyValueFactory<MsgFromManger,String>("msg"));
	}

	/**
	* loadTableFromDB method is used to load data from the database and 
	* store it in the ChatClient.item arraylist.
	* It sends a message to the server to get the data and handle it.
	*
	* @throws Exception
	*/
	public void loadTableFromDB() {
	    ArrayList<String> store = new ArrayList<>();
	    store.add("storeNeedsReffilingWorker");
	    try {
	        ClientUI.chat.accept(store);
	    }catch(Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	* loadDataToTableView method is used to load the data from the ChatClient.item arraylist 
	* and set it to the TableView, then it will be displayed on the scene.
	*
	*/
	public void loadDataToTableView() {
	    ArrayList<MsgFromManger> storesList = new ArrayList<>();
	    MsgFromManger temp;
	    for(int i=0;i<ChatClient.item.size();i++) {
	        temp= new MsgFromManger(ChatClient.item.get(i)+"'s stock needs to be refilled");
	        storesList.add(temp);
	    }
	    System.out.println("this is the data storeList:"+storesList);
	    msgList = FXCollections.observableArrayList(storesList);
	    mailTable.setItems(msgList);
	}

	/**
	 * The start method is responsible for displaying the inventory refilling scene on the primary stage.
	 *
	 * @param primaryStage the primary stage that the scene will be displayed on.
	 * @throws Exception 
	 */
	public void start(Stage primaryStage) throws Exception {   
	    Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/InventoryRefilling.fxml"));
	    Scene scene = new Scene(root);
	    primaryStage.setTitle("Inventory");
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
	 * Handles the submit button event.
	 * 
	 * @param event the ActionEvent object that is triggered when the submit button is clicked.
	 * @throws Exception 
	 */
	public void submit(ActionEvent event) throws Exception {
	    //String string;
	    try {
	    	// Get the selected index of the mailTable table view
	    	int selected = mailTable.getSelectionModel().getSelectedIndex();
	    	
	    	// Check if no option is selected
	    	if(selected == -1) {
	    		noOrderSelected.setVisible(true);
	    		noOrderSelected.setText("you must select an option from the table");
	    	} else {
	    		noOrderSelected.setVisible(false);
	    		
	    		// Get the selected message from the mailTable table view
	    		MsgFromManger msg = mailTable.getItems().get(selected);
	    		
	    		// Split the message to get the store location
	    		String[] parts = msg.getMsg().split("'s stock needs to be refilled");
	    		String storeLocation = parts[0];
	    		
	    		// Load the LocationStoreInventoryContoller.fxml scene
	    		FXMLLoader loader = new FXMLLoader();
	        	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	        	Stage primaryStage = new Stage();
	        	Pane root = loader.load(getClass().getResource("/clientGUI/LocationStoreInventoryContoller.fxml").openStream());
	        	LocationStoreInventoryContoller locationStoreInventoryContoller = loader.getController();
	        	
	        	// Pass the store location to the LocationStoreInventoryContoller controller
	        	locationStoreInventoryContoller.threshold(storeLocation);
	        	locationStoreInventoryContoller.loadWelcomeMsg(storeLocation);
	        	storeLocation=storeLocation.toLowerCase() + "items";
	        	//string=storeLocation.replaceAll("\\s+", "");
	        	locationStoreInventoryContoller.loadTableName(storeLocation.replaceAll("\\s+", ""));
	        	locationStoreInventoryContoller.loadTableFromDB();
	        	
	        	// Set the scene and show the primary stage
	        	Scene scene = new Scene(root);			
	        	primaryStage.setScene(scene);		
	        	primaryStage.show();
	        	
	        	// Close the application when the primary stage is closed
	        	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        		     @Override
	        		     public void handle(WindowEvent e) {
	        		      Platform.exit();
	        		      System.exit(0);
	        		     }
	        	});
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
