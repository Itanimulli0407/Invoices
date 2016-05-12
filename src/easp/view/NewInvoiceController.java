package easp.view;

import java.util.ArrayList;

import easp.Checker;

//import java.util.List;

import easp.GUIMain;
import easp.InputException;
import easp.model.Position;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
	private Checker checker;
	private Stage stage;
	private GUIMain main;

	public NewInvoiceController() {

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void initialize() {
		this.checker = new Checker();

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
				for (Position pos : positions) {
					try {
						pos.setInformations();
						checker.checkPosition(pos);
					} catch (InputException e) {
						//TODO: DO THINGS
					}
				}
				stage.close();
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

	public void printLine(String line) {
		System.out.println(line);
	}

	public void setMain(GUIMain main) {
		this.main = main;
	}

	public VBox getPosBox() {
		return this.posBox;
	}

}
