package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AddModifyBook extends Pane {

	private Pane root;
	private static List<String> dataToModify = new ArrayList<String>();

	public AddModifyBook() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddModifyBook.fxml"));
		loader.setController(this);
		try {
			root = loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void show(Manager mananger, String whitchbutton) {
		mananger.mangpane.getChildren().add(root);
		if (whitchbutton.equals("Modify")) {
			getdata.setVisible(true);

		} else {
			add.setVisible(true);
			viewAddModifyBook();
		}
	}

	@FXML
	private Label CategoryLabel , TitleLabel , PriceLabel ,PublisherLabel , ThresholdLabel,AuthorsLabel ;


	@FXML
	private ComboBox<String> category;

	@FXML
	private MenuItem sc , re, art , his , geo ;


	@FXML
	private TextField price , pname ,  threshold  , title , isbn  ;



	@FXML
	private Text notification;

	@FXML
	Button add;

	@FXML
	Button getdata;

	@FXML
	Button modify;


	@FXML
	private Label Cplabel, QuantityLabel ;

	@FXML
	private TextField copies , orderQ;


	@FXML
	private Text error;

	@FXML
	private TextArea Authors;

	@FXML
	private Label ISBNLabel;

	private String currentISBN = null;

	@FXML
	void closedialog(ActionEvent event) {
	}

	@FXML
	void addnew(ActionEvent event) throws ParseException {
        error.setVisible(true);

        DBConnector db = DBConnector.getInstance();

		if (isbn.getText().isEmpty() || title.getText().isEmpty() || pname.getText().isEmpty()
				|| category.getSelectionModel().isEmpty() || price.getText().isEmpty()
				|| threshold.getText().isEmpty() || copies.getText().isEmpty() || orderQ.getText().isEmpty()
				|| Authors.getText().isEmpty()) {
            error.setVisible(true);

			error.setText("ERROR:Enter The missed data");

            return;
		} else if (!isNumeric(price.getText()) || !isNumeric(orderQ.getText()) || !isNumeric(threshold.getText())
				|| !isNumeric(copies.getText())) {
            error.setVisible(true);

			error.setText("ERROR: Please enter numerical values for threshold, price, copies and order quantity.");

        }
		List<String> authors = new ArrayList<String>();
		for (String line : Authors.getText().split("\\n")) {
			authors.add(line);
		}
		if (db.publisherExists(pname.getText())) {
			if (!db.Search_book(isbn.getText())) {
				db.addBook(isbn.getText(), title.getText(), pname.getText(),
						category.getSelectionModel().getSelectedItem().toString(), price.getText(), threshold.getText(),
						copies.getText(), orderQ.getText(), authors);
				error.setText(" Success !! ");
			} else {
                error.setVisible(true);

                error.setText("ERROR: There is already a book with this ISBN!");

            }
		} else {
            error.setVisible(true);

            error.setText("ERROR: There is no publisher with that name , Add him first");

        }

    }

	@FXML
	void getdata(ActionEvent event) {
        error.setVisible(true);

        if (isbn.getText().isEmpty() || !isNumeric(isbn.getText())) {
			error.setVisible(true);
			error.setText("ERROR: Please insert valid ISBN");

		} else {
			viewAddModifyBook();
			modify.setVisible(true);
			DBConnector db = DBConnector.getInstance();
			if (db.Search_book(isbn.getText())) {
				db.getbook(isbn.getText());

				dataToModify = PassValues.getDataToModify();
				title.setText(dataToModify.get(0));
				pname.setText(dataToModify.get(1));
				price.setText(dataToModify.get(2));
				category.setValue(dataToModify.get(3));
				copies.setText(dataToModify.get(4));
				threshold.setText(dataToModify.get(5));
				orderQ.setText(dataToModify.get(6));
				Authors.setText(dataToModify.get(7).replace(',', '\n'));
				currentISBN = isbn.getText();
			} else {

				error.setText("ERROR: This book wasn't found");
			}
		}
	}

	@FXML
	void modifydata(ActionEvent event) {

        DBConnector db = DBConnector.getInstance();
		if (currentISBN == null) {
			error.setText("ERROR: Please enter the ISBN first and click on get data ");
            error.setVisible(true);

            return;
		} else {
			if (isbn.getText().isEmpty() || title.getText().isEmpty() || pname.getText().isEmpty()
					 || category.getSelectionModel().isEmpty() || price.getText().isEmpty()
					|| threshold.getText().isEmpty() || orderQ.getText().isEmpty() || Authors.getText().isEmpty()) {
				error.setText("ERROR: Please enter missed data");
                error.setVisible(true);

                return;
			} else if (!isNumeric(price.getText()) || !isNumeric(orderQ.getText()) || !isNumeric(threshold.getText())) {
				error.setText("ERROR: Please enter numerical values for threshold, price and order quantity.");
                error.setVisible(true);

            } else if (!db.publisherExists(pname.getText())) {
				error.setText("ERROR: There is no publisher with that name , Add him first");
                error.setVisible(true);

            } else {
				List<String> authors = new ArrayList<String>();
				for (String line : Authors.getText().split("\\n")) {
					authors.add(line);
				}
				db.modifybook(isbn.getText(), title.getText(), pname.getText(),
						category.getSelectionModel().getSelectedItem().toString(), price.getText(), threshold.getText(),
						orderQ.getText(), authors);
                error.setVisible(true);
                error.setText(" Success Modified!");


            }
		}
	}

	private void viewAddModifyBook() {
		CategoryLabel.setVisible(true);
		TitleLabel.setVisible(true);
		TitleLabel.setStyle("-fx-text-fill: white");
		PriceLabel.setVisible(true);
		PublisherLabel.setVisible(true);
		ThresholdLabel.setVisible(true);
		AuthorsLabel.setVisible(true);
		QuantityLabel.setVisible(true);
		Cplabel.setVisible(true);
		isbn.setVisible(true);
		category.setVisible(true);
		price.setVisible(true);
		pname.setVisible(true);
		threshold.setVisible(true);
		title.setVisible(true);
		notification.setVisible(true);
		orderQ.setVisible(true);
		copies.setVisible(true);
		Authors.setVisible(true);
	}

	private static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@FXML
	private void initialize() {
		category.setItems(FXCollections.observableArrayList("Science", "Art", "Religion", "History", "Geography"));
	}
}
