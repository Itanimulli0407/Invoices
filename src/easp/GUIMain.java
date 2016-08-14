package easp;

import java.io.File;
import java.io.IOException;

import easp.model.Customer;
import easp.view.CustomerOverviewController;
import easp.view.NewCustomerController;
import easp.view.NewInvoiceController;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIMain extends Application {

	private Stage primaryStage;
	private BorderPane root;

	// TODO: this has to be private, will be exchanged by storing data in xml or
	// database
	public ObservableList<Customer> customerData = FXCollections.observableArrayList();
	private MenuBar menuBar;
	private CustomerOverviewController overviewCtrl;
	private DBConnector connector;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("EASP 2016");

		initRoot();

		initRootMenuCustomers();
		
		showCustomerView();
		
		connector = new DBConnector();
		connector.setOverviewController(overviewCtrl);
		connector.connect();

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
		this.menuBar = new MenuBar();

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
			this.overviewCtrl = controller;

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
}

/*
 * Some test data
 *
Customer c = new Customer();
c.setFirstName(new SimpleStringProperty("Lukas"));
c.setLastName(new SimpleStringProperty("Wachter"));
c.setStreet(new SimpleStringProperty("zum Rockenhübel 29"));
c.setZipCode(new SimpleIntegerProperty(66589));
c.setCity(new SimpleStringProperty("Merchweiler"));
c.setPrivate(new SimpleStringProperty("06825-406225"));
c.setBirthday(new SimpleStringProperty("04.07.1996"));
c.setMobile(new SimpleStringProperty("0157-39112800"));
customerData.add(c);
Customer c2 = new Customer();
c2.setFirstName(new SimpleStringProperty("Kristina"));
c2.setLastName(new SimpleStringProperty("Bauer"));
c2.setStreet(new SimpleStringProperty("Illinger Straße 47"));
c2.setZipCode(new SimpleIntegerProperty(66589));
c2.setCity(new SimpleStringProperty("Merchweiler"));
c2.setPrivate(new SimpleStringProperty("06825-4999610"));
c2.setBirthday(new SimpleStringProperty("08.11.1995"));
c2.setMail(new SimpleStringProperty("kriba0811@gmail.com"));
customerData.add(c2);*/
