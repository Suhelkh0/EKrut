package clientGUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DiscountInitFirstPageController  implements Initializable { 
	@FXML
	private TextField OrderID;
	@FXML
	private ComboBox <String> chooseBranch;
	@FXML	                  
	private Label msg;
	private String chosen;
	public static String branchForDiscount;
	//public void 
	/**back to Marketing marketing home page
	 * 
	 * @throws Exception
	 */
	public void back() throws Exception {
		  MarketingManagerController aFrame = new MarketingManagerController();
		  aFrame.start((Stage) chooseBranch.getScene().getWindow());	
	}
	/** user choose the Item id and branch for the sale
	 * when the enter button is pressed then enter the discount page
	 * @param event
	 * @throws Exception
	 */
	
	public void EnterBTN(ActionEvent event) throws Exception {
		if(chooseBranch.getValue()==null) {
			msg.setText("please enter the branch");
		}
		ArrayList<String> list= new ArrayList<>();
		ChatClient.msgRecieved="";
		if(chooseBranch.getValue().equals("select branch")) {	
			msg.setText("please chose the branch");
			return;
		}
		if(OrderID.getText().isEmpty()) {
			msg.setText("please fill all the fields!");
			return;
		}
			list.add("discountEnterID");			
			list.add(OrderID.getText());
			list.add(chooseBranch.getValue()+"items");
	    	branchForDiscount=chooseBranch.getValue();
			ClientUI.chat.accept(list);	
		if(ChatClient.msgRecieved.equals("Order found")) {
			DiscountController aFrame = new DiscountController();
			  aFrame.start((Stage) OrderID.getScene().getWindow());
			}
		if(ChatClient.msgRecieved.equals("Order not found")) {
			msg.setTextFill(Color.RED);
			msg.setText("Item not found in store");
		}
	}
	/** 
	 * fill the combo box of display() with the branches
	 */
	public void display() {
		    chooseBranch.setValue("select branch");
	    	chooseBranch.getItems().addAll("abudhabi","dubai","haifa","karmiel","beersheva","eilat");	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	     display();
	}
	/** log out and return to the login page
	 * @param event the event that triggers this method.
	 * @throws Exception throw exception if loading the login page failed
	 */
	public void LogOutButtonOnAction(ActionEvent event) throws Exception {
		ArrayList<String> list = new ArrayList<>(); 
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) OrderID.getScene().getWindow());
	}
	/** start the page
	 * @param primaryStage
	 * @throws Exception
	 * 
	 */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/DiscountInitFirstPageForm.fxml"));
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
		   });}
}
