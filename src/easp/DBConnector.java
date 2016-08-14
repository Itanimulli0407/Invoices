package easp;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;

import easp.view.CustomerOverviewController;
import javafx.util.Pair;

public class DBConnector{

	Connection conn;
	String username, password;
	Properties connProps;

	CustomerOverviewController ctrl;

	public DBConnector () {
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
			System.err.println("Fehler: " + e);
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Fehler: " + e);
			e.printStackTrace();
		}
	}

	public void setOverviewController(CustomerOverviewController ctrl) {
		this.ctrl = ctrl;
	}

}