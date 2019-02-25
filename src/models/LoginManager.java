package models;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

import controllers.PatientMainViewController;
import controllers.PostsViewController;
import controllers.adminViewController;
import controllers.DoctorMainViewController;

/*
import controllers.LoginController;
import controllers.MainViewController;
import controllers.ProductListController;
import controllers.ProductViewController;
*/
//import application.Product;

import controllers.LoginController;
import controllers.MeasureListController;
import controllers.PatientDetailsController;
import models.User;

/** Manages control flow for the whole application */
public class LoginManager {
	private Scene scene;
	private User session;

	public LoginManager(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated. Will
	 * show the main application screen.
	 * 
	 * @throws Exception
	 */
	public void authenticated(User session, String username) throws Exception {
		showMainView(username, session);
		System.out.println("user: " + session.getUsername() + " pass: " + session.getPassword());
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	/**
	 * Callback method invoked to show the login application screen.
	 */
	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.initManager(this);

		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Callback method invoked to show the Main View screen.
	 */
	public void showMainView(String username, User session) {
		if (session instanceof Patient && !session.isAdmin) {
			// Load Patient Main View
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/patientMainView.fxml"));
				scene.setRoot((Parent) loader.load());
				PatientMainViewController controller = loader.<PatientMainViewController>getController();
				controller.userInteraction(this, username, session);
			} catch (Exception ex) {
				Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else if (session instanceof Doctor && !session.isAdmin) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/doctorMainView.fxml"));
				scene.setRoot((Parent) loader.load());
				DoctorMainViewController controller = loader.<DoctorMainViewController>getController();
				controller.userInteraction(this, username, session);
			} catch (Exception ex) {
				Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else if (session.isAdmin) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/adminView.fxml"));
				scene.setRoot((Parent) loader.load());
				adminViewController controller = loader.<adminViewController>getController();
				controller.userInteraction(this, username, session);
			} catch (Exception ex) {
				Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	public void showMeasuresListView(String username, User session) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/measuresView.fxml"));
			scene.setRoot((Parent) loader.load());
			MeasureListController controller = loader.<MeasureListController>getController();
			controller.userInteraction(this, username, session);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void showPatientDetails(String patient, String doctor) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/patientDetailsView.fxml"));
			scene.setRoot((Parent) loader.load());
			PatientDetailsController controller = loader.<PatientDetailsController>getController();
			controller.userInteraction(this, patient, doctor);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void showPostsView(User session) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/postsView.fxml"));
			scene.setRoot((Parent) loader.load());
			PostsViewController controller = loader.<PostsViewController>getController();
			controller.userInteraction(this, session);
		} catch (IOException e) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}