package clientGUI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Item;
import logic.ItemInBill;
import logic.TimerTwoMinutes;

/**
 * 
 * @author Suhel
 * @author Amne
 *
 */

public class PaymentController {
	@FXML
	private TextField Credit_Card_Number = new TextField();
	@FXML
	private TextField Expire_Date = new TextField();
	@FXML
	private TextField CVV = new TextField();
	@FXML
	private TextField email = new TextField();
	@FXML
	private Button placeOrder;
	@FXML
	private Button logOutBtn;
	@FXML
	private Button homeBtn;
	@FXML
	private Label errorOneFieldIsEmpty;
	@FXML
	private Label deffered;
	@FXML
	private ComboBox<String> defferedPaymentBox = new ComboBox<>();
	@FXML
	private ImageView logo;
	Image logoImage = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	@FXML
	private ImageView visa;
	Image visaImage = new Image(getClass().getResourceAsStream("visa.png"));
	
	ObservableList<String> defferedPayment = FXCollections.observableArrayList("Yes", "No");
	
	/**
	 * initializing all the required data for this class
	 */
	@FXML
	private void initialize() {
		logo.setImage(logoImage);
		visa.setImage(visaImage);
		defferedPaymentBox.setItems(defferedPayment);
		defferedPaymentBox.setVisible(false);
		deffered.setVisible(false);
		if(ChatClient.u1.getRole().equals("Subscriber") || ChatClient.u1.getPermissions()!=null) {
			defferedPaymentBox.setVisible(true);
			deffered.setVisible(true);
		}
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
	}
	
	/**
	 * placing the order if the order has passed all the tests about it's validity 
	 * and availability including valid inputs and that the order can be placed in DB
	 * @param event
	 * @throws Exception
	 */
	public void placeOrderBtn(ActionEvent event) throws Exception {
		ChatClient.thread.stop();
		ChatClient.thread = new Thread(new TimerTwoMinutes());
		ChatClient.thread.start();
		LocalDate currentDate = LocalDate.now();
	    String dateString = currentDate.toString();
	    LocalDate tenDays=currentDate.plusDays(10);
	    String estimatedDate=tenDays.toString();
		if(email.getText().trim().isEmpty()) {
			errorOneFieldIsEmpty.setText("One field is empty!");
		}
		else if(!(email.getText().trim().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))) {
			errorOneFieldIsEmpty.setText("Unvalid email");	
		}
		else if(defferedPaymentBox.getValue() == null && ((ChatClient.u1.getRole().equals("Subscriber") || ChatClient.u1.getPermissions()!=null))) {
			errorOneFieldIsEmpty.setText("You must to choose if to deffer the payment!!!");
			return;
		}
		
		else {
			ChatClient.thread.stop();
			ArrayList<String> requestToGetItems = new ArrayList<>();
			requestToGetItems.add("getAllItems");
			requestToGetItems.add(MainPageRemoteOrderController.storeLocation);
			if(ChatClient.u1.getPermissions() != null)
				requestToGetItems.add("Worker");
			else
				requestToGetItems.add(ChatClient.u1.getRole());
			requestToGetItems.add(ChatClient.u1.getStoreName());
			//getting the updated Stock
			ClientUI.chat.accept(requestToGetItems);
			
			ArrayList<Item> updatedtItemsInCatalog = new ArrayList<>();
			for(int i=0;i<ChatClient.items.size();i+=6) {
				Item temp = new Item(ChatClient.items.get(i),ChatClient.items.get(i+1),ChatClient.items.get(i+2),ChatClient.items.get(i+3),ChatClient.items.get(i+4), ChatClient.items.get(i+5));
				updatedtItemsInCatalog.add(temp);
			}
			ArrayList<ItemInBill> currentItemsInCatalogWithNoDuplicates = new ArrayList<>();
			int i;
			for(i=0; i<updatedtItemsInCatalog.size() ; i++) {
				Item tempItem = updatedtItemsInCatalog.get(i);
				ItemInBill tempItemInBill = new ItemInBill(tempItem.getId(), tempItem.getImageName(), tempItem.getPriceBeforeAndAfter().getOriginalPrice(), tempItem.getPriceBeforeAndAfter().getPriceAfterDiscount(), tempItem.getAmount(), tempItem.getName(), Integer.parseInt(tempItem.getAmount()));
				currentItemsInCatalogWithNoDuplicates.add(tempItemInBill);
			}
			
			//if one of the chosen items cannot be ordered
			for(ItemInBill itemInBillInCart : BillController.itemsInCartWithoutDuplicates) {
				//if the item itself is not anymore available
				if(!currentItemsInCatalogWithNoDuplicates.contains(itemInBillInCart)) {
					soldOutItems(event);
					return;
				}
				else {
					//if the chosen quantity of item is not available in DB
					int index = currentItemsInCatalogWithNoDuplicates.indexOf(itemInBillInCart);
					if(currentItemsInCatalogWithNoDuplicates.get(index).getQuantityInOrder() < itemInBillInCart.getQuantityInOrder()) {
						soldOutItems(event);
						return;
					}
				}
			}
			
			//asking for client's approval 
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Are you sure?");
			alert.setContentText("If you pressed OK you cannot cancel your order!");
			Optional <ButtonType> result = alert.showAndWait();
			if(result.isPresent() && result.get() == ButtonType.OK) {
				
				//placing the order in DB and updating needed information about threshold
				ArrayList<String> orderInfoToUpdateStock = new ArrayList<>();
				orderInfoToUpdateStock.add("UpdateAmountItemsAndCheckThreshold");
				String branch = ChooseOrderTypeController.orderDetails.get(4);
				orderInfoToUpdateStock.add(branch);
				for(ItemInBill itemInBill : BillController.itemsInCartWithoutDuplicates) {
					orderInfoToUpdateStock.add(itemInBill.getId());
					int index = currentItemsInCatalogWithNoDuplicates.indexOf(itemInBill);
					int amountInDB = Integer.parseInt(currentItemsInCatalogWithNoDuplicates.get(index).getAmount());
					amountInDB-= itemInBill.getQuantityInOrder();
					orderInfoToUpdateStock.add(Integer.toString(amountInDB));
				}
				ClientUI.chat.accept(orderInfoToUpdateStock);
				
				//placing the order details
				alert = new Alert(AlertType.INFORMATION);
				if(ChooseOrderTypeController.ordertype.equals("Immediate Order")) {
					ChooseOrderTypeController.orderDetails.add(null);
					addOrderDetails(ChooseOrderTypeController.ordertype, dateString, estimatedDate);
					ChooseOrderTypeController.orderDetails.add("Not Pick up later");
					ChooseOrderTypeController.orderDetails.add(null);
					ChooseOrderTypeController.orderDetails.add(Month.of(LocalDate.now().getMonthValue()).name().toLowerCase());
					ChooseOrderTypeController.orderDetails.add(Integer.toString(LocalDate.now().getYear()));
					ClientUI.chat.accept(ChooseOrderTypeController.orderDetails);
					alert.setHeaderText("Your Order has been successfully placed, Thank you");
				}
				if(ChooseOrderTypeController.ordertype.equals("Remote Order")) {
					alert.setTitle("Simulation");
					if(ChooseMethodController.remoteMethod.equals("Pick up later"))
						ChooseOrderTypeController.orderDetails.add(null);
					addOrderDetails(ChooseMethodController.remoteMethod, dateString, estimatedDate);
					if(ChooseMethodController.remoteMethod.equals("Pick up later")) {
						ChooseOrderTypeController.orderDetails.add("Pick up later");
						ChooseOrderTypeController.orderDetails.add("false");
					}
					else {
						ChooseOrderTypeController.orderDetails.add("Not Pick up later");
						ChooseOrderTypeController.orderDetails.add(null);
					}
					ChooseOrderTypeController.orderDetails.add(Month.of(LocalDate.now().getMonthValue()).name().toLowerCase());
					ChooseOrderTypeController.orderDetails.add(Integer.toString(LocalDate.now().getYear()));
					ClientUI.chat.accept(ChooseOrderTypeController.orderDetails);
					if(ChooseMethodController.remoteMethod.equals("Drone Delivery")) {
						alert.setHeaderText("Your Order has been successfully received");
						alert.setContentText("Your request was successfully received and is waiting for approving, you will get an email in " + email.getText() + " when your order is approved with estimated delivery date and time. Thank you!");
					}
					if(ChooseMethodController.remoteMethod.equals("Pick up later")) {
						alert.setHeaderText("Your Order has been successfully received");
						alert.setContentText("You have to insert the following code when you want to pick up your order. Code: " + ChatClient.orderCode);
					}
				}
				//updating the firstPayment flag
				if((ChatClient.u1.getRole().equals("Subscriber") || ChatClient.u1.getPermissions()!=null) && ChatClient.firstPayment.equals("true")) {
					ArrayList<String> updateFirstPayment = new ArrayList<>();
					updateFirstPayment.add("updateFirstPayment");
					updateFirstPayment.add(ChatClient.u1.getUserName());
					ClientUI.chat.accept(updateFirstPayment);
				}
				result = alert.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK)
					homeBtn(event);
			}
			else {
				ChatClient.thread = new Thread(new TimerTwoMinutes());
				ChatClient.thread.start();
			}
		}
	}
	
	/**
	 * display message if the order cannot be placed, and change to proper page
	 * @param event
	 * @throws Exception
	 */
	private void soldOutItems(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Placing your order has been failed");
		alert.setContentText("We are sorry, but some of your chosen items have been sold out! Do you want to order again?");
		Optional <ButtonType> result = alert.showAndWait();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		if(result.isPresent() && result.get() == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader();
			Stage primaryStage = new Stage();
			ChatClient.primaryStage = primaryStage;
			Pane root = loader.load(getClass().getResource("/clientGUI/CreateNewOrder.fxml").openStream());
			CreateNewOrderController createNewOrderController = loader.getController();
			createNewOrderController.loadCatalogItemsFromDB(MainPageRemoteOrderController.storeLocation);
			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else {
			Stage primaryStage = new Stage();
			CostumerHomePageController costumerHomePageController = new CostumerHomePageController();
			costumerHomePageController.start(primaryStage);
		}
	}
	
	/**
	 * load the shown information to the client
	 * @param costumerInfo
	 */
	public void loadCustomerInfo(ArrayList<String> costumerInfo){
		System.out.println(costumerInfo + " costumerInfo");
		Credit_Card_Number.setText(costumerInfo.get(5));
		Credit_Card_Number.setEditable(false);
		Expire_Date.setText(costumerInfo.get(6));
		Expire_Date.setEditable(false);
		CVV.setText(costumerInfo.get(7));
		CVV.setEditable(false);
		email.setText(ChatClient.u1.getEmail());
	}
	
	/**
	 * adding the details of the order
	 * @param orderMethod
	 * @param dateString
	 * @param estimatedDate
	 */
	private void addOrderDetails(String orderMethod, String dateString, String estimatedDate){
		ChooseOrderTypeController.orderDetails.add(orderMethod);
		ChooseOrderTypeController.orderDetails.add("Approved");
		ChooseOrderTypeController.orderDetails.add(dateString);
		ChooseOrderTypeController.orderDetails.add(estimatedDate);
		ChooseOrderTypeController.orderDetails.add("Not Received");
		ChooseOrderTypeController.orderDetails.add("PENDING");
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
