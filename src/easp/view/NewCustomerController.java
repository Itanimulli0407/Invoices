package easp.view;

import easp.Checker;
import easp.GUIMain;
import easp.InputException;
import easp.model.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	private TextField privateField;
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

	private Customer newCustomer = new Customer();

	private Checker checker;
	private GUIMain main;
	private Stage stage;

	public NewCustomerController() {

	}

	/**
	 * 
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Initialize function to set button actions for example
	 */
	@FXML
	public void initialize() {
		checker = new Checker();

		// handle clear button
		this.clearButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				firstNameField.setText("");
				nameField.setText("");
				mailField.setText("");
				privateField.setText("");
				birthdayField.setText("");
				mobileField.setText("");
				streetField.setText("");
				zipCityField.setText("");
				workField.setText("");
				faxField.setText("");
			}
		});

		// handle cancel Button
		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				stage.close();
			}
		});

		// handle ok button
		this.okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				if (checkInput()) {
					setInformationsToCustomer();
					// TODO: this has to be stored in database
					main.insertNewCustomer(newCustomer);
					stage.close();
				}
			}
		});

		// if focused remove red borders
		firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					firstNameField.setStyle("");
			}
		});
		nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					nameField.setStyle("");
			}
		});
		birthdayField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					birthdayField.setStyle("");
			}
		});
		mailField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					mailField.setStyle("");
			}
		});
		privateField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					privateField.setStyle("");
			}
		});
		mobileField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					mobileField.setStyle("");
			}
		});
		workField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					workField.setStyle("");
			}
		});
		faxField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					faxField.setStyle("");
			}
		});
		streetField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					streetField.setStyle("");
			}
		});
		zipCityField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					zipCityField.setStyle("");
			}
		});
	}

	/**
	 * Set entered informations to the new customer
	 */
	private void setInformationsToCustomer() {
		String name = nameField.getText();
		String firstName = firstNameField.getText();
		String street = streetField.getText();
		String zipCity = zipCityField.getText();
		String birthday = birthdayField.getText();
		String mail = mailField.getText();
		String phone = privateField.getText();
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
		
		// Format birthday
		System.out.println("Birthday:" + birthday + "Ende");
		if (birthday.length() > 1){
			String[] dmy = birthday.split("\\.");
			birthday = dmy[2] + "-" + dmy[1] + "-" + dmy[0];
			newCustomer.setBirthday(new SimpleStringProperty(birthday));
		} else {
			newCustomer.setBirthday(new SimpleStringProperty(""));
		}

		
		newCustomer.setPrivate(new SimpleStringProperty(phone));
		newCustomer.setMobile(new SimpleStringProperty(mobile));
		newCustomer.setWork(new SimpleStringProperty(work));
		newCustomer.setFax(new SimpleStringProperty(fax));
	}

	/**
	 * Checks whether entered informations are formatted correctly
	 * 
	 * @return is input legal
	 */
	private boolean checkInput() {

		Boolean thrown = false;

		// catching wrong inputs
		try {
			checker.checkFirstName(firstNameField.getText());
			firstNameField.setStyle("");
		} catch (InputException e) {
			firstNameField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkLastName(nameField.getText());
			nameField.setStyle("");
		} catch (InputException e) {
			nameField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkBirthday(birthdayField.getText());
			birthdayField.setStyle("");
		} catch (InputException e) {
			birthdayField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkPhoneNumber(privateField.getText());
			privateField.setStyle("");
		} catch (InputException e) {
			privateField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkPhoneNumber(mobileField.getText());
			mobileField.setStyle("");
		} catch (InputException e) {
			mobileField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkPhoneNumber(workField.getText());
			workField.setStyle("");
		} catch (InputException e) {
			workField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkPhoneNumber(faxField.getText());
			faxField.setStyle("");
		} catch (InputException e) {
			faxField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkMail(mailField.getText());
			mailField.setStyle("");
		} catch (InputException e) {
			mailField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		try {
			checker.checkAdress(streetField.getText(), zipCityField.getText());
			streetField.setStyle("");
			zipCityField.setStyle("");
		} catch (InputException e) {
			streetField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			zipCityField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}

		if (thrown)
			return false;
		else
			return true;
		// input is fine
	}

	public void setMain(GUIMain main) {
		this.main = main;
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getFirstNameField() {
		return firstNameField;
	}

	public TextField getBirthdayField() {
		return birthdayField;
	}

	public TextField getStreetField() {
		return streetField;
	}

	public TextField getZipCityField() {
		return zipCityField;
	}

	public TextField getMailField() {
		return mailField;
	}

	public TextField getPrivateField() {
		return privateField;
	}

	public TextField getMobileField() {
		return mobileField;
	}

	public TextField getWorkField() {
		return workField;
	}

	public TextField getFaxField() {
		return faxField;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getClearButton() {
		return clearButton;
	}

	public Customer getNewCustomer() {
		return newCustomer;
	}

}
