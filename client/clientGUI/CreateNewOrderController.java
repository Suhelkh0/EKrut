package clientGUI;

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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import logic.Item;
import logic.ItemInBill;
import logic.Price;
import logic.TimerTwoMinutes;

/**
 * 
 * @author Suhel
 * @author Amne
 *
 */
public class CreateNewOrderController implements Initializable {
	@FXML
	private TableView<Item> catalogTable;
	@FXML
	private TableView<Item> cartTable;
	@FXML
	private TableColumn<Item, ImageView> imageColumnCatalog;
	@FXML
	private TableColumn<Item, String> idColumnCatalog;
	@FXML
	private TableColumn<Item, String> nameColumnCatalog;
	@FXML
	private TableColumn<Item, Price> priceColumnCatalog;
	@FXML
	private TableColumn<Item, ImageView> imageColumnCart;
	@FXML
	private TableColumn<Item, String> idColumnCart;
	@FXML
	private TableColumn<Item, String> priceColumnCart;
	@FXML
	private Button add;
	@FXML
	private Button remove;
	@FXML
	private Button completeOrder;
	@FXML
	private Button RemoveAll;
	@FXML
	private Label totalPrice = new Label();
	@FXML
	private Label addWarning = new Label();
	@FXML
	private Label otherWarning = new Label();
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	@FXML
	private ImageView cart;
	Image cartImage = new Image(getClass().getResourceAsStream("cart2.png"));
	@FXML
	private ImageView addToCart;
	Image addToCartImage = new Image(getClass().getResourceAsStream("addToCart.png"));

	ArrayList<Item> itemsInCatalog = new ArrayList<>();
	ObservableList<Item> listCatalog;

	ArrayList<Item> itemsInCart = new ArrayList<>();
	ObservableList<Item> listCart;

	ArrayList<ItemInBill> itemsInCartWithoutDuplicates = new ArrayList<>();

	public static int newTotal = 0;

	/**
	 * The initialize method is used to initialize the elements of the FXML file and
	 * set default values for them. It sets the images for the cart and add to cart
	 * buttons, sets the alignment of the columns in the catalog and cart tables,
	 * sets the initial value for the total price, loads the columns for the catalog
	 * and cart tables, and starts a new thread for the TimerTwoMinutes class.
	 * 
	 * @param location  - URL object representing the location of the FXML file
	 * @param resources - ResourceBundle object used to set the resources for
	 *                  the FXML file  
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cart.setImage(cartImage);
		addToCart.setImage(addToCartImage);
		imageColumnCatalog.setStyle("-fx-alignment: BASELINE_CENTER");
		idColumnCatalog.setStyle("-fx-alignment: BASELINE_CENTER");
		nameColumnCatalog.setStyle("-fx-alignment: BASELINE_CENTER");
		priceColumnCatalog.setStyle("-fx-alignment: BASELINE_CENTER");
		priceColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		imageColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");
		idColumnCart.setStyle("-fx-alignment: BASELINE_CENTER");

		totalPrice.setText("0");
		LoadColumns();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}

	/**
	 * The LoadColumns method is used to load the columns of the catalog and cart
	 * tables with the appropriate values. It sets the cell value factory for the
	 * image, id, name, and price columns of the catalog table, and the image, id,
	 * and price columns of the cart table. The cell value factory binds the column
	 * to the corresponding property of the Item class.  
	 */
	public void LoadColumns() {
		imageColumnCatalog.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("image"));
		idColumnCatalog.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
		nameColumnCatalog.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		priceColumnCatalog.setCellValueFactory(new PropertyValueFactory<Item, Price>("PriceBeforeAndAfter"));
		imageColumnCart.setCellValueFactory(new PropertyValueFactory<Item, ImageView>("image"));
		idColumnCart.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
		priceColumnCart.setCellValueFactory(new PropertyValueFactory<Item, String>("preferedPrice"));
	}

	/**
	 * The addBtn method is used to add an item to the cart from the catalog table.
	 * It stops the current thread running the TimerTwoMinutes class, starts a new
	 * thread for the TimerTwoMinutes class, and adds the selected item from the
	 * catalog table to the cart table. It also sets the total price of the cart and
	 * shows a warning message if the item is not selected or the maximum amount of
	 * this item is reached.
	 * 
	 * @param event
	 * @throws Exception  
	 */
	public void addBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		try {
			int cntAppearance = 0, i;
			int selectedID = catalogTable.getSelectionModel().getSelectedIndex();
			if (selectedID == -1)
				addWarning.setText("You must choose an item");
			else {
				Item addedItem = catalogTable.getItems().get(selectedID);
				for (i = 0; i < itemsInCart.size(); i++) {
					if (itemsInCart.get(i).getId().equals(addedItem.getId())) {
						cntAppearance++;
					}
				}
				int maximumAmount = Integer.parseInt(addedItem.getAmount());
				if (cntAppearance < maximumAmount) {
					Item item = new Item(addedItem.getId(), addedItem.getImageName(),
							addedItem.getPriceBeforeAndAfter().getOriginalPrice(),
							addedItem.getPriceBeforeAndAfter().getPriceAfterDiscount(), addedItem.getAmount(),
							addedItem.getName());
					ItemInBill itemInBill = new ItemInBill(addedItem.getId(), addedItem.getImageName(),
							addedItem.getPriceBeforeAndAfter().getOriginalPrice(),
							addedItem.getPriceBeforeAndAfter().getPriceAfterDiscount(), addedItem.getAmount(),
							addedItem.getName(), 1);
					if (!itemsInCart.contains(item)) {
						itemsInCartWithoutDuplicates.add(itemInBill);
					} else {
						int index = itemsInCartWithoutDuplicates.indexOf(itemInBill);
						int oldQuantity = itemsInCartWithoutDuplicates.get(index).getQuantityInOrder();
						int oldTotalPrice = itemsInCartWithoutDuplicates.get(index).getTotalPrice();
						int currentAddedItemPrice = Integer.parseInt(item.getPreferedPrice());
						itemsInCartWithoutDuplicates.get(index).setQuantityInOrder(oldQuantity + 1);
						itemsInCartWithoutDuplicates.get(index).setTotalPrice(oldTotalPrice + currentAddedItemPrice);
					}
					itemsInCart.add(item);
					newTotal += Integer.parseInt(addedItem.getPreferedPrice());
					totalPrice.setText(Integer.toString(newTotal));
					addWarning.setText("");

				} else {
					addWarning.setText("There is only " + addedItem.getAmount() + " in the Stock!");
				}
				listCart = FXCollections.observableArrayList(itemsInCart);
				cartTable.setItems(listCart);
			}
			otherWarning.setText("");
		} catch (IndexOutOfBoundsException e) {

		}
	}

	/**
	 * The removeBtn method is used to remove an item from the cart table. It stops
	 * the current thread running the TimerTwoMinutes class, starts a new thread for
	 * the TimerTwoMinutes class, and removes the selected item from the cart table.
	 * It also sets the total price of the cart and shows a warning message if the
	 * item is not selected or there is no item in the cart.
	 * 
	 * @param event
	 * @throws Exception  
	 */
	public void removeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		try {
			if (itemsInCart.size() == 0)
				otherWarning.setText("There is no items in the cart");
			else {
				int selectedID = cartTable.getSelectionModel().getSelectedIndex();
				if (selectedID == -1)
					otherWarning.setText("You must choose an item");
				else {
					Item removedItem = cartTable.getItems().get(selectedID);
					ItemInBill itemInBill = new ItemInBill(removedItem.getId(), removedItem.getImageName(),
							removedItem.getPriceBeforeAndAfter().getOriginalPrice(),
							removedItem.getPriceBeforeAndAfter().getPriceAfterDiscount(), removedItem.getAmount(),
							removedItem.getName(), 1);

					int index = itemsInCartWithoutDuplicates.indexOf(itemInBill);
					int oldQuantity = itemsInCartWithoutDuplicates.get(index).getQuantityInOrder();
					if (oldQuantity == 1) {
						itemsInCartWithoutDuplicates.remove(index);
					} else {
						itemsInCartWithoutDuplicates.get(index).setQuantityInOrder(oldQuantity - 1);
						int oldTotalPrice = itemsInCartWithoutDuplicates.get(index).getTotalPrice();
						int currentRemovedItemPrice;
						currentRemovedItemPrice = Integer.parseInt(removedItem.getPreferedPrice());
						itemsInCartWithoutDuplicates.get(index).setTotalPrice(oldTotalPrice - currentRemovedItemPrice);
					}

					itemsInCart.remove(removedItem);
					cartTable.getItems().clear();
					listCart = FXCollections.observableArrayList(itemsInCart);
					cartTable.setItems(listCart);

					newTotal -= Integer.parseInt(removedItem.getPreferedPrice());
					totalPrice.setText(Integer.toString(newTotal));
					otherWarning.setText("");
				}
			}
			addWarning.setText("");
		} catch (IndexOutOfBoundsException e) {

		}
	}

	/**
	 *
	 * The completeOrderBtn method is used to complete an order by moving to the
	 * bill page. It stops the current thread running the TimerTwoMinutes class, and
	 * shows a warning message if the cart is empty. It loads the items in the cart
	 * and the total price in the bill page. It also check if the user is a
	 * subscriber or if it is the user's first payment, and if true, the user will
	 * receive 20% off the final price.
	 * 
	 * @param event - ActionEvent object that triggered the method call
	 * @throws Exception - in case of any error while completing the order
	 */
	public void completeOrderBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		if (itemsInCart.size() == 0) {
			otherWarning.setText("There is no items in the cart");
			ChatClient.thread = new Thread(new TimerTwoMinutes());
			ChatClient.thread.start();
		} else {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			ChatClient.primaryStage = primaryStage;
			Pane root = loader.load(getClass().getResource("/clientGUI/Bill.fxml").openStream());
			BillController billController = loader.getController();
			billController.loadTable(itemsInCart, itemsInCartWithoutDuplicates);
			String allItemsInOrder = "";
			for (int i = 0; i < itemsInCart.size(); i++) {
				allItemsInOrder += itemsInCart.get(i).getId();
				if (i < itemsInCart.size() - 1)
					allItemsInOrder += ",";
			}
			if (ChooseOrderTypeController.orderDetails.size() > 5) {
				int lastIndex = ChooseOrderTypeController.orderDetails.size() - 1;
				for (int i = 0; i < 2; i++) {
					ChooseOrderTypeController.orderDetails.remove(lastIndex - i);
				}
			}

			ChooseOrderTypeController.orderDetails.add(allItemsInOrder);

			ArrayList<String> checkIfFirstPayment = new ArrayList<>();
			checkIfFirstPayment.add("checkIfFirstPayment");
			checkIfFirstPayment.add(ChatClient.u1.getUserName());
			ClientUI.chat.accept(checkIfFirstPayment);
			if (ChatClient.firstPayment.equals("true")
					&& (ChatClient.u1.getRole().equals("Subscriber") || ChatClient.u1.getPermissions() != null)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Congratulations! you have 20% sale on your final price");
				alert.setContentText("You earned this sale only for your first payment");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				}
				int originalPrice = Integer.parseInt(totalPrice.getText());
				originalPrice = (int) (originalPrice * 80 / 100);
				billController.loadTotal(Integer.toString(originalPrice));
			} else {
				billController.loadTotal(totalPrice.getText());
			}
			billController.loadGivenCatalogItems(itemsInCatalog);
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
		}
		addWarning.setText("");
	}

	/**
	 * The removeAllBtn method handles the removal of all items in the cart. It
	 * first stops the thread and restarts it. Then, it checks if the cart is empty
	 * and displays a message if it is. If the cart is not empty, it prompts the
	 * user for confirmation before clearing the cart, resetting the list of items,
	 * and updating the total price.
	 * 
	 * @param event the event that triggers the method call
	 * @throws Exception if the thread is interrupted
	 */
	public void removeAllBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		if (itemsInCart.size() == 0) {
			otherWarning.setText("There is no items in the cart");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Are you sure you want to remove all the items?!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				cartTable.getItems().clear();
				itemsInCart = new ArrayList<>();
				itemsInCartWithoutDuplicates = new ArrayList<>();
				totalPrice.setText("0");
				newTotal = 0;
				otherWarning.setText("");
			}
		}
		addWarning.setText("");
	}

	/**
    *
	*The loadCart method loads the contents of a cart by updating the class level variables itemsInCart and itemsInCartWithoutDuplicates
	*with the provided ArrayLists, then sets the observable list of the cart table to the itemsInCart ArrayList and updates the cart table
	*with the new data.
	*@param itemsInCart an ArrayList of Item objects representing the items in the cart
	*@param itemsInCartNoDuplicates an ArrayList of ItemInBill objects representing the items in the cart without duplicates
	*/
	public void loadCart(ArrayList<Item> itemsInCart, ArrayList<ItemInBill> itemsInCartNoDuplicates) {
		this.itemsInCart = itemsInCart;
		this.itemsInCartWithoutDuplicates = itemsInCartNoDuplicates;
		listCart = FXCollections.observableArrayList(itemsInCart);
		cartTable.setItems(listCart);
	}

	/**
    *
	*The loadTotal method updates the total price of the cart.
	*It sets the text of the totalPrice label to the provided total and parse it to integer and assign it to newTotal variable.
	*@param total a string representing the total cost of the items in the cart
	*/
	public void loadTotal(String total) {
		this.totalPrice.setText(total);
		this.newTotal = Integer.parseInt(total);
	}

	/**
	*
	*The loadCatalogItemsFromDB method loads the items in the catalog by sending a request to the server to retrieve all items
	*from the database and filtering them by store location. It then creates Item objects from the returned data and adds them to
	*the itemsInCatalog ArrayList. The method also clears the catalog table and cart table, calls the LoadColumns method,
	*sets the observable list of the catalog table to the itemsInCatalog ArrayList and updates the catalog table with the new data.
	*@param storeLocation the location of the store for which the catalog should be loaded
	*/
	public void loadCatalogItemsFromDB(String storeLocation) {
		ArrayList<String> requestToGetItems = new ArrayList<>();
		requestToGetItems.add("getAllItems");
		requestToGetItems.add(storeLocation);
		if (ChatClient.u1.getPermissions() != null)
			requestToGetItems.add("Worker");
		else
			requestToGetItems.add(ChatClient.u1.getRole());
		requestToGetItems.add(ChatClient.u1.getStoreName());
		ClientUI.chat.accept(requestToGetItems);
		Item temp;
		for (int i = 0; i < ChatClient.items.size(); i += 6) {
			temp = new Item(ChatClient.items.get(i), ChatClient.items.get(i + 1), ChatClient.items.get(i + 2),
					ChatClient.items.get(i + 3), ChatClient.items.get(i + 4), ChatClient.items.get(i + 5));
			itemsInCatalog.add(temp);
		}
		catalogTable.getItems().clear();
		cartTable.getItems().clear();
		LoadColumns();
		listCatalog = FXCollections.observableArrayList(itemsInCatalog);
		catalogTable.setItems(listCatalog);
	}

	/**
	*
	*The loadGivenCatalogItems method loads a given catalog by updating the class level variable itemsInCatalog
	*with the provided ArrayList and then sets the observable list of the catalog table to the itemsInCatalog ArrayList
	*and updates the catalog table with the new data.
	*@param itemsInCatalog an ArrayList of Item objects representing the items in the catalog
	*/
	public void loadGivenCatalogItems(ArrayList<Item> itemsInCatalog) {
		this.itemsInCatalog = itemsInCatalog;
		listCatalog = FXCollections.observableArrayList(itemsInCatalog);
		catalogTable.setItems(listCatalog);
	}

	/**
	 * logOut method is used to log out the current user from the chat application.
	 * It stops the thread running the chat client and redirects the user to the logout page.
	 * @param event
	 * @throws Exception
	 */
	public void logOut(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.logOut(event);
	}

	/**
	 * homeBtn method is used to redirect the user to the home page of the chat application.
     * It stops the thread running the chat client and redirects the user to the home page.
	 * @param event
	 * @throws Exception
	 */
	public void homeBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		CostumerHomePageController.home(event);
	}
}
