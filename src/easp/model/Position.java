package easp.model;

import java.text.DecimalFormat;

import easp.Checker;
import easp.InputException;
import easp.view.NewInvoiceController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Position {

	// TODO: use this if article store is used
	// private String articleID;

	private int amount;
	private String title;
	private String article;
	private String unit;
	private double pricePerUnit;
	private double price;
	private NewInvoiceController ctrl;
	private Checker checker;

	private TextField titleField;
	private TextField articleField;
	private TextField amountField;
	private TextField unitField;
	private TextField pricePerUnitField;
	private TextField priceField;

	public Position(NewInvoiceController ctrl) {
		this.title = "";
		this.amount = 0;
		this.article = "";
		this.unit = "Stk.";
		this.pricePerUnit = 0.00;
		this.price = 0.00;
		this.ctrl = ctrl;
		this.checker = new Checker();
	}

	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#0.00");
		return "Article: " + title + " -> " + article + ", Amount: " + String.valueOf(amount) + ", Unit: " + unit + ", Price per Unit: "
				+ String.valueOf(pricePerUnit) + ", Price: " + f.format(price) + "€ //";
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

		// Title Field
		titleField = new TextField();
		titleField.setFont(new Font(14));
		titleField.setPromptText("Titel (max. 25 Zeichen)");
		// Field for Articles
		articleField = new TextField();
		articleField.setFont(new Font(14));
		articleField.setPromptText("Artikel");
		articleField.setPrefWidth(350.0);
		articleField.setMaxWidth(1000.00);
		articleField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					articleField.setStyle("");
			}
		});

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
				// Computes price
				if (amountField.getText().equals("")) {
					amount = 0;
				} else {
					amount = Integer.parseInt(amountField.getText());
				}
				computePrice();
			}
		});
		amountField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					amountField.setStyle("");
			}
		});

		// Field for unit of one article
		unitField = new TextField();
		unitField.setFont(new Font(14));
		unitField.setPromptText("ME");
		unitField.setMaxWidth(60.0);
		unitField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					unitField.setStyle("");
			}
		});

		// Field for price per unit of article
		pricePerUnitField = new TextField();
		pricePerUnitField.setFont(new Font(14));
		pricePerUnitField.setPromptText("G-Preis");
		pricePerUnitField.setMaxWidth(80.0);
		pricePerUnitField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String entry = pricePerUnitField.getText();
				// Checks if number (or ",") is typed and checks if more than 2
				// digits after comma are typed
				if (!event.getCharacter().matches("\\d")) {
					if (event.getCharacter().matches(",")) {
						if (entry.contains(",")) {
							event.consume();
						}
					} else {
						event.consume();
					}
				} else {
					if (entry.contains(",") && entry.substring(entry.indexOf(",")).length() > 2) {
						event.consume();
					}
				}
				// Sets the entered value and computes the price
				if (!entry.equals("")) {
					entry = entry.replace(",", ".");
					if (entry.contains("€"))
						entry = entry.substring(0, entry.indexOf("€"));
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
		DecimalFormat f = new DecimalFormat("#0.00");
		pricePerUnitField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				// Show € symbol when focus is lost
				if (!newPropertyValue) {
					if (!pricePerUnitField.getText().equals("")) {
						if (pricePerUnitField.getText().contains("€")) {
							pricePerUnitField.setText(
									pricePerUnitField.getText().substring(0, pricePerUnitField.getText().indexOf("€")));
						}
						if (!pricePerUnitField.getText().contains(",")) {
							pricePerUnitField.setText(pricePerUnitField.getText().concat(".00"));
						}
						pricePerUnitField.setText(pricePerUnitField.getText().replace(",", "."));
						double pricePerUnit = Double.parseDouble(pricePerUnitField.getText());
						pricePerUnitField.setText(f.format(pricePerUnit) + "€");
					}
				} else {
					// Remove text when text field is focused and remove red
					// borders
					pricePerUnitField.setStyle("");
					pricePerUnitField.setText("");
				}
			}
		});

		// Field for end price. Will be computed automatically by computePrice()
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
				ctrl.remove(Position.this);
			}
		});

		box.getChildren().addAll(this.titleField, this.articleField, this.amountField, this.unitField, this.pricePerUnitField,
				this.priceField, deleteButton);
		return box;
	}

	public boolean setInformations() {
		boolean thrown = false;
		try {
			checker.checkTitle(titleField.getText());
		} catch (InputException e) {
			titleField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}
		try {
			checker.checkArticle(articleField.getText());
		} catch (InputException e) {
			articleField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}
		try {
			checker.checkAmount(amountField.getText());
		} catch (InputException e) {
			amountField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}
		try {
			checker.checkUnit(unitField.getText());
		} catch (InputException e) {
			unitField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}
		try {
			checker.checkPPU(pricePerUnitField.getText());
		} catch (InputException e) {
			pricePerUnitField.setStyle(
					"-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 5 5 5 5; -fx-background-radius: 5 5 5 5;");
			thrown = true;
		}
		if (thrown)
			return false;
		else {
			this.title = titleField.getText();
			this.article = articleField.getText();
			this.amount = Integer.parseInt(amountField.getText());
			String price = priceField.getText().substring(0, priceField.getText().indexOf("€"));
			price = price.replace(",", ".");
			this.price = Double.parseDouble(price);
			String pricePerUnitText;
			if (pricePerUnitField.getText().contains("€")) {
				pricePerUnitText = pricePerUnitField.getText().substring(0, pricePerUnitField.getText().indexOf("€"));
			} else {
				pricePerUnitText = pricePerUnitField.getText();
			}
			pricePerUnitText = pricePerUnitText.replace(",", ".");
			this.pricePerUnit = Double.parseDouble(pricePerUnitText);
			this.unit = unitField.getText();
			return true;
		}
	}

	public int getAmount() {
		return amount;
	}
	
	public String getTitle() {
		return title;
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

	public TextField getArticleField() {
		return articleField;
	}

	public TextField getAmountField() {
		return amountField;
	}

	public TextField getUnitField() {
		return unitField;
	}

	public TextField getPricePerUnitField() {
		return pricePerUnitField;
	}

	public TextField getPriceField() {
		return priceField;
	}

}
