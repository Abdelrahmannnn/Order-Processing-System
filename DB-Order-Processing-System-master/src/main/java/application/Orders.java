package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Orders extends Pane {

	private Pane root;

	private static int counter;
	private static List<Integer> ID_Order;
	private static List<String> Date;
	private static List<Integer> Quantity;
	private static List<String> ISBN;

	public Orders() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Orders.fxml"));
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
	private TextField quantity , isbn ;


	@FXML
	private Text quantity_of_error, ISBN_error;


	@FXML
	private GridPane Grid0, Grid1, Grid2, Grid3, Grid4, Grid5, Grid6, Grid7, Grid8, Grid9;

	@FXML
	private Label ID0, ID1, ID2, ID3, ID4, ID5, ID6, ID7, ID8, ID9;

	@FXML
	private Label ISBN0, ISBN1, ISBN2, ISBN3, ISBN4, ISBN5, ISBN6, ISBN7, ISBN8, ISBN9;

	@FXML
	private Label Quantity0, Quantity1, Quantity2, Quantity3, Quantity4, Quantity5, Quantity6, Quantity7, Quantity8,
			Quantity9;

	@FXML
	private Line Line0, Line1, Line2, Line3, Line4, Line5, Line6, Line7, Line8, Line9;

	@FXML
	private Label Date0, Date1, Date2, Date3, Date4, Date5, Date6, Date7, Date8, Date9;

	@FXML
	private Button Confirm0, Confirm1, Confirm2, Confirm3, Confirm4, Confirm5, Confirm6, Confirm7, Confirm8, Confirm9;

	@FXML
	private Button Previous;

	@FXML
	private Button Next;

	@FXML
	void confirmAction(ActionEvent event) {
		String IDBtn = ((Node) event.getSource()).getId();
		String pattern = "(Confirm)(\\d+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(IDBtn);
		if (m.find()) {
			int index = counter + Integer.parseInt(m.group(2));
			DBConnector db = new DBConnector();
			db.confirmOrder(ID_Order.get(index));
			PassValues.removeFromOrder(index);
			initialize();
		}
	}

	@FXML
	void nextAction(ActionEvent event) {
		counter += 10;
		Next.setDisable(true);
		viewOrders(counter);
		if (ISBN.size() - counter < 0) {
			Next.setDisable(true);
		}
		Previous.setDisable(false);
	}

	@FXML
	void previousAction(ActionEvent event) {
		counter -= 10;
		Next.setDisable(false);
		viewOrders(counter);
		if (counter < 10) {
			Previous.setDisable(true);
		}
		Next.setDisable(false);
	}

	int RowViewer0(int counter) {
		if (counter < ISBN.size()) {
			Grid0.setVisible(true);
			Line0.setVisible(true);
			ID0.setText(ID_Order.get(counter).toString());
			ISBN0.setText(ISBN.get(counter));
			Date0.setText(Date.get(counter));
			Quantity0.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid0.setVisible(false);
			Line0.setVisible(false);
		}
		return counter;
	}

	int RowViewer1(int counter) {
		if (counter < ISBN.size()) {
			Grid1.setVisible(true);
			Line1.setVisible(true);
			ID1.setText(ID_Order.get(counter).toString());
			ISBN1.setText(ISBN.get(counter));
			Date1.setText(Date.get(counter));
			Quantity1.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid1.setVisible(false);
			Line1.setVisible(false);
		}
		return counter;
	}

	int RowViewer2(int counter) {
		if (counter < ISBN.size()) {
			Grid2.setVisible(true);
			Line2.setVisible(true);
			ID2.setText(ID_Order.get(counter).toString());
			ISBN2.setText(ISBN.get(counter));
			Date2.setText(Date.get(counter));
			Quantity2.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid2.setVisible(false);
			Line2.setVisible(false);
		}
		return counter;
	}

	int RowViewer3(int counter) {
		if (counter < ISBN.size()) {
			Grid3.setVisible(true);
			Line3.setVisible(true);
			ID3.setText(ID_Order.get(counter).toString());
			ISBN3.setText(ISBN.get(counter));
			Date3.setText(Date.get(counter));
			Quantity3.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid3.setVisible(false);
			Line3.setVisible(false);
		}
		return counter;
	}

	int RowViewer4(int counter) {
		if (counter < ISBN.size()) {
			Grid4.setVisible(true);
			Line4.setVisible(true);
			ID4.setText(ID_Order.get(counter).toString());
			ISBN4.setText(ISBN.get(counter));
			Date4.setText(Date.get(counter));
			Quantity4.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid4.setVisible(false);
			Line4.setVisible(false);
		}
		return counter;
	}

	int RowViewer5(int counter) {
		if (counter < ISBN.size()) {
			Grid5.setVisible(true);
			Line5.setVisible(true);
			ID5.setText(ID_Order.get(counter).toString());
			ISBN5.setText(ISBN.get(counter));
			Date5.setText(Date.get(counter));
			Quantity5.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid5.setVisible(false);
			Line5.setVisible(false);
		}
		return counter;
	}

	int RowViewer6(int counter) {
		if (counter < ISBN.size()) {
			Grid6.setVisible(true);
			Line6.setVisible(true);
			ID6.setText(ID_Order.get(counter).toString());
			ISBN6.setText(ISBN.get(counter));
			Date6.setText(Date.get(counter));
			Quantity6.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid6.setVisible(false);
			Line6.setVisible(false);
		}
		return counter;
	}

	int RowViewer7(int counter) {
		if (counter < ISBN.size()) {
			Grid7.setVisible(true);
			Line7.setVisible(true);
			ID7.setText(ID_Order.get(counter).toString());
			ISBN7.setText(ISBN.get(counter));
			Date7.setText(Date.get(counter));
			Quantity7.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid7.setVisible(false);
			Line7.setVisible(false);
		}
		return counter;
	}

	int RowViewer8(int counter) {
		if (counter < ISBN.size()) {
			Grid8.setVisible(true);
			Line8.setVisible(true);
			ID8.setText(ID_Order.get(counter).toString());
			ISBN8.setText(ISBN.get(counter));
			Date8.setText(Date.get(counter));
			Quantity8.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid8.setVisible(false);
			Line8.setVisible(false);
		}
		return counter;
	}

	int RowViewer9(int counter) {
		if (counter < ISBN.size()) {
			Grid9.setVisible(true);
			Line9.setVisible(true);
			ID9.setText(ID_Order.get(counter).toString());
			ISBN9.setText(ISBN.get(counter));
			Date9.setText(Date.get(counter));
			Quantity9.setText(Quantity.get(counter).toString());
			counter++;
		} else {
			Grid9.setVisible(false);
			Line9.setVisible(false);
		}
		return counter;
	}

	public void viewOrders(int counter) {
		counter = RowViewer0(counter);
		counter = RowViewer1(counter);
		counter = RowViewer2(counter);
		counter = RowViewer3(counter);
		counter = RowViewer4(counter);
		counter = RowViewer5(counter);
		counter = RowViewer6(counter);
		counter = RowViewer7(counter);
		counter = RowViewer8(counter);
		counter = RowViewer9(counter);

		if (ISBN.size() - counter != 0) {
			Next.setDisable(false);
		}
	}

	@FXML
	void placeorder(ActionEvent event) {
		DBConnector db = DBConnector.getInstance();
		if (quantity.getText().isEmpty()) {
			quantity_of_error.setText("Please enter the quantity ");
			quantity_of_error.setVisible(true);
			return;
		} else if (!isNumeric(quantity.getText())) {
			quantity_of_error.setText("Please enter valid number for quantity");
			quantity_of_error.setVisible(true);
			return;
		} else {
			quantity_of_error.setVisible(false);
		}

		if (isbn.getText().isEmpty()) {
			ISBN_error.setText("Please enter the book ISBN");
			ISBN_error.setVisible(true);
			return;
		} else if (!db.Search_book(isbn.getText())) {
			ISBN_error.setText("Book not even exist please add its data first");
			ISBN_error.setVisible(true);
			return;
		} else {
			ISBN_error.setVisible(false);
		}
		db.addorder(isbn.getText(), quantity.getText());
		db.getAllOrders();
		initialize();

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
	public void initialize() {
		ID_Order = PassValues.getOrderID();
		ISBN = PassValues.getOrderISBN();
		Quantity = PassValues.getOrderQuantity();
		Date = PassValues.getOrderDate();

		Next.setDisable(true);
		Previous.setDisable(true);
		counter = 0;
		viewOrders(0);
	}
}
