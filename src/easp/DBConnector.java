package easp;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import easp.model.Customer;
import easp.view.CustomerOverviewController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public class DBConnector {

	private final String getCustomerInformations = "SELECT * FROM kunden JOIN nummern ON kunden.id = nummern.kunde;";

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
			login = ctrl.getLogin();
			while (!login.isPresent()) {
				login = ctrl.getLogin();
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
			}
		}
		return customers;
	}

	private Map<Integer, Customer> createCustomers(ResultSet r) throws SQLException {
		Map<Integer, Customer> customers = new HashMap<>();

		while (r.next()) {
			Customer c = new Customer();

			String street = "";
			int hnr = 0;
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

				hnr = r.getInt("hausnr");
				street = r.getString("strasse");
				c.setStreet(new SimpleStringProperty(street + Integer.toString(hnr)));
				
				StringProperty mail = new SimpleStringProperty(r.getString("email"));
				c.setMail(mail);

				StringProperty number = new SimpleStringProperty(r.getString("nummer"));
				// Get kind of number
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

	public void setOverviewController(CustomerOverviewController ctrl) {
		this.ctrl = ctrl;
	}

}