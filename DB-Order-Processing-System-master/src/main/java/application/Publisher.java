package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Publisher {

	private Pane root;
	private ArrayList<String> addresses = new ArrayList<String>();
	private ArrayList<String> phones = new ArrayList<String>();

	public Publisher() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Publisher.fxml"));
		loader.setController(this);
		try {
			root = loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void show(Manager manager) {
		manager.mangpane.getChildren().add(root);
	}

	@FXML
	private TextArea Address, Phone;

	@FXML
	private Button Add;

	@FXML
	private TextField Name;

	@FXML
	private Text error;

	@FXML
	void checkDuplicatedAddresses(KeyEvent event) {
		List<String> address = new ArrayList<String>();
		addresses.clear();
		for (String line : Address.getText().split("\\n")) {
			if (address.contains(line)) {
				error.setText("ERROR: Duplicated Address");
				Add.setDisable(true);
			} else {
				address.add(line);
				addresses.add(line);
				Add.setDisable(false);
				error.setText("");
			}
		}

	}

	@FXML
	void checkDuplicatedPhones(KeyEvent event) {
		List<String> phone = new ArrayList<String>();
		phones.clear();
		for (String line : Phone.getText().split("\\n")) {
			if (phone.contains(line)) {
				error.setText("ERROR: Duplicated Phone");
				Add.setDisable(true);
			} else {
				phone.add(line);
				phones.add(line);
				Add.setDisable(false);
				error.setText("");
			}
		}
	}

	@FXML
	void add(ActionEvent event) {
		DBConnector db = DBConnector.getInstance();

		if (Name.getText().isEmpty()) {
			error.setText("Error: Please enter publisher name.");
		} else if (db.publisherExists(Name.getText())) {
			error.setText("Error: This Name is Already Exist.");
		} else if (Address.getText().isEmpty()) {
			error.setText("Error: Please enter at least one address");
		} else if (Phone.getText().isEmpty()) {
			error.setText("Error: Please enter at least one phone");
		} else {
			db.addPublisher(Name.getText(), phones, addresses);
			error.setText("Publisher added");
			Address.clear();
			Phone.clear();
			Name.clear();
		}
	}

}