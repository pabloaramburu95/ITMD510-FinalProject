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
import models.Doctor;
import models.Measure;
import models.Patient;
import models.User;
import models.daoModel;
import models.daoModelImpl;

public class adminViewController {
	@FXML
	private TableView<Patient> tablePatients;
	@FXML
	private TableColumn<Patient, String> usernamePatient;
	@FXML
	private TableColumn<Patient, String> passwordPatient;
	@FXML
	private TableColumn<Patient, String> treatmentPatient;
	@FXML
	private TableColumn<Patient, String> doctorPatient;
	@FXML
	private TableView<Doctor> tableDoctors;
	@FXML
	private TableColumn<Doctor, String> usernameDoc;
	@FXML
	private TableColumn<Doctor, String> passwordDoc;
	@FXML
	private Button logoutButton;;
	@FXML
	private Button selectedUserButton;
	@FXML
	private TextField selectedUserText;
	@FXML
	private Label adminLabel;
	@FXML
	private Label tableLabelP;
	@FXML
	private Label tableLabelD;

	public void userInteraction(final models.LoginManager loginManager, String username, User session)
			throws Exception {

		daoModel dao = new daoModelImpl();
		adminLabel.setText("Welcome admin!");
		fillTables(dao);
		logoutButton.setOnAction(e -> loginManager.logout());
		selectedUserButton.setOnAction(e -> {
			try {
				deleteSelectedUser();
				fillTables(dao);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	User selected;

	private void deleteSelectedUser() throws Exception {
		daoModel dao = new daoModelImpl();
		dao.deleteUser(selected);
		tableDoctors.refresh();
		tablePatients.refresh();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMATION");
		alert.setHeaderText("User deleted");
		String userDeleted = selected.getUsername();
		alert.setContentText("The user " + userDeleted + " has been deleted from database.");
		alert.showAndWait();

	}

	@FXML
	private void displaySelectedPatient(MouseEvent event) {
		Patient user = tablePatients.getSelectionModel().getSelectedItem();
		if (user == null || user.getUsername().trim().equals("")) {
			selectedUserText.setText("nothing selected");
		} else {
			selectedUserText.setText(user.getUsername());
		}
		selected = user;
	}

	@FXML
	private void displaySelectedDoctor(MouseEvent event) {
		Doctor user = tableDoctors.getSelectionModel().getSelectedItem();
		if (user == null || user.getUsername().trim().equals("")) {
			selectedUserText.setText("nothing selected");
		} else {
			selectedUserText.setText(user.getUsername());
		}
		selected = user;
	}

	private void fillTables(daoModel dao) throws Exception {
		tablePatients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableDoctors.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tablePatients.refresh();
		tableDoctors.refresh();

		List<Patient> patients = dao.getPatients();
		List<Doctor> doctors = dao.getDoctors();
		ObservableList<Patient> patientsList = FXCollections.observableArrayList();
		ObservableList<Doctor> docList = FXCollections.observableArrayList();
		for (int i = 0; i < patients.size(); i++) {
			String u = String.valueOf(patients.get(i).getUsername());
			String p = String.valueOf(patients.get(i).getPassword());
			String t = String.valueOf(patients.get(i).getTreatment());
			String d = String.valueOf(patients.get(i).getDoctor());
			patientsList.add(new Patient(u, p, false, t, d));
			usernamePatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("username"));
			passwordPatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("password"));
			treatmentPatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("treatment"));
			doctorPatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("doctor"));
		}
		tablePatients.setItems(patientsList);

		for (int i = 0; i < doctors.size(); i++) {
			String u = String.valueOf(doctors.get(i).getUsername());
			String p = String.valueOf(doctors.get(i).getPassword());
			docList.add(new Doctor(u, p, false));
			usernameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("username"));
			passwordDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("password"));

		}
		tableDoctors.setItems(docList);
	}
}
