package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Costumer;

public class CostumerRegisterController implements Initializable {
	private Costumer s;

	@FXML
	private Button btnNo = null;
	@FXML
	private Button btnYes = null;

	private String id;

	@FXML
	private Button backBtn = null;
	@FXML
	private Button btnHome = null;
	@FXML
	private Label noBtnLabel = new Label();
	@FXML
	private ImageView logoImage;
	@FXML
	private ImageView CostumerRegister;
	ObservableList<String> list;

	Image logo = new Image(getClass().getResourceAsStream("noBackgroundLogo-removebg-preview.png"));
	Image CostumerRegister1 = new Image(getClass().getResourceAsStream("Costumer_Register.png"));

	/**
	 * getBackBtn method is used to return to the previous window which is
	 * UsersInfoFormController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or
	 *                   opening the UsersInfoFormController window
	 */
	public void getBackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGUI/UsersInfoForm.fxml").openStream());
		UsersInfoFormController usersInfoFormController = loader.getController();
		usersInfoFormController.loadSubscriber(ChatClient.s1);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Users Info Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * The loadSubscriber method is used to load the user's information from the
	 * Costumer object into the appropriate text fields on the form.
	 *
	 * @param s1 an object of the Costumer class that holds the user's information
	 */
	public void loadSubscriber(Costumer s1) {
		this.s = s1;
		System.out.println(s);
		this.id = s.getId();
	}

	/**
	 * This method is responsible for redirecting the user to the home page of the
	 * application. It hides the current window and opens a new stage for the home
	 * page.
	 * 
	 * @param event - ActionEvent object that is triggered when the 'Home' button is
	 *              clicked
	 * @throws Exception - in case of any error while loading the home page
	 */
	public void getHomeBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UsersController Frame = new UsersController();
		Frame.start(primaryStage);
	}

	/**
	 * This method is used to handle the event when the user clicks the "No" button
	 * in the UI. It sets the text of the "noBtnLabel" label to indicate that the
	 * user did not register as a costumer and makes the label visible.
	 * 
	 * @param event The event object representing the button click event.
	 * @throws Exception
	 * 
	 */
	public void getNoButton(ActionEvent event) throws Exception {
		noBtnLabel.setText("The user doesn't register as a costumer");
		noBtnLabel.setVisible(true);

	}

	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/Costumer_Register.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Costumer registering page");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.show();
		// image.setPickOnBounds(true);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	/**
	 * This method is used to update user's role to WaitForApproval by sending the
	 * update request to the server. If the request sent successfully, an alert will
	 * show to the user with a message that their registration request is forwarded
	 * to the regional manager for approval. Also, after settling the account, an
	 * email and text message (SMS) will send to the customer, which includes
	 * confirmation of the completion of the process.
	 * 
	 * @param event - The event of clicking on the Yes button
	 * @throws Exception
	 */
	public void getYesBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		ArrayList<String> updateUserInfo = new ArrayList<>();
		Stage primaryStage = new Stage();
		updateUserInfo.add("UpdatingRoleOfUserInDB");
		updateUserInfo.add(s.getId());
		updateUserInfo.add("WaitForApproval");
		ClientUI.chat.accept(updateUserInfo);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Manager Approval");
		alert.setContentText(
				"The registration request is forwarded to the regional manager for approval.\n After settling the account, an email and text message (SMS) will send to the customer, which includes confirmation of the completion of the process.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/UsersFrame.fxml"));
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

		} else if (result.get() == ButtonType.CANCEL) {
			noBtnLabel.setText("Registiration failed!");
		}
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	}

	/**
	 * initialize method is used to initialize the elements in the scene
	 *
	 * @param arg0 URL for the location of the root object
	 * @param arg1 ResourceBundle for the location of the root object
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logoImage.setImage(logo);
		CostumerRegister.setImage(CostumerRegister1);

	}

}
