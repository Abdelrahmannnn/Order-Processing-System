package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Manager extends Pane {
	MainPage mainPage;
	private Pane root;

	public Manager() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Manager.fxml"));
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
	Pane mangpane;

	@FXML
	void addnewbook(ActionEvent event) {
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		AddModifyBook addModifyBook = new AddModifyBook();
		addModifyBook.show(this, "Add");

	}

	@FXML
	void modifyexistingbook(ActionEvent event) {
		/// open frame as dialog
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		AddModifyBook addModifyBook = new AddModifyBook();
		addModifyBook.show(this, "Modify");
	}

	@FXML
	void placeneworder(ActionEvent event) {
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		DBConnector db = new DBConnector();
		db.getAllOrders();
		Orders order = new Orders();
		order.show(this);
	}

	@FXML
	void promotion(ActionEvent event) {
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		Users user = new Users();
		user.show(this);
	}

	@FXML
	void addpublisher(ActionEvent event) {
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		Publisher publisher = new Publisher();
		publisher.show(this);
	}

	@FXML
	private void initialize() {
	}

	@FXML
	void reports(ActionEvent event) {
		if (!mangpane.getChildren().isEmpty()) {
			mangpane.getChildren().remove(0);
		}
		Reports reports = new Reports();
		reports.show(this);
	}
}