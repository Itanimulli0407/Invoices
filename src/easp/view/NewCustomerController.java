package easp.view;

import easp.GUIMain;
import easp.model.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewCustomerController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField birthdayField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField zipCityField;
	@FXML
	private TextField mailField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField mobileField;
	@FXML
	private TextField workField;
	@FXML
	private TextField faxField;
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button clearButton;

	private TextField[] textFieldArray = new TextField[10];

	private Customer newCustomer = new Customer();

	private GUIMain main;
	private Stage stage;

	public NewCustomerController() {

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void initialize() {
		// create array with all TextFields
		textFieldArray[0] = this.nameField;
		textFieldArray[1] = this.firstNameField;
		textFieldArray[2] = this.streetField;
		textFieldArray[3] = this.zipCityField;
		textFieldArray[4] = this.birthdayField;
		textFieldArray[5] = this.phoneField;
		textFieldArray[6] = this.mobileField;
		textFieldArray[7] = this.workField;
		textFieldArray[8] = this.faxField;
		textFieldArray[9] = this.mailField;

		// handle clear button
		this.clearButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				for (int i = 0; i < textFieldArray.length; i++) {
					textFieldArray[i].clear();
				}
			}
		});

		// handle cancel Button
		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				for (int i = 0; i < textFieldArray.length; i++) {
					textFieldArray[i].clear();
				}
				stage.close();
			}
		});

		// handle ok button
		this.okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				if (checkInput()) {
					setInformationsToCustomer();
					//TODO: this has to be stored in xml file
					main.customerData.add(newCustomer);
					stage.close();
				}
			}
		});
	}

	private void setInformationsToCustomer() {
		String name = nameField.getText();
		String firstName = firstNameField.getText();
		String street = streetField.getText();
		String zipCity = zipCityField.getText();
		String birthday = birthdayField.getText();
		String mail = mailField.getText();
		String phone = phoneField.getText();
		String mobile = mobileField.getText();
		String work = workField.getText();
		String fax = faxField.getText();

		newCustomer.setFirstName(new SimpleStringProperty(firstName));
		newCustomer.setLastName(new SimpleStringProperty(name));
		if (zipCity.length() != 0) {
			String[] zipCityParts = zipCity.split(" ", 2);
			if (zipCityParts[0].length() != 0) {
				IntegerProperty zipCode = new SimpleIntegerProperty(Integer.parseInt(zipCityParts[0]));
				newCustomer.setZipCode(zipCode);
			} else {
				newCustomer.setZipCode(new SimpleIntegerProperty(0));
			}
			if (zipCityParts.length > 1) {
				newCustomer.setCity(new SimpleStringProperty(zipCityParts[1]));
			} else {
				newCustomer.setCity(new SimpleStringProperty(""));
			}
		} else {
			newCustomer.setCity(new SimpleStringProperty(""));
			newCustomer.setZipCode(new SimpleIntegerProperty(0));
		}
		newCustomer.setStreet(new SimpleStringProperty(street));
		newCustomer.setMail(new SimpleStringProperty(mail));
		newCustomer.setBirthday(new SimpleStringProperty(birthday));
		newCustomer.setPhone(new SimpleStringProperty(phone));
		newCustomer.setMobile(new SimpleStringProperty(mobile));
		newCustomer.setWork(new SimpleStringProperty(work));
		newCustomer.setFax(new SimpleStringProperty(fax));
}

	/**
	 * 
	 * @return is input legal
	 */
	private boolean checkInput() {
		String name = nameField.getText();
		String firstName = firstNameField.getText();
		String street = streetField.getText();
		String zipCity = zipCityField.getText();
		String birthday = birthdayField.getText();
		String mail = mailField.getText();
		String phone = phoneField.getText();
		String mobile = mobileField.getText();
		String work = workField.getText();
		String fax = faxField.getText();

		// catching wrong inputs
		if (name.length() == 0 || firstName.length() == 0) {
			this.showErrorMessage("Keinen vollständigen Namen angegeben");
			return false;
		}
		if ((street.length() != 0) && (zipCity.length() == 0)) {
			this.showErrorMessage("Keinen Wohnort angegeben");
			return false;
		}
		if ((street.length() == 0) && (zipCity.length() != 0)) {
			this.showErrorMessage("Keine Straße angegeben");
			return false;
		}
		if (birthday.length() != 0 && !birthday.matches("\\d\\d[.]\\d\\d[.]\\d\\d\\d\\d")) {
			this.showErrorMessage(
					"Das eingegebene Geburtsdatum hat das falsche Format. Erwartet wird eine Eingabe der Form TT.MM.JJJJ");
			return false;
		}
		if (street.length() != 0 && !street.matches("[a-zA-Z_0-9äöüß\\s]+\\s+[a-zA-Z_0-9-]")) {
			this.showErrorMessage(
					"Die eingegebene Adresse ist nicht korrekt. Erwartet wird eine Eingabe der Form \"Straße Hausnr\"");
			return false;
		}
		if (zipCity.length() != 0 && !zipCity.matches("\\d\\d\\d\\d\\d\\s[a-zA-Z-äöüß]+")) {
			this.showErrorMessage(
					"Der eingegebene Ort/PLZ ist nicht korrekt. Erwartet wird eine Eingabe der Form \"PLZ Stadt\"");
			return false;
		}
		if (mail.length() != 0 && !mail.matches("[a-zA-Z_0-9.]+[@][a-zA-Z_0-9.]+[.][a-zA-Z]+")) {
			this.showErrorMessage(
					"Die eingebene Mail Adresse ist nicht korrekt. Erwartet wird eine Eingabe der Form \"mail.morchars@provider.tld\"");
			return false;
		}
		if (phone.length() != 0 && !phone.matches("[0-9/\\- +]+")) {
			this.showErrorMessage(
					"Die eingegebene Telefonnummer ist nicht korrekt. Sie enthält womöglich Buchstaben oder unzulässige Zeichen");
			return false;
		}
		if (mobile.length() != 0 && !mobile.matches("[0-9/\\- +]+")) {
			this.showErrorMessage(
					"Die eingegebene Mobilnummer ist nicht korrekt. Sie enthält womöglich Buchstaben oder unzulässige Zeichen");
			return false;
		}
		if (work.length() != 0 && !work.matches("[0-9/\\- +]+")) {
			this.showErrorMessage(
					"Die eingegebene geschäftliche Nummer ist nicht korrekt. Sie enthält womöglich Buchstaben oder unzulässige Zeichen");
			return false;
		}
		if (fax.length() != 0 && !fax.matches("[0-9/\\- +]+")) {
			this.showErrorMessage(
					"Die eingegebene Faxnummer ist nicht korrekt. Sie enthält womöglich Buchstaben oder unzulässige Zeichen");
			return false;
		}
		// input is fine
		return true;
	}

	private void showErrorMessage(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehler bei der Eingabe");
		alert.setHeaderText("Sie haben einen Fehler bei der Eingabe der Daten gemacht");
		alert.setContentText(error + " \n\nÜberprüfen sie bitte ihre Eingabe.");
		alert.show();
	}

	public void setMain(GUIMain main) {
		this.main = main;
	}

}
