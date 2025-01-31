package clientGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Qamar
 * @author shokry
 *
 */
public class CostumerReport implements Initializable {
	@FXML
	private Button home;
	@FXML
	private Button back;
	@FXML
	private Label Machine;
	@FXML
	private Label year;
	@FXML
	private CategoryAxis x;
	@FXML
	private Label month;
	@FXML
	private NumberAxis y;
	@FXML
	private BarChart<?, ?> histogramChart;

	/**
	 * 	This method is used to initialize the data for the histogram chart.
	 * 	It retrieves customer report data from the ChatClient class and sets the data for the chart.
	 * 	@param location The URL location to be used.
	 * 	@param resources The resource bundle to be used.
	*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> costumerReport = ChatClient.report;
		XYChart.Series set1 = new XYChart.Series<>();
		System.out.println(costumerReport);
		Machine.setText(costumerReport.get(1));
		year.setText(costumerReport.get(6));
		month.setText(costumerReport.get(7));
		set1.getData().add(new XYChart.Data("0-3", Integer.parseInt(costumerReport.get(2))));
		set1.getData().add(new XYChart.Data("3-5", Integer.parseInt(costumerReport.get(3))));
		set1.getData().add(new XYChart.Data("5-10", Integer.parseInt(costumerReport.get(4))));
		set1.getData().add(new XYChart.Data("10+", Integer.parseInt(costumerReport.get(5))));
		histogramChart.getData().addAll(set1);
	}
	
	/**
	 * The start method is used to launch the application and set the primary stage
	 * of the application.
	 * 
	 * @param primaryStage The primary stage of the application.
	 * @throws Exception If there is an error loading the FXML file.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/clientGUI/CostumerReportPage.fxml"));
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
	}
	
	/**
	 * getBackBtn method is used to return to the previous window which is MainPageReportsController
	 *
	 * @param event The event that triggers the getBack action
	 * @throws Exception If there is an error while hiding the current window or opening the MainPageReportsController window
	 */
	public void back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		MainPageReportsController reportsFrame = new MainPageReportsController();
		reportsFrame.start(primaryStage);
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
	public void home(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		if (ChatClient.u1.getRole().equals("CEO")) {
			CEOController cEOCntroller = new CEOController();
			cEOCntroller.start(stage);
		} else {
			ManagerPageController managerPageController = new ManagerPageController();
			managerPageController.start(stage);
		}
	}
}