package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
/**
 * 
 * @author Raeed Ataria
 *
 */
public class DiscountController implements Initializable {
	@FXML
	private TextField discount_name;
	@FXML
	private TextField saleEndDate;
	@FXML
	private TextField salePrice;
	@FXML
	private TextField SaleEndTime;
	@FXML
	private Text orderID;
	@FXML
	private Text orderName;
	@FXML
	private Text price;
	@FXML
	private Label msg;
	@FXML
	private TextField StartDate;
	@FXML
	private TextField StartTime;
	
	/**
	 * go back to the previous page (first page for discount)
	 */
	public void back() {
		DiscountInitFirstPageController aFrame8 = new DiscountInitFirstPageController();
		  try {
			aFrame8.start((Stage) SaleEndTime.getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
			msg.setText("Something went wrong");		
		}	
	}
	/** log out and return to the login page
	 * @param event the event that triggers this method
	 * @throws Exception throw exception if loading the login page failed
	 */
	public void LogOutButtonOnAction(ActionEvent event) throws Exception {
		ArrayList<String> list = new ArrayList<>(); 
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) StartDate.getScene().getWindow());
	}
	/** get all the sale template from the user, enter the template to the DB in the discounts table
	 * @param event the event that triggers this method.
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	public void discountButtonOnAction(ActionEvent event) throws IOException, InterruptedException {
		ArrayList<String> list = new ArrayList<>();
		ChatClient.msgRecieved = "";
		if (discount_name.getText().isEmpty() || saleEndDate.getText().isEmpty() || salePrice.getText().isEmpty()
				|| SaleEndTime.getText().isEmpty() || StartDate.getText().isEmpty() || StartTime.getText().isEmpty()) {
			msg.setText("please fill all the fields!");
			return;
		} else if (salePrice.getText().matches("[0-9]+") == false || Integer.parseInt(salePrice.getText()) < 0) {
			msg.setText("The price has to be a number bigger than zero");
		} else {
			// check if the sale price is less than the actaul price
			if (Integer.parseInt(ChatClient.itemForDiscount.getOriginalPrice()) <= Integer
					.parseInt(salePrice.getText())) {
				msg.setText("the sale price has to be less than the actaul price");
				return;
			}
			// check if the EndDate date is valid
			LocalDate date1 = null, date2 = null;
			try {
				date1 = LocalDate.parse(saleEndDate.getText());
				LocalDate currentDate = LocalDate.now();
				if (currentDate.isAfter(date1)) {
					msg.setText("End Date to be\\n greater than the current date! ");
					return;
				}
			} catch (DateTimeParseException e) {
				System.out.println(date1);
				msg.setText("EndDate is not a valid date");
				return;
			}
			// check if the SatartDate date is valid
			try {
				date2 = LocalDate.parse(StartDate.getText());
				LocalDate currentDate = LocalDate.now();
				if (currentDate.isAfter(date2) ) {
					msg.setText("Start Date has to be\n greater than the current date!");
					return;
				}
			} catch (DateTimeParseException e) {
				System.out.println(date2);
				msg.setText("startDate is not a valid date");
				return;
			}
			// check time
			if (checkTime(SaleEndTime.getText()) == false || checkTime(StartTime.getText()) == false)
				return;
			// check if the end date is greater or equals to current date
			if (date2.isAfter(date1)) {
				msg.setText("End date has to be greater than or equals Start date ");
				return;
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Discount Confirmation Alert");
			alert.setContentText("Do you want to CONFIRM the discount");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				list.add("discount");
				list.add(discount_name.getText());
				list.add(salePrice.getText());
				list.add(saleEndDate.getText());
				list.add(SaleEndTime.getText());
				list.add(ChatClient.itemForDiscount.getId());
				list.add(StartDate.getText());
				list.add(StartTime.getText());
				list.add(DiscountInitFirstPageController.branchForDiscount + "items");
				ClientUI.chat.accept(list);
			} else {
				return;
			}
		}
		if (ChatClient.msgRecieved.equals("discount was updated successfully")) {
			msg.setTextFill(Color.ORANGE);
			msg.setText("Discount was updated successfully");
		}
		if (ChatClient.msgRecieved.equals("Order not found")) {
			msg.setTextFill(Color.RED);
			msg.setText("There is a discount with for these item\n with the same name");
		}
	}
	/** check if the time is valid
	 * @param input the date that wanted to be checked
	 * @return return true if it is valid else return false
	 */
	private boolean checkTime(String input) {
		String[] timeParts = input.split(":");
		if (timeParts.length == 2) {
			try {
				int hour = Integer.parseInt(timeParts[0]);
				int minute = Integer.parseInt(timeParts[1]);
				if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
					return true;
				} else {
					msg.setText("please enter a valid time");
				}
			} catch (NumberFormatException e) {
				msg.setText("please enter a valid time");
			}
		} else {
			msg.setText("please enter a valid time");
		}
		return false;
	}
/**start the window page
 * @param primaryStage
 * @throws Exception
 * 
 */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/DiscountForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);	
		primaryStage.show();
		//when press on X then terminate the procces 
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		     @Override
		     public void handle(WindowEvent e) {
		      Platform.exit();
		      System.exit(0);
		     }
		   });
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderID.setText(ChatClient.itemForDiscount.getId());
		orderName.setText(ChatClient.itemForDiscount.getName());
		price.setText(ChatClient.itemForDiscount.getOriginalPrice());
	}
	

}
