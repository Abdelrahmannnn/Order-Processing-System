package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage primaryStage) throws IOException {
		Registration registration = new Registration();
		registration.showStage();
		if (PassValues.getClosedNot()) {
			MainPage mainPage = new MainPage();
			mainPage.showStage();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}