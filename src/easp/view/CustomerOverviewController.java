package easp.view;

import easp.GUIMain;
import easp.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerOverviewController {

	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableColumn<Customer, Integer> idColumn;
	@FXML
	private TableColumn<Customer, String> firstNameColumn;
	@FXML
	private TableColumn<Customer, String> lastNameColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label zipCityLabel;
	@FXML
	private Label birthdayLabel;
	@FXML
	private Label mailLabel;
	@FXML
	private Label phoneLabel;

	private GUIMain main;

	public CustomerOverviewController() {

	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastName());

		this.showCustomerDetails(new Customer());

		this.customerTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> this.showCustomerDetails(newValue));
	}

	public void setMain(GUIMain main) {
		this.main = main;
		this.customerTable.setItems(this.main.getCustomerData());
	}

	public void showCustomerDetails(Customer c) {
		this.nameLabel.setText(c.getFirstName().get() + " " + c.getLastName().get());
		this.phoneLabel.setText(c.getPhone().get());
		this.birthdayLabel.setText(c.getBirthday().get());
		this.mailLabel.setText(c.getMail().get());
		this.streetLabel.setText(c.getStreet().get());
		if (c.getZipCode().get() == 0) {
			this.zipCityLabel.setText(c.getCity().get());
		} else {
			this.zipCityLabel.setText(String.valueOf(c.getZipCode().get()) + " " + c.getCity().get());
		}
	}

}
