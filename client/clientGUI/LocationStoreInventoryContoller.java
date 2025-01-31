package clientGUI;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Item;
/**
 * 
 * @author danial
 *
 */
public class LocationStoreInventoryContoller implements Initializable {

	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private Label welcomeMsg;
	@FXML
	private Button backBtn = null;
	@FXML
	private Button saveBtn = null;
	@FXML
	private TableView<Item> tableItems;
	private String tableName;
	@FXML
	private Label msgDisplay;
	@FXML
	private Label errorMsg;
	@FXML
	private Label thresholdLabel;
	@FXML
	private TableColumn<Item, String> col_Id;
	@FXML
	private TableColumn<Item, String> col_Amount;
	ObservableList<Item> iList;
	int thresholdNum;

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	/**
	 * Method to handle the back button press.
	 * 
	 * @param event - The action event that triggered the method call.
	 * @throws Exception - If there is any exception while creating the new stage.
	 */
	public void back(ActionEvent event) throws Exception {
	    // hide the current window
	    ((Node) event.getSource()).getScene().getWindow().hide();
	    // Create new stage
	    Stage stage = new Stage();
	    // Create new instance of InventoryRefillingController
	    InventoryRefillingController inventory = new InventoryRefillingController();
	    // Start the new stage
	    inventory.start(stage);
	}


	/**
	 * Method to initialize the components of the scene.
	 * 
	 * @param url - The location used to resolve relative paths for the root object, or null if the location is not known
	 * @param rb - The resources used to localize the root object, or null if the root object was not localized
	 */
	 @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // Set the alignment of the ID column to center
	        col_Id.setStyle("-fx-alignment: BASELINE_CENTER");
	        col_Amount.setStyle("-fx-alignment: BASELINE_CENTER");
	        // set the logo image
	        logo.setImage(logoImage);
	        LoadColumns();
	    }


	/**
	 * Method to load the columns for the table view.
	 */
	public void LoadColumns() {
	    // Allow the table to be editable
	    tableItems.setEditable(true);
	    // Set the cell value factory for the ID column
	    col_Id.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
	    // Set the cell value factory for the Amount column
	    col_Amount.setCellValueFactory(new PropertyValueFactory<Item, String>("amount"));
	    // Set the cell factory for the Amount column
	    col_Amount.setCellFactory(TextFieldTableCell.forTableColumn());
	    // Set the edit commit event for the Amount column
	    col_Amount.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
	        @Override
	        public void handle(CellEditEvent<Item, String> event) {
	            Item item = event.getRowValue();
	            item.setAmount(event.getNewValue());
	        }
	    });
	}


	/**
	 * Method to set the table name for the current instance.
	 * 
	 * @param tableName - The name of the table being loaded.
	 */
	public void loadTableName(String tableName) {
	    // Set the table name for the current instance
	    this.tableName = tableName;
	}


	/**
	 * Method to load the welcome message and threshold label for a store.
	 * 
	 * @param location - The name of the store for which the message and threshold are being displayed.
	 */
	public void loadWelcomeMsg(String location) {
	    // Set the text of the welcome message label
	    welcomeMsg.setText("Welcome to " + location + "'s inventory");
	    // Set the text of the threshold label
	    thresholdLabel.setText("This is " + location + "'s threshold: " + thresholdNum);
	}


	/**
	 * Method to update the threshold for a store.
	 * 
	 * @param store - The name of the store for which the threshold is being updated.
	 */
	public void threshold(String store) {
	    ArrayList<String> thresholdList = new ArrayList<>();
	    thresholdList.add("thresholdUpdate");
	    thresholdList.add(store);
	    try {
	        ClientUI.chat.accept(thresholdList);
	        Integer.parseInt(ChatClient.item.get(0));
	        thresholdNum = Integer.parseInt(ChatClient.item.get(0));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	/**
	 * Method to load the table from the database.
	 */
	public void loadTableFromDB() {
	    ArrayList<Item> itemsList = new ArrayList<>();
	    ArrayList<String> store = new ArrayList<>();
	    store.add(tableName);
	    try {
	        ClientUI.chat.accept(store);
	        Item temp;
	        for (int i = 0; i < ChatClient.item.size(); i += 2) {
	            temp = new Item(ChatClient.item.get(i), null, null, null, ChatClient.item.get(i + 1), null);

	            // Check if the amount of the item is below the threshold
	            if (Integer.parseInt(temp.getAmount()) <= thresholdNum) {
	                System.out.println(thresholdNum);
	                itemsList.add(temp);
	            }
	        }
	        iList = FXCollections.observableArrayList(itemsList);
	        tableItems.setItems(iList);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	* saveTable method is used to save the changes made to the tableView in the database
	* It gets the rows of the table, creates an ArrayList of strings,
	* adds the command "UpdateInventory" and the table name to the ArrayList,
	* and for each row in the table, it adds the item's ID and amount to the ArrayList.
	* Then it sends the ArrayList to the server via the chat client.
	* If a negative number or an invalid number is entered, it will display an error message.
	*
	*/
	public void saveTable() {
		ObservableList<Item> rows = tableItems.getItems();
		try {
			ArrayList<String> tableViewList = new ArrayList<>();
			tableViewList.add("UpdateInventory");
			tableViewList.add(tableName);
			for (Item item : rows) {
				String id = item.getId();
				if (Integer.parseInt(item.getAmount()) < 0) {
					throw new NumberFormatException("Negative number");
				}
				String amount = item.getAmount();

				tableViewList.add(id);
				tableViewList.add(amount);
			}
			msgDisplay.setVisible(true);
			errorMsg.setVisible(false);
			ClientUI.chat.accept(tableViewList);
			msgDisplay.setText("Stock Updated Successfully");
			System.out.println(tableViewList);
		} catch (NumberFormatException e) {
			if (e.getMessage().equals("Negative number")) {
				msgDisplay.setVisible(false);
				errorMsg.setVisible(true);
				errorMsg.setText("you cannot enter a negative number");
			} else {
				errorMsg.setVisible(true);
				errorMsg.setText("you must enter a valid number");
				msgDisplay.setVisible(false);
			}
		}
		tableItems.getItems().clear();
		loadTableFromDB();
		if (iList.isEmpty()) {
			ArrayList<String> stockReffiling = new ArrayList<>();
			stockReffiling.add("stockReffiled");
			stockReffiling.add(tableName);
			try {
				ClientUI.chat.accept(stockReffiling);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}