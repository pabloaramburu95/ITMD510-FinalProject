package controllers;

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
import javafx.scene.input.MouseEvent;
import models.Measure;
import models.Patient;
import models.User;
import models.daoModel;
import models.daoModelImpl;

public class PatientDetailsController {
	@FXML
	private TableView<Measure> tableView;
	@FXML
	private TableColumn<Measure, String> columnOne;
	@FXML
	private TableColumn<Measure, String> columnTwo;
	@FXML
	private TableColumn<Measure, String> columnThree;
	@FXML
	private TableColumn<Measure, String> columnFour;
	@FXML
	private TableColumn<Measure, String> columnFive;
	@FXML
	private Button logoutButton;
	@FXML
	private Button updateButton;
	@FXML
	private Label patientLabel;
	
	@FXML
	private Label oldTreatmentText;
	@FXML
	private TextField newTreatmentText;
	@FXML
	private Label dLabel;

	public void userInteraction(final models.LoginManager loginManager, String patient, String doctor)
			throws Exception {
		daoModel dao = new daoModelImpl();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		dLabel.setText("Welcome doctor " + doctor + "!");
		patientLabel.setText("This is the information for your patient: " + patient + ".");
		logoutButton.setOnAction(e -> loginManager.logout());
		String treatment = new String(dao.getTreatment(patient));
		oldTreatmentText.setText("Treatment: "+treatment);
		updateButton.setOnAction(e -> {
			try {
				if (newTreatmentText.getText() == null || newTreatmentText.getText().trim().equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText("Update treatment error");
					alert.setContentText("You must write a new treatment");
					alert.showAndWait();
				} else {
					oldTreatmentText.setText("Treatment: " + newTreatmentText.getText());
					dao.updateTreatment(patient, newTreatmentText.getText());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		logoutButton.setOnAction(e -> loginManager.logout());
		

		showTable(patient);
	}

	public void showTable(String patient) throws Exception {
		daoModel dao = new daoModelImpl();
		tableView.setVisible(true);
		tableView.refresh();
		ObservableList<Measure> list = FXCollections.observableArrayList();
		List<Measure> medidas = dao.getMeasuresFromPatient(patient);
		for (int i = 0; i < medidas.size(); i++) {
			String meas = String.valueOf(medidas.get(i).getMeasure());
			String m = String.valueOf(medidas.get(i).getMonth());
			String d = String.valueOf(medidas.get(i).getDay());
			String y = String.valueOf(medidas.get(i).getYear());
			String comment = String.valueOf(medidas.get(i).getComments());

			list.add(new Measure(meas, y, m, d, comment, patient));
			columnOne.setCellValueFactory(new PropertyValueFactory<Measure, String>("measure"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<Measure, String>("year"));
			columnThree.setCellValueFactory(new PropertyValueFactory<Measure, String>("month"));
			columnFour.setCellValueFactory(new PropertyValueFactory<Measure, String>("day"));
			columnFive.setCellValueFactory(new PropertyValueFactory<Measure, String>("comments"));
		}
		tableView.setItems(list);

	}

}
