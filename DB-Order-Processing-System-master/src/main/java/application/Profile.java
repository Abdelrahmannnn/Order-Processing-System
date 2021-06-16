package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

public class Profile extends Pane {
	MainPage mainPage;
	private Pane root;

	public Profile() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
		loader.setController(this);
		// viewData();
		try {
			root = loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void show(MainPage main) {
		mainPage = main;
		main.ParentPane.getChildren().add(root);
		viewData();
	}

	@FXML
	private TextField firstname ,phone ,email , shippingadd ,lastname;


	@FXML
	private PasswordField oldpassword ,newpassword;

	@FXML
	private Button save , back;

	@FXML
	private Text errorpass ,  errordata;

	private String first, last, emailadd, phonenum, shipadd;

	@FXML
	void editData(ActionEvent event) {
		if (firstname.getText().isEmpty()) {
			errordata.setText("Please enter first name first");
			return;
		} else {
			first = firstname.getText();
		}
		if (lastname.getText().isEmpty()) {
			errordata.setText("Please enter last name first");
			return;
		} else {
			last = lastname.getText();
		}
		if (email.getText().isEmpty() || !validEmail(email.getText())) {
			errordata.setText("Please enter a valid email first");
			return;
		} else {
			emailadd = email.getText();
		}
		if (phone.getText().isEmpty() || !isNumeric(phone.getText())) {
			errordata.setText("Please enter a valid phone number");
			return;
		} else {
			phonenum = phone.getText();
		}
		shipadd = shippingadd.getText();

		DBConnector db = DBConnector.getInstance();
		db.editData(first, last, emailadd, phonenum, shipadd);
		viewData();
	}

	void viewData() {
		firstname.setText(PassValues.getFirstName());
		lastname.setText(PassValues.getSecondName());
		email.setText(PassValues.getEmail());
		phone.setText(PassValues.getPhone());
		shippingadd.setText(PassValues.getAddress());
	}

	@FXML
	void changepassword(ActionEvent event) {
		DBConnector db = DBConnector.getInstance();
		if (!oldpassword.getText().isEmpty()) {
			if (oldpassword.getText().equals(db.getpass())) {
				if (!newpassword.getText().isEmpty()) {
					db.changepass(newpassword.getText());
				} else {
					errorpass.setText("Error: Please enter new password");
				}
			} else {
				errorpass.setText("Error: Please enter your valid password");
			}
		} else {
			errorpass.setText("Error: Please enter old password first");
		}
		oldpassword.clear();
		newpassword.clear();
	}

	private static boolean validEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	private static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}