package easp;

import java.io.File;
import java.io.IOException;

import easp.model.Customer;
import easp.view.CustomerOverviewController;
import easp.view.NewCustomerController;
import easp.view.NewInvoiceController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIMain extends Application {

	private Stage primaryStage;
	private BorderPane root;

	// TODO: this has to be private, will be exchanged by storing data in xml or database
	public ObservableList<Customer> customerData = FXCollections.observableArrayList();
	private MenuBar menuBar;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("EASP 2016");

		initRoot();

		initRootMenuCustomers();

		showCustomerView();
	}

	public GUIMain() {
		
	}

	private void initRoot() {
		try {
			// Load the .fxml file for the root pane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation((GUIMain.class.getResource("view" + File.separator + "Root.fxml")));
			this.root = (BorderPane) loader.load();

			// show root pane
			Scene scene = new Scene(this.root);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			System.err.println("Error while loading root pane");
			e.printStackTrace();
		}
	}

	private void initRootMenuCustomers() {
		// create a menu bar
		this.menuBar  = new MenuBar();

		// create entries for the menu bar
		Menu menuFile = new Menu("Datei");
		Menu menuEdit = new Menu("Bearbeiten");
		Menu menuCreate = new Menu("Erstellen");
		Menu menuHelp = new Menu("Hilfe");

		// create entries for menuFile
		// TODO: setOnAction handling
		MenuItem save = new MenuItem("Kundendatei Speichern");
		MenuItem saveas = new MenuItem("Kundendatei Speichern unter...");
		MenuItem load = new MenuItem("Kundendatei Laden...");
		MenuItem exit = new MenuItem("Beenden");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		menuFile.getItems().addAll(save, saveas, load, new SeparatorMenuItem(), exit);

		// create entries for menuEdit
		// TODO: setOnAction handling
		MenuItem newCustomer = new MenuItem("Neuen Kunden anlegen");
		newCustomer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				showNewCustomerView();
			}
		});
		MenuItem edit = new MenuItem("Kundendaten bearbeiten");
		MenuItem delete = new MenuItem("Kundendaten löschen");
		menuEdit.getItems().addAll(newCustomer, edit, delete);

		// create entries for menuCreate
		// TODO: setOnAction handling
		MenuItem invoice = new MenuItem("Rechnung");
		invoice.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t){
				showNewInvoiceView();
			}
		});
		MenuItem offer = new MenuItem("Angebot");
		MenuItem reminder = new MenuItem("Zahlungserinnerung");
		menuCreate.getItems().addAll(invoice, offer, reminder);

		// create entries for menuHelp
		// TODO: setOnAction handling
		MenuItem help = new MenuItem("Hilfe");
		MenuItem about = new MenuItem("Über");
		menuHelp.getItems().addAll(help, about);

		menuBar.getMenus().addAll(menuFile, menuEdit, menuCreate, menuHelp);

		this.root.setTop(menuBar);
	}

	private void showCustomerView() {
		try {
			// Load the .fxml file for the person Overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource("view" + File.separator + "CustomerOverview.fxml"));
			AnchorPane customerView = (AnchorPane) loader.load();

			// show customer overview in center of root pane
			this.root.setCenter(customerView);

			// set main
			CustomerOverviewController controller = loader.getController();
			controller.setMain(this);

		} catch (IOException e) {
			System.err.println("Error while loading customer overview");
			e.printStackTrace();
		}
	}

	private void showNewCustomerView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource("view" + File.separator + "NewCustomerView.fxml"));
			AnchorPane newCustomerView = (AnchorPane) loader.load();

			Stage popup = new Stage();
			popup.setTitle("Neuer Kunde");
			popup.initModality(Modality.WINDOW_MODAL);
			popup.initOwner(this.primaryStage);
			Scene popupScene = new Scene(newCustomerView);
			popup.setScene(popupScene);
			
			NewCustomerController controller = loader.getController();
			controller.setStage(popup);
			controller.setMain(this);
			
			popup.showAndWait();
		} catch (IOException e) {
			System.err.println("Error while loading new customer view");
			e.printStackTrace();
		}
	}
	
	private void showNewInvoiceView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GUIMain.class.getResource("view" + File.separator + "NewInvoiceView.fxml"));
			AnchorPane newInvoiceView = (AnchorPane) loader.load();
						
			Stage popup = new Stage();
			popup.setTitle("Neue Rechnung");
			popup.initModality(Modality.WINDOW_MODAL);
			popup.initOwner(this.primaryStage);
			Scene popupScene = new Scene(newInvoiceView);
			popup.setScene(popupScene);
			
			NewInvoiceController controller = loader.getController();
			controller.setStage(popup);
			controller.setMain(this);
			
			popup.showAndWait();
		} catch (IOException e) {
			System.err.println("Error while loading new invoice view");
			e.printStackTrace();
		}
	}
	
	public void getBackToMenu(){
		this.showCustomerView();
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public ObservableList<Customer> getCustomerData() {
		return this.customerData;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
