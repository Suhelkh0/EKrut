package client;
import javafx.application.Application;

import javafx.stage.Stage;
import logic.Costumer;

import java.util.ArrayList;
import java.util.Vector;
//import gui.SubscriberInfoFormController;
import client.ClientController;
import clientGUI.ChooseOrderTypeController;
import clientGUI.CostumerHomePageController;
import clientGUI.InventoryRefillingController;
import clientGUI.LocationStoreInventoryContoller;
import clientGUI.MainPageRemoteOrderController;
import clientGUI.ManagerCostumerApproval;
import clientGUI.ManagerPageController;
import clientGUI.RegionalShippingOperatorContoller;
import clientGUI.UsersController;
//import gui.EkrutFrameController;
import clientGUI.logInController;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 /*chat= new ClientController("localhost",5555);
		 ArrayList<String> newClient = new ArrayList<>();
		 newClient.add("newClient");
		 ClientUI.chat.accept(newClient);*/
		ClientHomePgaeController aFrame = new ClientHomePgaeController(); // create EkrutFrame
		//logInController aFrame = new logInController(); // create EkrutFrame
		//UsersController aFrame = new UsersController(); // create EkrutFrame
		//ManagerPageController aFrame= new ManagerPageController();
		 //InventoryRefillingController aFrame = new InventoryRefillingController();
		 //OrderConfirmation aFrame = new OrderConfirmation();
		 //RegionalShippingOperatorContoller aFrame = new RegionalShippingOperatorContoller();
		 //ManagerPageController aFrame= new ManagerPageController();
		aFrame.start(primaryStage);
		
	}
	
	
}
