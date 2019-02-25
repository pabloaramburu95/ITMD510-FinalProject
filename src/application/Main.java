package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.LoginManager;
import models.daoModel;
import models.daoModelImpl;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {

			daoModel dao = new daoModelImpl();
			//dao.createTables();
			//dao.insertAdminUser();
			Scene scene = new Scene(new StackPane());
			LoginManager loginManager = new LoginManager(scene);
			loginManager.showLoginScreen();
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
			stage.setMinWidth(stage.getWidth());
	        stage.setMinHeight(stage.getHeight());
	        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			System.out.println("Error occurred while inflating view: " + e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
