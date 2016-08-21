package easp;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import easp.model.Customer;
import easp.view.CustomerOverviewController;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class DBConnector {

	private final String getCustomerInformations = "SELECT * FROM kunden LEFT JOIN nummern ON kunden.id = nummern.kunde";
	private final String insertCustomer = "INSERT INTO kunden (nachname, vorname, geburtsdatum, strasse, plz, ort, email) VALUES (?,?,?,?,?,?,?)";
	private final String deleteCustomer = "DELETE FROM kunden WHERE id = ?";
	private final String editCustomer = "UPDATE kunden SET ? = ? WHERE id = ?";

	Connection conn;
	String username, password;
	Properties connProps;

	CustomerOverviewController ctrl;

	public DBConnector() {
		conn = null;
		connProps = new Properties();
		System.out.println("DBConnector initialized");
		System.out.println("Waiting for connection.");
	}

	public void connect() {
		System.out.println("Trying to start connection ...");
		try {
			System.out.println("Driver: " + Class.forName("org.postgresql.Driver"));

			// Login
			Optional<Pair<String, String>> login;
			login = getLogin();
			while (!login.isPresent()) {
				login = getLogin();
			}
			username = login.get().getKey();
			password = login.get().getValue();
			System.out.println("Username: " + username);
			System.out.println("Password entered");
			connProps.put("username", username);
			connProps.put("password", password);

			// Start connection
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/EASP", connProps);
			System.out.println("Connection to database EASP established");

		} catch (Exception e) {
			System.err.println("Error: " + e);
			e.printStackTrace();
		}
	}

	private Optional<Pair<String, String>> getLogin() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();

		// Set appearence of dialog
		dialog.setHeaderText("Bitte geben sie ihren Benutzernamen und ihr Passwort ein.");
		dialog.setTitle("Login");

		ClassLoader loader = DBConnector.class.getClassLoader();
		String filepath = loader.getResource("").toString() + "view" + File.separator + "icons" + File.separator + "login.png";
		ImageView imgv = new ImageView();
		Image img = new Image(filepath);
		imgv.setImage(img);
		dialog.setGraphic(imgv);
		
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> dates = dialog.showAndWait();

		return dates;

	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Error: " + e);
			e.printStackTrace();
		}
	}

	public Map<Integer, Customer> retrieveCustomerInformations() {
		Map<Integer, Customer> customers = new HashMap<>();
		if (conn != null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet r = stmt.executeQuery(getCustomerInformations);

				// Output
				customers = createCustomers(r);

				r.close();
				stmt.close();
			} catch (Exception e) {
				System.err.println("Error: " + e);
				e.printStackTrace();
			}
		}
		return customers;
	}

	private Map<Integer, Customer> createCustomers(ResultSet r) throws SQLException {
		Map<Integer, Customer> customers = new HashMap<>();

		while (r.next()) {
			Customer c = new Customer();

			IntegerProperty id = new SimpleIntegerProperty(0);

			// Set id of new customer to determine if present in list or not
			id = new SimpleIntegerProperty(r.getInt("id"));
			c.setId(id);

			// If customer is not present in list add all informations
			if (!customers.containsKey(id.get())) {

				StringProperty lastName = new SimpleStringProperty(r.getString("nachname"));
				c.setLastName(lastName);

				StringProperty firstName = new SimpleStringProperty(r.getString("vorname"));
				c.setFirstName(firstName);

				// Format birthday to match "dd.mm.yyyy"
				String birthday = r.getString("geburtsdatum");
				String[] ymd = birthday.split("-");
				c.setBirthday(new SimpleStringProperty(ymd[2] + "." + ymd[1] + "." + ymd[0]));

				IntegerProperty zip = new SimpleIntegerProperty(r.getInt("plz"));
				c.setZipCode(zip);

				StringProperty city = new SimpleStringProperty(r.getString("ort"));
				c.setCity(city);

				StringProperty street = new SimpleStringProperty(r.getString("strasse"));
				c.setStreet(street);

				StringProperty mail = new SimpleStringProperty(r.getString("email"));
				c.setMail(mail);

				StringProperty number = new SimpleStringProperty(r.getString("nummer"));
				// Get kind of number
				try {
					switch (r.getString("art")) {
					case "Mobil":
						c.setMobile(number);
						break;
					case "Privat":
						c.setPrivate(number);
						break;
					case "Geschäftlich":
						c.setWork(number);
						break;
					case "Fax":
						c.setFax(number);
						break;
					default:
						break;
					}
				} catch (NullPointerException e) {

				}

				customers.put(id.get(), c);
			} else {
				// else set only numbers for customer with given id
				c = customers.get(id.get());
				StringProperty number = new SimpleStringProperty(r.getString("nummer"));
				switch (r.getString("art")) {
				case "Mobil":
					c.setMobile(number);
					break;
				case "Privat":
					c.setPrivate(number);
					break;
				case "Geschäftlich":
					c.setWork(number);
					break;
				case "Fax":
					c.setFax(number);
					break;
				default:
					break;
				}
			}
		}

		return customers;
	}

	/*
	 * (nachname, vorname, geburtsdatum, strasse, plz, ort, email)
	 */

	public void insertNewCustomer(Customer c) {
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement(insertCustomer);

				// Set missing informations to prepared statement
				stmt.setString(1, c.getLastName().get());
				stmt.setString(2, c.getFirstName().get());
				stmt.setDate(3, Date.valueOf(c.getBirthday().get()));
				stmt.setString(4, c.getStreet().get());
				stmt.setInt(5, c.getZipCode().get());
				stmt.setString(6, c.getCity().get());
				stmt.setString(7, c.getMail().get());

				// Execute statement
				stmt.execute();

				// Close everything
				stmt.close();
			} catch (Exception e) {
				System.err.println("Fehler: " + e);
			}
		}
	}

	public void deleteCustomer(Customer c) {
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement(deleteCustomer);

				// Set id (= Primary Key) which will be deleted from database
				stmt.setInt(1, c.getId().get());

				// Execute Delete
				stmt.execute();
			} catch (Exception e) {
				System.err.println("Fehler: " + e);
			}
		}
	}

	public void editCustomer(Customer c, String column, Object newValue) {
		if (conn != null) {
			try {
				// Fill in informations
				PreparedStatement stmt = conn.prepareStatement(editCustomer);
				stmt.setString(1, column);
				stmt.setObject(2, newValue); // <- TODO: Does this work?
				stmt.setInt(3, c.getId().get());

				// Execute Update
				stmt.executeUpdate();
			} catch (Exception e) {
				System.err.println("Fehler: " + e);
			}
		}
	}
	
	// TODO
	public void insertNewNumbers(Customer c){
		if (conn != null){
			Map<String, String> numbers = c.getNumbers();
			if (numbers.containsKey("Privat")){
				Statement stmt = prepareNumberStatement("Privat", numbers.get("Privat"));
			}
		}
	}

	// TODO
	private Statement prepareNumberStatement(String string, String string2) {
		return null;
	}

	public void setOverviewController(CustomerOverviewController ctrl) {
		this.ctrl = ctrl;
	}

}