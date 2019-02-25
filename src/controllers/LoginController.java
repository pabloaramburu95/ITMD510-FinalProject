package controllers;

import javafx.fxml.FXML;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

import models.daoModel;
import models.Patient;
import models.Doctor;
import models.User;

public class LoginController {

	@FXML
	private TextField user;
	@FXML
	private PasswordField password;
	@FXML
	private TextField doctor;
	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;
	@FXML
	private CheckBox checkBox;
	@FXML
	private Label lblError;
	@FXML
	private Label welcome;
	@FXML
	private ImageView logo;

	private boolean isDoctor = false;
	String sessionID = null;
	String username = null;
	User session;

	public void initManager(final models.LoginManager loginManager) throws FileNotFoundException {
		
		Image image = new Image(new FileInputStream("src/application/images/LOGO.png"));
		logo.setImage(image);
		loginButton.setOnAction((event) -> {
			if (user.getText() == null || user.getText().trim().equals("") || password.getText() == null
					|| password.getText().trim().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Login error");
				alert.setContentText("You must fill username and password");
				alert.showAndWait();
				return;
			}
			try {
				session = authorize();
				username = session.getUsername();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (username != null) {
				try {
					loginManager.authenticated(session, session.getUsername());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		checkBox.setOnAction((event) -> {
			isDoctor = checkBox.isSelected();
		});

		registerButton.setOnAction((event) -> {
			if ( (!isDoctor) && (user.getText() == null || user.getText().trim().equals("") 
					|| password.getText() == null || password.getText().trim().equals("") 
					|| doctor.getText() == null || doctor.getText().trim().equals("")) ) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText("Registration error");
					alert.setContentText("You must fill username, password and doctor");
					alert.showAndWait();
					return;
				
			} else if(user.getText() == null || user.getText().trim().equals("") || password.getText() == null
					|| password.getText().trim().equals("")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Registration error");
				alert.setContentText("You must fill username and password");
				alert.showAndWait();
				return;
				
			}
			try {
				register();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private User authorize() throws Exception {
		int id = 0;
		User u;
		// Call method that returns ask in DB if username and pass exists
		daoModel dao = new models.daoModelImpl();
		// dao.showTable("sellers_ar");
		
		if (isDoctor == false) {
			u = new Patient(user.getText(), password.getText(), false, "", doctor.getText());
		} else {
			u = new Doctor(user.getText(), password.getText(), false);
		}
		
		if (user.getText().trim().equals("admin") & password.getText().trim().equals("admin")) {
			u.setAdmin(true);
		}
		try {
			id = dao.returnIdUser(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Login error");
			alert.setContentText("The user does not exist in the database");
			alert.showAndWait();
			//System.out.println("The user does not exist in the database");
		} else {
			//System.out.println("The user exists in the database");
			// sessionID = generateSessionID();
			return u;
		}
		return null;
	}

	private void register() throws Exception {
		daoModel dao = new models.daoModelImpl();
		User u;
		if (isDoctor == false) {
			u = new Patient(user.getText(), password.getText(), false, "", doctor.getText());
		} else {
			u = new Doctor(user.getText(), password.getText(), false);
		}
		dao.insertUser(u);

	}

}
