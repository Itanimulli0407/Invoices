package easp.model;

import java.text.DecimalFormat;

import easp.view.NewInvoiceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Position {

	// TODO: use this if article store is used
	// private String articleID;

	private int amount;
	private String article;
	private String unit;
	private double pricePerUnit;
	private double price;
	private NewInvoiceController ctrl;

	private TextField articleField;
	private TextField amountField;
	private TextField unitField;
	private TextField pricePerUnitField;
	private TextField priceField;

	public Position(NewInvoiceController ctrl) {
		this.amount = 0;
		this.article = "";
		this.unit = "Stück";
		this.pricePerUnit = 0.00;
		this.price = 0.00;
		this.ctrl = ctrl;

	}

	protected void computePrice() {
		double price = amount * pricePerUnit;
		DecimalFormat f = new DecimalFormat("#0.00"); 
		priceField.setText(f.format(price) + "€");
	}

	public HBox getHBox() {
		HBox box = new HBox();
		box.setPadding(new Insets(10, 20, 10, 20));
		box.setSpacing(10.0);

		// Field for Articles
		articleField = new TextField();
		articleField.setFont(new Font(14));
		articleField.setPromptText("Artikel");
		articleField.setPrefWidth(480.0);
		articleField.setMaxWidth(1000.00);

		// Field for amount of articles
		amountField = new TextField();
		amountField.setFont(new Font(14));
		amountField.setPromptText("M");
		amountField.setMaxWidth(60.0);
		amountField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// Checks if number is typed
				if (!event.getCharacter().matches("\\d")) {
					event.consume();
				}
			}
		});
		amountField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				//Computes price
				if (amountField.getText().equals("")){
					amount = 0;
				} else {
					amount = Integer.parseInt(amountField.getText());
				}
				computePrice();
			}

		});

		// Field for unit of one article
		unitField = new TextField();
		unitField.setFont(new Font(14));
		unitField.setPromptText("ME");
		unitField.setMaxWidth(60.0);

		// Field for price per unit of article
		pricePerUnitField = new TextField();
		pricePerUnitField.setFont(new Font(14));
		pricePerUnitField.setPromptText("G-Preis");
		pricePerUnitField.setMaxWidth(80.0);
		pricePerUnitField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String entry = pricePerUnitField.getText();
				// Checks if number (or ",") is typed and checks if more than 2 digits after comma are typed
				if (!event.getCharacter().matches("\\d")) {
					if (event.getCharacter().matches(",")) {
						if (entry.contains(",")) {
							event.consume();
						}
					} else {
						event.consume();
					}
				} else {
					if (entry.contains(",") && entry.substring(entry.indexOf(",")).length()>2) {
						event.consume();
					}
				}
				// Sets the entered value and computes the price
				if (!entry.equals("")) {
					entry = entry.replace(",", ".");
					pricePerUnit = Double.parseDouble(entry);
				} else {
					pricePerUnit = 0.00;
				}
				computePrice();
			}
		});
		pricePerUnitField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String entry = pricePerUnitField.getText();
				// Sets the entered value and computes the price
				if (!entry.equals("")) {
					entry = entry.replace(",", ".");
					pricePerUnit = Double.parseDouble(entry);
				} else {
					pricePerUnit = 0.00;
				}
				computePrice();
			}
		});

		// Fiel for end price. Will be computed automatically
		priceField = new TextField();
		priceField.setFont(new Font(14));
		priceField.setEditable(false);
		priceField.setText("0,00€");
		priceField.setMaxWidth(100.0);

		Button deleteButton = new Button();
		deleteButton.setText("-");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ctrl.getPosBox().getChildren().remove(box);
			}
		});

		box.getChildren().addAll(this.articleField, this.amountField, this.unitField, this.pricePerUnitField,
				this.priceField, deleteButton);
		return box;
	}
	
	public void setInformations() {
		this.article = articleField.getText();
		this.amount = Integer.parseInt(amountField.getText());
		this.price = Double.parseDouble(priceField.getText());
		this.pricePerUnit = Double.parseDouble(pricePerUnitField.getText());
		this.unit = unitField.getText();
	}

	public int getAmount() {
		return amount;
	}

	public String getArticle() {
		return article;
	}

	public String getUnit() {
		return unit;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public double getPrice() {
		return price;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
