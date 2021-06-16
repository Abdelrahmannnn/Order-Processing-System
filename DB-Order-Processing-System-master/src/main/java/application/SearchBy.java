package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SearchBy extends Pane {
	MainPage mainPage;
	private Pane root;

	public SearchBy() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchBy.fxml"));
		loader.setController(this);
		try {
			root = loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	public void show(MainPage main) {
		mainPage = main;
		main.ParentPane.getChildren().add(root);
	}

	@FXML
	private TextField Title;


	@FXML
	private TextField Author;

	@FXML
	private TextField Publisher;


	@FXML
	private TextField SellingPrice;

	@FXML
	private Text SellingPriceError;



	@FXML
	private ComboBox<String> Category;

	@FXML
	private Button OK;

	@FXML
	void okAction(ActionEvent event) {
		double Selling_price = 0.0;
		String Categories = "";
		if (!SellingPrice.getText().equals("")) {
			Selling_price = Double.parseDouble(SellingPrice.getText());
		}
		if (!Category.getSelectionModel().isEmpty()) {
			Categories = Category.getSelectionModel().getSelectedItem().toString();
		}

		DBConnector.getInstance().bookSearch(Title.getText(), Publisher.getText(), Categories,
				Selling_price, Author.getText());

		PassValues.setWhichBtb("Add To Cart");
		BookList bookList = new BookList();
		bookList.show(mainPage);
	}

	@FXML
	void priceCheckAction(KeyEvent event) {
		if (!SellingPrice.getText().matches("\\d*")) {
			SellingPriceError.setVisible(true);
			OK.setDisable(true);
		} else {
			if (!SellingPrice.getText().matches("")) {
				OK.setDisable(false);
			} else {
				SellingPriceError.setVisible(false);
				OK.setDisable(true);
			}
		}
	}



	@FXML
	void Has_value(KeyEvent event) {
		if (!Publisher.getText().matches("") || !Title.getText().matches("") || !Author.getText().matches("")) {
			OK.setDisable(false);
		} else {
			OK.setDisable(true);
		}
	}

	@FXML
	void categoryCheckAction(ActionEvent event) {
		if (!Category.getSelectionModel().isEmpty()) {
			OK.setDisable(false);
		} else {
			OK.setDisable(true);
		}
	}

	@FXML
	private void initialize() {
		Category.setItems(FXCollections.observableArrayList("Science", "Art", "Religion", "History", "Geography"));
		OK.setDisable(true);
	}
}