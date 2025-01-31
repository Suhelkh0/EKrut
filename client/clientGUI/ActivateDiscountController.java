package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import javafx.application.Application;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * 
 * @author Raeed Ataria
 *
 */
public class ActivateDiscountController implements Initializable  {
	@FXML 
	private Button submitButton;
	@FXML 
	private TextField DiscounID;
	@FXML
	private Text choseDiscountText;
	@FXML 
	private Label msgUI;
	@FXML 
	private ComboBox choseDiscountName;
	@FXML 
	private ComboBox choseBranch;
	int flag=0,flag1=0;
	/**back to Marketing Employee home page
	 * 
	 * @throws Exception
	 */
	public void back() throws Exception {
		MarketingEmployeeController aFrame = new MarketingEmployeeController();
		  aFrame.start((Stage) DiscounID.getScene().getWindow());	
	}
	/** Activate the discount 
	 * the user choose the discount name and the branch
	 * @throws Exception 
	 */

	public void activateDiscount() {
		msgUI.setTextFill(Color.RED);
		if(choseDiscountName.getValue()==null) {
			msgUI.setText("choose a discount name");
			return;
		}
		if(choseBranch==null) {
			msgUI.setText("choose a branch");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Discount Confirmation Alert");
		alert.setContentText("Do you want to CONFIRM the discount");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			ArrayList<String> msg=new ArrayList<>();
			msg.add("activateDiscount");
			msg.add(DiscounID.getText());
			msg.add((String)choseDiscountName.getValue());
			msg.add((String) choseBranch.getValue()+"items");
			ClientUI.chat.accept(msg);
		}
		else {
			 return;
		}
		if(ChatClient.msgRecieved.equals("Error")) {
			msgUI.setText("The chosen item doesn`t \n exist in the store");
		}
		else {
			msgUI.setTextFill(Color.ORANGE);
			msgUI.setText("The discount was successfully activated");
		}
	}
	/** search for the existing discount that were approved by the manger 
	 * @param event the event that triggers this method.
	 */
	public void actionOnSearchForDiscountBTN(ActionEvent event) {
		choseDiscountName.getItems().clear();
		if(choseBranch==null) {
			msgUI.setText("choose a branch");
			return;
		}
		msgUI.setTextFill(Color.RED);
		String []array=new String[1000];
		if(DiscounID.getText().isEmpty())
		{
			msgUI.setText("please enter the snack id");
			choseDiscountName.setVisible(false);
			choseDiscountText.setVisible(false);
			submitButton.setVisible(false);		
			return;
		}
		else {
			if(choseBranch.getValue()==null)
			{
				msgUI.setTextFill(Color.RED);
				msgUI.setText("please choose the branch");
			}
			msgUI.setText("");
			ArrayList<String> msg=new ArrayList<>();
			msg.add("search for discounts");
			msg.add(DiscounID.getText());
			msg.add((String) choseBranch.getValue()+"items");
			ClientUI.chat.accept(msg); 
			for(int i=0;i<ChatClient.discountNamesList.size();i++) {
				array[i]=ChatClient.discountNamesList.get(i);
			}
			if(ChatClient.discountNamesList.size()==0) {
				msgUI.setText("there are no discounts for this item");
				hide();				
				return;
			}
			//make them visible 
			choseDiscountName.setVisible(true);
			choseDiscountText.setVisible(true);
			submitButton.setVisible(true);
			choseDiscountName.getItems().addAll(ChatClient.discountNamesList);			
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		hide();	
	}
	/** 
	 * set the branches combo box according to to what area the worker responsible for
	 * set the choseDiscountName and choseDiscountText as false
	 */
	public void hide() {
		choseDiscountName.setVisible(false);
		choseDiscountText.setVisible(false);
		submitButton.setVisible(false);	
		if(flag1==0) {
			flag1=1;
		 if(ChatClient.u1.getStoreName().equals("abudhabi")||ChatClient.u1.getStoreName().equals("dubai")) {
			 choseBranch.getItems().addAll("abudhabi","dubai");
		    }
		    if(ChatClient.u1.getStoreName().equals("haifa") ||ChatClient.u1.getStoreName().equals("karmiel")) {
		    	choseBranch.getItems().addAll("haifa","karmiel");
		    }
		    if(ChatClient.u1.getStoreName().equals("beersheva")||ChatClient.u1.getStoreName().equals("eilat")){
		    	choseBranch.getItems().addAll("beersheva","eilat");		    	
		    	
		    }}	
		
	}
	/** log out and return to the login page
	 * @param event the action event
	 * @throws Exception throw exception if loading the login page failed
	 */
	public void LogOutButtonOnAction(ActionEvent event) throws Exception {
		ArrayList<String> list = new ArrayList<>(); 
		ChatClient.msgRecieved = "";
		list.add("logOut");
		ClientUI.chat.accept(list);
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		logInController aFrame = new logInController(); // create EkrutFrame
		aFrame.start((Stage) choseDiscountText.getScene().getWindow());
	}
	/** start the stage
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/ActivateDiscountForm.fxml"));
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
}
