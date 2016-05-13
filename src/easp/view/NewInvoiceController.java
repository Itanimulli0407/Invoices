package easp.view;

import java.util.ArrayList;

import easp.Checker;

//import java.util.List;

import easp.GUIMain;
import easp.PDFTester;
import easp.model.Customer;
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

	public NewInvoiceController() {
		
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setCustomer(Customer customer){
		this.customer = customer;
	}

	@FXML
	public void initialize() {
		positions = new ArrayList<Position>();
		
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
				if (!thrown){
					for (Position pos : positions) {
						System.out.println(pos.toString());
					}
					// Only for testing purposes
					PDFTester tester = new PDFTester();
					tester.testPdf(customer, positions);
					stage.close();
				}
			}
		});

		// handle add Button
		this.addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Position pos = new Position(NewInvoiceController.this);
				positions.add(pos);
				posBox.getChildren().add(pos.getHBox());
			}
		});

	}
	
	public void remove(Position position) {
		this.positions.remove(position);
	}

	public void setMain(GUIMain main) {
		this.main = main;
	}

	public VBox getPosBox() {
		return this.posBox;
	}

}
