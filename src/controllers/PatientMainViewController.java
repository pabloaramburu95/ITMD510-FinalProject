package controllers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.User;
import models.Patient;
import models.daoModel;
import models.daoModelImpl;
import models.Measure;

public class PatientMainViewController {
	@FXML
	private Button logoutButton;
	@FXML
	private Button measuresButton;
	@FXML
	private Button postsButton;
	@FXML
	private Label userLabel;
	@FXML
	private Label doctorLabel;
	@FXML
	private Button treatmentButton;
	@FXML
	private ImageView patientImage;

	
	
	
	public void userInteraction(final models.LoginManager loginManager, String username, User session) throws Exception {
		Image image = new Image(new FileInputStream("src/application/images/est.png"));
		patientImage.setImage(image);
		daoModel dao = new daoModelImpl();
		String doctor = dao.returnDoctor(session);
		
		userLabel.setText("Welcome "+ username +"!");
		doctorLabel.setText("Your doctor is "+ doctor +".");
		
		measuresButton.setOnAction(e -> {
			try {
				loginManager.showMeasuresListView(username,session);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		logoutButton.setOnAction(e -> loginManager.logout());
		postsButton.setOnAction(e -> {
			try {
				loginManager.showPostsView(session);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		treatmentButton.setOnAction(e -> {
			try {
				showTreatment(session);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

	}

	private void showTreatment(User session) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMATION");
		alert.setHeaderText("Treatment detail");
		daoModel dao = new daoModelImpl();
		String treat = dao.getTreatment(session.getUsername());
		alert.setContentText("Your treatment is " + treat);
		alert.showAndWait();
	}
	
	
}
