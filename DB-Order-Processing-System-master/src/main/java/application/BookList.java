package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookList {

	private static List<String> Categories;
	private static List<String> ISBN;
	private static List<Double> Selling_price;
	private static List<String> Publishers;
	private static List<String> Titles;
	private static List<ArrayList<String>> Authors;
	private static int counter = 0;
	private Pane root;

	public BookList() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookList.fxml"));
		loader.setController(this);
		try {
			root = loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void show(MainPage main) {
		main.ParentPane.getChildren().add(root);
	}

	@FXML
	private GridPane Grid0, Grid1, Grid2, Grid3, Grid4, Grid5, Grid6, Grid7, Grid8, Grid9;

	@FXML
	private Label Label0, Label1, Label2, Label3, Label4, Label5, Label6, Label7, Label8, Label9;

	@FXML
	private Button Detail0, Detail1, Detail2, Detail3, Detail4, Detail5, Detail6, Detail7, Detail8, Detail9;

	@FXML
	private Button Add0, Add1, Add2, Add3, Add4, Add5, Add6, Add7, Add8, Add9;

	@FXML
	private Line Line0, Line1, Line2, Line3, Line4, Line5, Line6, Line7, Line8, Line9;

	@FXML
	private Button Previous , Next;

	@FXML
	private Text feedback;

	@FXML
	void nextAction(ActionEvent event) {
		counter += 10;
		Next.setDisable(true);
		viewBooks(counter);
		if (ISBN.size() - counter < 0) {
			Next.setDisable(true);
		}
		Previous.setDisable(false);
	}

	@FXML
	void previousAction(ActionEvent event) {
		counter -= 10;
		Next.setDisable(false);
		viewBooks(counter);
		if (counter < 10) {
			Previous.setDisable(true);
		}
		Next.setDisable(false);
	}

	int RowViewer0(int counter) {
		if (counter < ISBN.size()) {
			Grid0.setVisible(true);
			Line0.setVisible(true);
			Label0.setText(Titles.get(counter));
			Add0.setText(PassValues.getwbutton());
			Label0.setStyle("-fx-text-fill: white");
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
			Label1.setText(Titles.get(counter));
			Label1.setStyle("-fx-text-fill: white");
			Add1.setText(PassValues.getwbutton());
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
			Label2.setText(Titles.get(counter));
			Label2.setStyle("-fx-text-fill: white");
			Add2.setText(PassValues.getwbutton());
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
			Label3.setText(Titles.get(counter));
			Label3.setStyle("-fx-text-fill: white");
			Add3.setText(PassValues.getwbutton());
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
			Label4.setText(Titles.get(counter));
			Label4.setStyle("-fx-text-fill: white");
			Add4.setText(PassValues.getwbutton());
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
			Label5.setText(Titles.get(counter));
			Label5.setStyle("-fx-text-fill: white");
			Add5.setText(PassValues.getwbutton());
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
			Label6.setText(Titles.get(counter));
			Label6.setStyle("-fx-text-fill: white");
			Add6.setText(PassValues.getwbutton());
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
			Label7.setText(Titles.get(counter));
			Label7.setStyle("-fx-text-fill: white");
			Add7.setText(PassValues.getwbutton());
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
			Label8.setText(Titles.get(counter));
			Label8.setStyle("-fx-text-fill: white");
			Add8.setText(PassValues.getwbutton());
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
			Label9.setText(Titles.get(counter));
			Label9.setStyle("-fx-text-fill: white");
			Add9.setText(PassValues.getwbutton());
			counter++;
		} else {
			Grid9.setVisible(false);
			Line9.setVisible(false);
		}
		return counter;
	}

	public void viewBooks(int counter) {
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

		if (ISBN.size() - counter > 0) {
			Next.setDisable(false);
		}
	}

	@FXML
	void addRemoveAction(ActionEvent event) {
		String IDBtn = ((Node) event.getSource()).getId();
		String pattern = "(Add)(\\d+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(IDBtn);
		if (m.find()) {
			int index = counter + Integer.parseInt(m.group(2));
			if (PassValues.getwbutton().equals("Add To Cart")) {
				PassValues.setBookCartTitle(Titles.get(index));
				PassValues.setBookCartPublisher(Publishers.get(index));
				PassValues.setBookCartISBN(ISBN.get(index));
				PassValues.setBookCartCategory(Categories.get(index));
				PassValues.setBookCartSellingPrice(Selling_price.get(index));
				PassValues.setBookCartAuthors(Authors.get(index));
				feedback.setText("Added ! ");
			} else {
				PassValues.removeFromCart(index);
			}
		}
		viewBooks(counter);
	}

	private void viewMyCart() {
		Publishers = PassValues.getBookCartPublisher();
		Titles = PassValues.getBookCartTitle();
		Categories = PassValues.getBookCartCategory();
		ISBN = PassValues.getBookCartISBN();
		Selling_price = PassValues.getBookCartPrice();
		Authors = PassValues.getBookCartAuthors();
	}

	private void viewSearchResult() {
		Publishers = PassValues.getPublisher();
		Titles = PassValues.getTitle();


		Categories = PassValues.getCategory();

		ISBN = PassValues.getISBN();
		Selling_price = PassValues.getSellingPrice();
		Authors = PassValues.getAuthors();
	}

	@FXML
	void detailAction(ActionEvent event) {
		String n = ((Node) event.getSource()).getId();
		String pattern = "(Detail)(\\d+)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(n);
		if (m.find()) {
			int index = counter + Integer.parseInt(m.group(2));
			Dialog<String> dialog = new Dialog<>();
			ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			grid.add(new Label("Title:"), 0, 0);
			grid.add(new Label(Titles.get(index)), 1, 0);
			grid.add(new Label("Author(s):"), 0, 1);
			String combineAuthers = "";
			for (int i = 0; i < Authors.get(index).size(); i++) {
				combineAuthers += Authors.get(index).get(i);
				if (i < Authors.get(index).size() - 1) {
					combineAuthers += ", ";
				}
			}
			grid.add(new Label(combineAuthers), 1, 1);
			grid.add(new Label("Publisher:"), 0, 2);
			grid.add(new Label(Publishers.get(index)), 1, 2);

			grid.add(new Label("Selling price:"), 0, 4);
			grid.add(new Label(Selling_price.get(index).toString()), 1, 4);
			grid.add(new Label("Category:"), 0, 5);
			grid.add(new Label(Categories.get(index)), 1, 5);
			dialog.getDialogPane().setContent(grid);
			dialog.showAndWait();
		}
	}

	@FXML
	public void initialize() {
		Next.setDisable(true);
		Previous.setDisable(true);
		if (PassValues.getwbutton().equals("Delete From Cart")) {
			viewMyCart();
		} else {
			viewSearchResult();
		}
		viewBooks(0);
	}
}