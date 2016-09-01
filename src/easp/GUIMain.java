package easp;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIMain extends Application {

	private Stage primaryStage;
	private BorderPane root;

	private ObservableList<Customer> customerData = FXCollections.observableArrayList();
	private MenuBar menuBar;
	private CustomerOverviewController overviewCtrl;
	private DBConnector connector;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("EASP 2016");
		
	    this.primaryStage.getIcons().add(new Image("file:resources/images/EASP2016-setup-icon.bmp"));

		initRoot();

		initRootMenuCustomers();

		connector = new DBConnector();
		connector.connect();
		connector.testConnection();
		updateCustomers();

		showCustomerView();
	}

	/*
	 * Empty Constructor
	 */
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
		this.menuBar = new MenuBar();

		// create entries for the menu bar
		Menu menuFile = new Menu("Datei");
		Menu menuEdit = new Menu("Bearbeiten");
		Menu menuCreate = new Menu("Erstellen");
		Menu menuHelp = new Menu("Hilfe");

		// create entries for menuFile
		// TODO: setOnAction handling
		MenuItem save = new MenuItem("Kundendatei in Datei speichern");
		MenuItem saveas = new MenuItem("Kundendatei Speichern unter...");
		MenuItem load = new MenuItem("Kundendatei Laden...");
		MenuItem exit = new MenuItem("Beenden");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		menuFile.getItems().addAll(save, saveas, load, new SeparatorMenuItem(), exit);

		// TODO: setOnAction handling
		MenuItem newCustomer = new MenuItem("Neuen Kunden anlegen");
		newCustomer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				showNewCustomerView(false, null);
			}
		});

		// TODO: Implement this
		MenuItem edit = new MenuItem("Kundendaten bearbeiten");
		edit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent t){
				if (overviewCtrl.getActCustomer() != null){
					showNewCustomerView(true, overviewCtrl.getActCustomer());
				}
			}
		});

		MenuItem delete = new MenuItem("Kundendaten löschen");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (overviewCtrl.getActCustomer() != null) {
					deleteCustomer(overviewCtrl.getActCustomer());
				}
			}
		});

		menuEdit.getItems().addAll(newCustomer, edit, delete);

		// create entries for menuCreate
		// TODO: setOnAction handling
		MenuItem invoice = new MenuItem("Rechnung");
		invoice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (GUIMain.this.overviewCtrl.getActCustomer() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setHeaderText("Kein Kunde gewählt");
					alert.setContentText("Bitte wählen sie vor dem Erstellen einer Rechnung einen Kunden aus, "
							+ "an welchen die Rechnung gestellt werden soll.");
					alert.showAndWait();
				} else {
					showNewInvoiceView();
				}
			}
		});

		// TODO: Implement this
		MenuItem offer = new MenuItem("Angebot");

		// TODO: Implement this
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
			this.overviewCtrl = controller;

		} catch (IOException e) {
			System.err.println("Error while loading customer overview");
			e.printStackTrace();
		}
	}

	private void showNewCustomerView(boolean update, Customer c) {
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
			// determines whether update or insertion
			controller.setUpdate(update, c);

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
			// controller.setMain(this);
			controller.setCustomer(overviewCtrl.getActCustomer());

			popup.showAndWait();
		} catch (IOException e) {
			System.err.println("Error while loading new invoice view");
			e.printStackTrace();
		}
	}

	public void getBackToMenu() {
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

	public void insertNewCustomer(Customer c) {
		connector.insertNewCustomer(c);
		Map<String, String> numbers = c.getNumbers();
		// Data needs to be updated to determine the id of new customer
		updateCustomers();

		// Find latest Customer to add numbers
		Customer latestCustomer = new Customer();
		for (Customer cus : customerData) {
			if (cus.getId().get() > latestCustomer.getId().get()) {
				latestCustomer = cus;
			}
		}

		connector.insertNumbers(latestCustomer, numbers);
		updateCustomers();
	}

	public void deleteCustomer(Customer c) {
		connector.deleteCustomer(c);
		updateCustomers();
	}

	public void editCustomer(Customer c) {
		// Edit entries in table "Kunden"
		connector.editCustomer(c);
		
		// Edit entries in table "Nummern"
		connector.editNumbers(c, c.getNumbers());
		
		updateCustomers();
	}

	private void updateCustomers() {
		this.customerData.clear();
		Map<Integer, Customer> customers = connector.retrieveCustomerInformations();
		for (int k : customers.keySet()) {
			customerData.add(customers.get(k));
		}
	}
}
