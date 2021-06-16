package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;



public class Registration {
	private final Stage thisStage;

	public Registration() {
		thisStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registration.fxml"));
			loader.setController(this);
			thisStage.setScene(new Scene(loader.load()));
			thisStage.setResizable(false);
			thisStage.setTitle("Book Store Data Base");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private TextField firstname , email , usernameup , phone ,shippingadd ;


	@FXML
	private PasswordField passwordup , passwordin;;

	@FXML
	private Button signup;

	@FXML
	private TextField usernamein , lastname;


	@FXML
	private Button signin;


	@FXML
	private Text userErrorLogin , PassErrorLogin ,userErrorSignup , firstErrorSignup , secondErrorSignup ,
			PassErrorSignup , Text ,addressErrorSignup , emailErrorSignup , phoneErrorSignup , signuperror , signinerror;



	@FXML
	public void reg(ActionEvent event) throws SQLException {


		boolean flag = false;
		if (usernameup.getText().isEmpty()) {
			userErrorSignup.setVisible(true);
			flag = true;
		}
		if (firstname.getText().isEmpty() || firstname.getText().matches(".*\\d.*")) {
			firstErrorSignup.setVisible(true);
			flag = true;
		}
		if (lastname.getText().isEmpty() || lastname.getText().matches(".*\\d.*")) {
			secondErrorSignup.setVisible(true);
			flag = true;
		}
		if (email.getText().isEmpty() || !validEmail(email.getText())) {
			emailErrorSignup.setVisible(true);
			flag = true;
		}
		if (phone.getText().isEmpty() || !isNumeric(phone.getText())) {
			phoneErrorSignup.setVisible(true);
			flag = true;
		}
		if (passwordup.getText().isEmpty()) {
			PassErrorSignup.setVisible(true);
			flag = true;
		}
		if (shippingadd.getText().isEmpty()) {
			addressErrorSignup.setVisible(true);
			flag = true;
		}
		if (!flag) {
			String firstName = firstname.getText();
			String lastName = lastname.getText();
			String userName = usernameup.getText();
			String emailadd = email.getText();
			String password = passwordup.getText();
			String phonen = phone.getText();
			String shippingadds = shippingadd.getText();
			DBConnector DB = new DBConnector();
			if (DB.Exist(userName)) {
				signuperror.setText("The user name is already exist! Try another one ");
			} else {
				DB.insertRecord(userName, password, firstName, lastName, emailadd, phonen, shippingadds);
				thisStage.close();
				PassValues.setClosedNot(true);
			}
		}
	}

	@FXML
	public void login(ActionEvent event) throws SQLException {
		if (usernamein.getText().isEmpty()) {
			userErrorLogin.setVisible(true);
			PassErrorLogin.setVisible(true);
			return;
		}

		if (usernamein.getText().isEmpty()) {
			userErrorLogin.setVisible(true);
			return;
		}
		if (passwordin.getText().isEmpty()) {
			PassErrorLogin.setVisible(true);
			return;
		}

		String username = usernamein.getText();
		String password = passwordin.getText();

		DBConnector DB = new DBConnector();
		boolean flag = DB.is_valid(username, password);
		if (!flag) {
			signinerror.setText(" User Name or Password are not correct !");
			return;
		} else {
			thisStage.close();
			PassValues.setClosedNot(true);
		}

	}

	public static void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	private static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean validEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	@FXML
	void TestAddressSignup(KeyEvent event) {
		addressErrorSignup.setVisible(false);
	}

	@FXML
	void TestEmailSignup(KeyEvent event) {
		emailErrorSignup.setVisible(false);
	}

	@FXML
	void TestFirstSignup(KeyEvent event) {
		firstErrorSignup.setVisible(false);
	}

	@FXML
	void TestPassLogin(KeyEvent event) {
		PassErrorLogin.setVisible(false);

	}

	@FXML
	void TestPassSignup(KeyEvent event) {
		PassErrorSignup.setVisible(false);
	}

	@FXML
	void TestPhoneSignup(KeyEvent event) {
		phoneErrorSignup.setVisible(false);
	}

	@FXML
	void TestSecondSignup(KeyEvent event) {
		secondErrorSignup.setVisible(false);
	}

	@FXML
	void TestUserLogIn(KeyEvent event) {
		userErrorLogin.setVisible(false);
	}

	@FXML
	void TestUserSignup(KeyEvent event) {
		userErrorSignup.setVisible(false);
	}

	public void showStage() {
		thisStage.showAndWait();
	}

}