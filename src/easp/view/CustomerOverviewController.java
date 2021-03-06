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
	private Label privateLabel;
	@FXML
	private Label mobileLabel;
	@FXML
	private Label workLabel;
	@FXML
	private Label faxLabel;

	private GUIMain main;
	private Customer actCustomer;

	public CustomerOverviewController() {

	}

	public Customer getActCustomer() {
		return this.actCustomer;
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastName());

		Customer customer = new Customer();
		this.showCustomerDetails(customer);
		this.actCustomer = null;

			this.customerTable.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> this.showCustomerDetails(newValue));
	}

	public void setMain(GUIMain main) {
		this.main = main;
		this.customerTable.setItems(this.main.getCustomerData());
	}

	public void showCustomerDetails(Customer c) {
		this.actCustomer = c;
		if (c != null) {
			this.nameLabel.setText(c.getFirstName().get() + " " + c.getLastName().get());
			this.privateLabel.setText(c.getPrivate().get());
			this.mobileLabel.setText(c.getMobile().get());
			this.workLabel.setText(c.getWork().get());
			this.faxLabel.setText(c.getFax().get());
			this.birthdayLabel.setText(c.getBirthday().get());
			this.mailLabel.setText(c.getMail().get());
			this.streetLabel.setText(c.getStreet().get());
			if (c.getZipCode().get() == 0) {
				this.zipCityLabel.setText(c.getCity().get());
			} else {
				this.zipCityLabel.setText(String.valueOf(c.getZipCode().get()) + " " + c.getCity().get());
			}
		} else {
			this.nameLabel.setText("");
			this.privateLabel.setText("");
			this.mobileLabel.setText("");
			this.workLabel.setText("");
			this.faxLabel.setText("");
			this.birthdayLabel.setText("");
			this.streetLabel.setText("");
			this.zipCityLabel.setText("");
			this.mailLabel.setText("");
		}
	}

}
