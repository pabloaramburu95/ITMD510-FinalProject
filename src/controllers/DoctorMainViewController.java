package controllers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Measure;
import models.Patient;
import models.User;
import models.daoModel;
import models.daoModelImpl;

public class DoctorMainViewController {
	@FXML
	private ListView<String> tableView;
	@FXML
	private Button logoutButton;
	@FXML
	private Button patientsButton;
	@FXML
	private Button postsButton;
	@FXML
	private Button patientDetailButton;
	@FXML
	private TextField patienttext;
	@FXML
	private Label dLabel;
	@FXML
	private ImageView docImage;
	
	public void userInteraction(final models.LoginManager loginManager, String username, User session) throws Exception {
		Image image = new Image(new FileInputStream("src/application/images/fotoDoctor.png"));
		docImage.setImage(image);
		tableView.setVisible(false);
		patienttext.setVisible(false);
		patientDetailButton.setVisible(false);
		daoModel dao = new daoModelImpl();
		String doctor = username;
		dLabel.setText("Welcome doctor "+ doctor +"!");
		logoutButton.setOnAction(e -> loginManager.logout());
		List<String> patients = new ArrayList<String>(dao.getPatientsFromDoctor(doctor));
		patientsButton.setOnAction(e -> {
			try {
				viewPatients(patients, session);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		patientDetailButton.setOnAction(e->{
			String patientDetail = patienttext.getText();
			if (patientDetail == null|| patientDetail.trim().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Patient details error");
				alert.setContentText("You must introduce a patient.");
				alert.showAndWait();
			} else {
				try {
					loginManager.showPatientDetails(patientDetail, doctor);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
	}
	
	private ObservableList<String> list = FXCollections.observableArrayList();

	private void viewPatients(List<String> patients, User session) throws Exception {
		list.removeAll(list);
		tableView.setVisible(true);
		patienttext.setVisible(true);
		patientDetailButton.setVisible(true);
		for (int i = 0; i<patients.size(); i++) {
			String user = patients.get(i); 
			list.add(user);
		}
		tableView.getItems().clear();
		tableView.getItems().addAll(list);

	}
	@FXML
	private void displaySelected(MouseEvent event) {
		String patient = tableView.getSelectionModel().getSelectedItem();
		if(patient==null|| patient.isEmpty()) {
			patienttext.setText("nothing selected");
		}
		else {
			patienttext.setText(patient);
		}
	}
}
