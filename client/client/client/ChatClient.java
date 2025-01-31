// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Costumer;
import logic.Threshold;
import ocsf.client.*;
import client.*;
import common.ChatIF;
import logic.Client;
import logic.Item;
import logic.MyFile;
import logic.Costumer;
import logic.User;

import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author suhel
 * @author amne
 * @author qamar
 * @author daniel
 * @author raed
 * @author shokry
 *
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	// Raeed
	public static boolean contenue = false;
	public static Thread thread;
	public static Stage primaryStage;
	public static User u1 = new User(null, null, null, null, null, null, null, null);
	public static Item itemForDiscount;
	public static ArrayList<String> discountNamesList = new ArrayList<>();
	public static ArrayList<String> items = new ArrayList<>();
	public static ArrayList<String> costumerInfo = new ArrayList<>();
	public static String firstPayment;
	public static String orderCodeMsg;
	public static String orderCode;
	public static boolean awaitResponse = false;
	public static String msgRecieved;
	public static Costumer s1 = new Costumer(null, null, null, null, null, null, null);
	public static String msgRecived;
	public static ArrayList<String> waitingForApproval = new ArrayList<>();
	public static String subscriberNumber;
	public static String data;
	public static int flag = 0;
	public static ArrayList<String> report = new ArrayList<>();
	public static String amount;
	public static String income;
	public static String pickUpMethod;
	public static String droneDeliveryMethod;
	public static String immediateMethod;
	public static final Threshold t1 = new Threshold(null, null);
	public static ArrayList<String> item;// dani

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();

	}

	@Override
	public void connectionClosed() {

		try {
			sendToServer("dis");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Connection to server closed.");
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.println("--> handleMessageFromServer");
		msgRecieved = "";
		if (msg instanceof ArrayList) {
			ArrayList<Object> infoObject = (ArrayList<Object>) msg;
			if (infoObject.get(0) instanceof String) {
				ArrayList<String> info = new ArrayList<>();
				for (int i = 0; i < infoObject.size(); i++) {
					info.add((String) infoObject.get(i));
				}
				///////// suhel///////////
				if (info.get(0).equals("getAllItems")) {
					info.remove(0);
					items = info;
				}
				if (info.get(0).equals("costumerInfo")) {
					info.remove(0);
					costumerInfo = info;
				}
				/////// suhel+Qamar/////////////
				if (info.get(0).equals("Stock reports")) {
					if (info.get(1).equals("NoReport")) {
						msgRecived = "NotReport";
					} else {
						report.clear();
						report.add(info.get(1));
						report.add(info.get(2));
						report.add(info.get(3));
						report.add(info.get(4));
						report.add(info.get(5));
						report.add(info.get(6));
						report.add(info.get(7));
						report.add(info.get(8));

						msgRecived = "Report";

					}
					awaitResponse = false;

				}

				if (info.get(0).equals("Costumer reports")) {
					if (info.get(1).equals("No report")) {
						msgRecived = "NotReport";
					} else {
						report.clear();
						report.add(info.get(1));
						report.add(info.get(2));
						report.add(info.get(3));
						report.add(info.get(4));
						report.add(info.get(5));
						report.add(info.get(6));
						report.add(info.get(7));
						report.add(info.get(8));

						msgRecived = "Report";

					}
					awaitResponse = false;

				}
				////////// raed///////////
				if (info.get(0).equals("login")) {

					if (info.get(1).equals("NotFound"))
						msgRecieved = "username or password are not correct try again!";
					if (info.get(1).equals("user Already logged in")) {
						msgRecieved = "user Already logged in";
					}
					if (msgRecieved.equals("")) {
						u1.setFirstName(info.get(1));
						u1.setLastName(info.get(2));
						u1.setId(info.get(3));
						u1.setRole(info.get(4));
						u1.setStoreName(info.get(5));
						u1.setUserName(info.get(6));
						u1.setEmail(info.get(7));
						u1.setPhoneNumber(info.get(8));
						u1.setPermissions(info.get(9));
					}
				}
				// raeed 2
				/*
				 * handle the discount of the itemID
				 */
				if (info.get(0).equals("search for discounts")) {
					discountNamesList.clear();
					for (int k = 1; k < info.size(); k++)
						discountNamesList.add(info.get(k));

				}
				// raeed2
				/*
				 * get the item that wanted to be discounted
				 */
				if (info.get(0).equals("discountEnterID")) {
					if (info.get(1).equals("false")) {
						msgRecieved = "Order not found";
					} else {
						itemForDiscount = new Item(info.get(2), info.get(3), info.get(4));
						msgRecieved = "Order found";
					}
				}
				if (info.get(0).equals("discount")) {
					if (info.get(1).equals("updated")) {
						msgRecieved = "discount was updated successfully";
					} else {
						msgRecieved = "Order not found";
					}
				}
				// handle Activate Discount
				if (info.get(0).equals("activateDiscount")) {
					if (info.get(1).equals("Error")) {
						msgRecieved = "Error";
					} else {
						msgRecieved = "OK";
					}
				}
				/*
				 * if (info.get(0).equals("discount")) {
				 * 
				 * if (info.get(1).equals("Found")) {
				 * System.out.println("The the discount is already in DB"); msgRecieved =
				 * "The the discount is already in DB"; } else { msgRecieved = "OK"; } }
				 */

				// handle Activate Discount
				/*
				 * if (info.get(0).equals("ActivateDiscount")) { if
				 * (info.get(0).equals("Error")) { msgRecieved = "Error";
				 * 
				 * } else { msgRecieved = "OK"; } }
				 */
				////// qamar
				if (info.get(0).equals("NotFound")) {
					msgRecived = "NotFound";
					awaitResponse = false;

				}

				if (info.get(0).equals("Orders_report")) {
					if (info.get(1).equals("NoReport")) {
						msgRecived = "NotReport";
					} else {
						report.clear();
						msgRecived = "Report";
						report.add(info.get(1));
						report.add(info.get(2));
						report.add(info.get(3));
						report.add(info.get(4));
						report.add(info.get(5));

						// amount = info.get(1);
						// income = info.get(2);
						// pickUpMethod = info.get(3);
						// droneDeliveryMethod = info.get(4);
						// immediateMethod = info.get(5);
					}
					awaitResponse = false;

				}

				if (info.get(0).equals("WaitForApproval")) {
					info.remove(0);
					waitingForApproval = info;
					awaitResponse = false;
				}

				if (info.get(0).equals("subscriberNumber")) {

					subscriberNumber = info.get(1);
					awaitResponse = false;

				}
				if (info.get(0).equals("Thresholdnum")) {
					t1.setThreshold(info.get(1));
					awaitResponse = false;
				}
				if (info.get(0).equals("UserInfo")) {
					if (info.get(1).equals("UserNotExist")) {
						msgRecieved = info.get(1);
						awaitResponse = false;
					} else {
						msgRecieved = info.get(1);
						s1.setFirstName(info.get(2));
						s1.setLastName(info.get(3));
						s1.setId(info.get(4));
						s1.setPhoneNumber(info.get(5));
						s1.setEmailAddress(info.get(6));
						s1.setCreditCardNumber(info.get(7));
						s1.setRole(info.get(8));
						System.out.println(s1);
						awaitResponse = false;
					}
				}
				////////////// dani
				if (info.get(0).equals("items")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
					System.out.println("chatClient item array" + item);
				}
				if (info.get(0).equals("ApprovingOrders")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
				}
				if (info.get(0).equals("ordersNumber")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
				}

				/////////////// updated//////////////
				if (info.get(0).equals("thresholdUpdate")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
				}
				if (info.get(0).equals("storeNeedsReffiling")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
				}
				if (info.get(0).equals("storeNeedsReffilingWorker")) {
					info.remove(0);
					item = info;
					awaitResponse = false;
				}
				/////////////// updated//////////////

				//////////////////// dani
			}
			if (infoObject.get(0) instanceof MyFile) {
				try {
					ArrayList<MyFile> infoMyFile = new ArrayList<>();
					int[] filesSize = new int[infoObject.size()];
					ArrayList<File> newFiles = new ArrayList<>();
					ArrayList<FileOutputStream> fis = new ArrayList<>();
					ArrayList<BufferedOutputStream> bos = new ArrayList<>();
					for (int i = 0; i < infoObject.size(); i++) {
						infoMyFile.add((MyFile) infoObject.get(i));
						filesSize[i] = infoMyFile.get(i).getSize();
						newFiles.add(new File("common/" + infoMyFile.get(i).getFileName()));
						fis.add(new FileOutputStream(newFiles.get(i)));
						bos.add(new BufferedOutputStream(fis.get(i)));
						bos.get(i).write(infoMyFile.get(i).getMybytearray(), 0, infoMyFile.get(i).getSize());
						bos.get(i).flush();
						fis.get(i).flush();
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error send (Files)msg) to Client");
				}
			}
		}
		if (msg instanceof String) {
			String str = (String) msg;
			if (str.equals("")) {
				awaitResponse = false;
			} else {
				if (str.equals("No Such Code") || str.equals("Was Picked Up") || str.equals("Code is found"))
					orderCodeMsg = str;

				if (str.equals("true") || str.equals("false")) {
					firstPayment = str;
					awaitResponse = false;
				}
				if (str.equals("Connected"))
					awaitResponse = false;
				else {
					String info = (String) msg;
					if (info.equals("Exist") || info.equals("NotExist")) {
						data = info;
						awaitResponse = false;
					}
				}
			}
		}

		if (msg instanceof Integer) {
			orderCode = Integer.toString((Integer) msg);
			awaitResponse = false;
		}
		awaitResponse = false;

	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param str The message from the UI.
	 */

	public void handleMessageFromClientUI(ArrayList<String> str) {
		try {
			if (str.get(0).equals("exit"))
				quit();
			if (str.get(0).equals("newClient")) {
				str.add(this.getHost());
			}
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(str);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {

	}
}
//End of ChatClient class
