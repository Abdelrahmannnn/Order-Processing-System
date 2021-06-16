package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class MainPage {

	private final Stage thisStage;

	public MainPage() {
		thisStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
			loader.setController(this);
			thisStage.setScene(new Scene(loader.load()));
			thisStage.setResizable(false);
			thisStage.setTitle("Welcome to your book store data base");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private Button CheckOut;

	@FXML
	private Button ManageData;

	@FXML
	Pane ParentPane;
	@FXML
	public static Button Search;
	@FXML
	public Label userName;

	@FXML
	private void SearchAction(ActionEvent event) {
		PassValues.clearAllSearch();
		if (!ParentPane.getChildren().isEmpty()) {
			ParentPane.getChildren().remove(0);
		}
		SearchBy searchby = new SearchBy();
		searchby.show(this);
		CheckOut.setVisible(false);
	}

	@FXML
	void ManageAction(ActionEvent event) throws IOException {
		if (!ParentPane.getChildren().isEmpty()) {
			ParentPane.getChildren().remove(0);
		}
		Manager manager = new Manager();
		manager.show(this);
		CheckOut.setVisible(false);

	}

	@FXML
	void MyCartAction(ActionEvent event) {
		if (!ParentPane.getChildren().isEmpty()) {
			ParentPane.getChildren().remove(0);
		}
		PassValues.setWhichBtb("Delete From Cart");
		BookList bookList = new BookList();
		bookList.show(this);
		CheckOut.setVisible(true);
		if (PassValues.getBookCartISBN().isEmpty()) {
			CheckOut.setDisable(true);
		} else {
			CheckOut.setDisable(false);
		}
	}

	@FXML
	void editProfileAction(ActionEvent event) throws IOException {
		if (!ParentPane.getChildren().isEmpty()) {
			ParentPane.getChildren().remove(0);
		}
		Profile profile = new Profile();
		profile.show(this);
		CheckOut.setVisible(false);

	}

	@FXML
	void checkOutAction(ActionEvent event) throws IOException {
		Dialog<Pair<String, String>> dialog = new Dialog<>();

		ButtonType OK = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OK);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField Card = new TextField();
		Card.setPromptText("Card Number");
		TextField Date = new TextField();
		Date.setPromptText("Date");
		Label CardError = new Label("enter valid Card Number");
		grid.add(new Label("Credit Card Number:"), 0, 0);
		grid.add(Card, 1, 0);
		grid.add(CardError, 0, 1);
		grid.add(new Label("Expired Date:"), 0, 2);
		grid.add(Date, 1, 2);
		grid.add(new Label("write data in the following form year/month/day"), 1, 3);
		Node OKButton = dialog.getDialogPane().lookupButton(OK);
		OKButton.setDisable(true);
		CardError.setVisible(false);

		Card.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Card.getText().matches("\\d*")) {
				CardError.setVisible(true);
				cardCheck = false;
			} else {
				if (!Card.getText().matches("") && checkLuhn(Card.getText())) {
					CardError.setVisible(false);
					cardCheck = true;
				} else if (Card.getText().matches("")) {
					CardError.setVisible(false);
					cardCheck = false;
				} else {
					CardError.setVisible(true);
					cardCheck = false;
				}
			}
			if (dateCheck && cardCheck) {
				OKButton.setDisable(false);
			} else {
				OKButton.setDisable(true);
			}
		});

		Date.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!Date.getText().equals("")) {
				Date toDayDate = new Date();
				toDayDate = Calendar.getInstance().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date enteredDate = new Date();
				try {
					enteredDate = sdf.parse(Date.getText());
					if (enteredDate.after(toDayDate)) {
						CardError.setVisible(false);
						dateCheck = true;
					} else {
						CardError.setVisible(true);
						dateCheck = false;
					}
				} catch (ParseException e) {
				}

			} else {
				CardError.setVisible(false);
			}
			if (dateCheck && cardCheck) {
				OKButton.setDisable(false);
			} else {
				OKButton.setDisable(true);
			}
		});

		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> Card.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OK) {
				ParentPane.getChildren().remove(0);
				ParentPane.getChildren().clear();
				CheckOut.setVisible(false);
				DBConnector.getInstance().checkOut(PassValues.getBookCartISBN());
				PassValues.clearAllCart();
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Your choice: " + result.get());
		}
	}

	public void showStage() {
		if (PassValues.getprivilege().equals("manager")) {
			ManageData.setVisible(true);
		} else {
			ManageData.setVisible(false);
		}
		thisStage.showAndWait();
	}

	private static boolean checkLuhn(String cardNo) {
		return ( cardNo.length() == 16 );

	}

	@FXML
	public void initialize() {
		userName.setText("Welcome " + PassValues.getUserName() + " To BookStore");

	}

	private static Boolean cardCheck = false;
	private static Boolean dateCheck = false;

}