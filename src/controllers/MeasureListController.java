package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import models.Measure;
import models.Patient;
import models.User;
import models.daoModel;
import models.daoModelImpl;

public class MeasureListController  {
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
	 private TableColumn<Measure, String> columnSix;
	
	
	@FXML
	private ListView<String> listView;

	@FXML
	private Button logoutButton;
	@FXML
	private Button deleteButton;
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
	private Button viewMeasuresButton;
	// Needed for uploading a measure
	@FXML
	private Button uploadMeasureButton;
	@FXML
	private TextField value;
	@FXML
	private TextField year;
	@FXML
	private TextField month;
	@FXML
	private TextField day;
	@FXML
	private TextField comments;

	public void userInteraction(final models.LoginManager loginManager, String username, User session)
			throws Exception {
		daoModel dao = new daoModelImpl();
		String doctor = dao.returnDoctor(session);
		tableView.setVisible(false);
		deleteButton.setVisible(false);

		//List<Measure> medidas = new ArrayList<Measure>(dao.getMeasuresFromPatient(session));

		doctorLabel.setText("Your doctor is " + doctor + ".");
		userLabel.setText("Welcome " + username + "!");
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
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		viewMeasuresButton.setOnAction(e -> {
			try {
				viewMeasures(dao, session);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		uploadMeasureButton.setOnAction(e -> {
			try {
				uploadMeasure(session);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		deleteButton.setOnAction(e -> {
			try {
				delete(dao, session);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

	}
	
	@FXML
	private void setDelete() {
		deleteButton.setVisible(true);
	}
	
	private void delete(daoModel dao, User session ) throws Exception {
		Measure m = tableView.getSelectionModel().getSelectedItem();
		dao.deleteMeasure(m);
		viewMeasures(dao, session);
		
	}
	private void viewMeasures(daoModel dao, User session) throws Exception {
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setVisible(true);
		String patient = session.getUsername();
		ObservableList<Measure> list = FXCollections.observableArrayList();
		List<Measure> med= dao.getMeasuresFromPatient(session.getUsername());
		for (int i = 0; i<med.size(); i++) {
			String meas = String.valueOf(med.get(i).getMeasure());
			String m = String.valueOf(med.get(i).getMonth());
			String d= String.valueOf(med.get(i).getDay());
			String y= String.valueOf(med.get(i).getYear());
			String comment = String.valueOf(med.get(i).getComments());
			
			list.add(new Measure(meas, y, m, d, comment, patient));
			columnOne.setCellValueFactory(new PropertyValueFactory<Measure,String>("measure"));
			columnTwo.setCellValueFactory(new PropertyValueFactory<Measure,String>("year"));
			columnThree.setCellValueFactory(new PropertyValueFactory<Measure,String>("month"));
			columnFour.setCellValueFactory(new PropertyValueFactory<Measure,String>("day"));
			columnFive.setCellValueFactory(new PropertyValueFactory<Measure,String>("comments"));
			columnSix.setCellValueFactory(new PropertyValueFactory<Measure,String>("patient"));
		}
		tableView.setItems(list);
		tableView.refresh();

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

	private boolean isNotNumber(String str) {
		boolean resultado;
        try {
            Integer.parseInt(str);
            resultado = false;
        } catch (NumberFormatException excepcion) {
            resultado = true;
        }

        return resultado;
	}
	private boolean isNotDouble(String str) {
		boolean resultado;
        try {
            Double.parseDouble(str);
            resultado = false;
        } catch (NumberFormatException excepcion) {
            resultado = true;
        }

        return resultado;
	}
	private void uploadMeasure(User session) throws Exception {
		String patient = session.getUsername();
		daoModel dao = new daoModelImpl();
		if (value.getText() == null || year.getText() == null || month.getText() == null || day.getText() == null
				|| comments.getText() == null || value.getText().trim().equals("") || year.getText().trim().equals("")
				|| month.getText().trim().equals("") || day.getText().trim().equals("")
				|| comments.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Upload measurement error");
			alert.setContentText("Some field was not filled");
			alert.showAndWait();
			return;
		}
		double valueNumber = Double.parseDouble((value.getText()));
		int monthNumber = Integer.parseInt(month.getText());
		int dayNumber = Integer.parseInt(day.getText());
		int yearNumber = Integer.parseInt(year.getText());

		System.out.println(valueNumber);
		if ( isNotDouble(value.getText()) || isNotNumber(year.getText()) || isNotNumber(month.getText())
				|| isNotNumber(day.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Upload measurement error");
			alert.setContentText("Fields value, month, day and year must be numbers");
			alert.showAndWait();
			return;
		}
		if(valueNumber<0 || monthNumber<0 || dayNumber<0 || yearNumber<2000 
				|| monthNumber>12 || dayNumber>31 || yearNumber > 2018) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Upload measurement");
			alert.setHeaderText("Error");
			alert.setContentText("Fields value, month, day and year must be positive and real dates");
			alert.showAndWait();
			return;
		}
		
		Measure measure = new Measure(value.getText(), year.getText(), month.getText(), day.getText(),
				comments.getText(), patient);
		dao.insertMeasure(measure, session);
		viewMeasures(dao, session);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMATION");
		alert.setHeaderText("Your measure was succesfully uploaded ");
		alert.setContentText("Value: " + value.getText() + " from " + month.getText() + "/" + day.getText() + "/"
				+ year.getText() + ".");
		alert.showAndWait();	
	}
}
