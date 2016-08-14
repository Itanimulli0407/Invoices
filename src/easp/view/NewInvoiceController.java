package easp.view;

import java.util.ArrayList;

import easp.Checker;

//import java.util.List;

import easp.GUIMain;
import easp.PDFMaker;
import easp.model.Customer;
import easp.model.Invoice;
import easp.model.Position;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewInvoiceController {

	@FXML
	private VBox posBox;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button addButton;

	private ArrayList<Position> positions;
	private Stage stage;
	private GUIMain main;
	private Customer customer;
	private Invoice invoice;

	/**
	 * Empty constructor
	 */
	public NewInvoiceController() {

	}

	/**
	 * 
	 * @return Returns the created invoice
	 */
	public Invoice getInvoice() {
		if (!this.invoice.equals(null)) {
			return this.invoice;
		} else
			return new Invoice(0, new ArrayList<Position>());
	}

	/**
	 * 
	 * @param stage:
	 *            The stage where the Overlay will appear
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * 
	 * @param customer:
	 *            receiving customer
	 * 
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Initializes variables, buttons, etc
	 */
	@FXML
	public void initialize() {
		positions = new ArrayList<Position>();
		invoice = new Invoice(0, positions);

		// handle cancel Button
		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				stage.close();
			}
		});

		// handle ok Button
		this.okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				boolean thrown = false;
				for (Position pos : positions) {
					if (!pos.setInformations())
						thrown = true;
				}
				if (!thrown) {
					for (Position pos : positions) {
						System.out.println(pos.toString());
					}
					NewInvoiceController.this.invoice = new Invoice(customer.getId().get(), positions);

					// TODO: You can do this better

					// Only for testing purposes
					PDFMaker tester = new PDFMaker();
					tester.makePDF(customer, positions);

					// IDEA: Use Database entries to create invoice (only on
					// purpose)

					stage.close();
				}
			}
		});

		// handle add Button
		this.addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Position pos = new Position(NewInvoiceController.this, NewInvoiceController.this.invoice);
				positions.add(pos);
				posBox.getChildren().add(pos.getHBox());
			}
		});

	}

	/**
	 * 
	 * @param position:
	 *            Removes a given position from invoice
	 */
	public void remove(Position position) {
		this.positions.remove(position);
	}

	/**
	 * 
	 * @param main:
	 *            References GUIMain
	 */
	public void setMain(GUIMain main) {
		this.main = main;
	}

	/**
	 * 
	 * @return Returns VBox with positions
	 */
	public VBox getPosBox() {
		return this.posBox;
	}

}
