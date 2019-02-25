package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class daoModelImpl implements daoModel {
	
	DBConnect con = new DBConnect();
	private Statement statement = null;
	private Statement statement2 = null;

	// CREATE METHODS
	@Override
	public void createTables() throws Exception, SQLException {
		
		try {
			System.out.println("------- CREATION OF TABLES -------");
			statement = con.getConnection().createStatement();
			String sql_patients = "CREATE TABLE patientsMySugar " + " (patientId int not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(20) UNIQUE, " + " password VARCHAR(20), " + " isAdmin VARCHAR(3), "
					+ "treatment VARCHAR(255), " + "doctor VARCHAR(20), " +" PRIMARY KEY ( patientId ))";

			statement.executeUpdate(sql_patients);
			System.out.println("Patients table created");
			
			String sql_doctors = "CREATE TABLE doctorsMySugar " + " (doctorId int not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(20), " + " password VARCHAR(20), " + " isAdmin VARCHAR(3), "
					+" PRIMARY KEY ( doctorId ))";

			statement.executeUpdate(sql_doctors);
			System.out.println("Doctors table created");

			
			String sql_measures = "CREATE TABLE measuresMySugar " + " (measureId int not NULL AUTO_INCREMENT, "
					+ " measure DOUBLE NOT NULL, " + " year int NOT NULL, " + " month int NOT NULL, " 
					+ " day int NOT NULL, " + "comments VARCHAR(255), " + " PRIMARY KEY ( measureId ), " 
					+ "patient VARCHAR(20))";
					//+ ", " + "FOREIGN KEY patientId REFERENCES patients(patientId)";

			statement.executeUpdate(sql_measures);
			System.out.println("Measures table created");
			
			String sql_posts = "CREATE TABLE postsMySugar " + " (postId int not NULL AUTO_INCREMENT, "
					+ " username VARCHAR(20), " + "post VARCHAR(255), " + " PRIMARY KEY ( postId ))"; 
			
			statement.executeUpdate(sql_posts);
			System.out.println("Posts table created");
			
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void insertUser(User u) throws Exception {
		System.out.println("------- INSERTION OF USER --------");
		try {
			statement = con.getConnection().createStatement();
			if (u instanceof Patient) {
				// the user is a patient
				String sql = "INSERT INTO patientsMySugar(username, password, isAdmin, treatment, doctor) " 
						+ "VALUES ('" + u.getUsername()+ "', '" + u.getPassword() + "', 'fal', '" + ((Patient) u).getTreatment() + "', '" + ((Patient) u).getDoctor() + "')";
				statement.executeUpdate(sql);
			} else {
				// the user is a doctor
				String sql = "INSERT INTO doctorsMySugar(username, password, isAdmin) " 
						+ "VALUES ('" + u.getUsername()+ "', '" + u.getPassword() + "', 'fal')";
				statement.executeUpdate(sql);
			}
			statement.close();
			System.out.println("User has been inserted");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Database Operations");
			alert.setContentText("This user has been registered!");
			alert.showAndWait();
			System.out.println("User: " + u.getUsername() + " has been registered correctly");
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Database Operations");
			alert.setContentText("ERROR!");
			alert.showAndWait();
		}
	}

	@Override
	public void insertMeasure(Measure m, User u) throws Exception {
		System.out.println("------- INSERTION OF MEASURE -------");
		try {
			statement = con.getConnection().createStatement();
			
			if (u instanceof Patient) {
				//Only a patient can upload a measure
				
				String sql = "INSERT INTO measuresMySugar(measure, year, month, day, comments, patient) " 
						+ "VALUES ('" + m.getMeasure()+ "', '" + m.getYear() + "', '" + m.getMonth() 
						+ "', '" + m.getDay() + "', '" + m.getComments() + "', '" + u.getUsername()
						+ "')";
				statement.executeUpdate(sql);
			}
			statement.close();
			System.out.println("Measure has been correctly inserted");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void insertAdminUser() throws Exception{
		try {
			statement = con.getConnection().createStatement();
			String sql = "INSERT INTO patientsMySugar(username, password, isAdmin) " 
					+ "VALUES ('admin', 'admin', 'tru')";
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void insertPost(Post p) throws Exception{
		try {
			System.out.println(p.getUsername());
			statement = con.getConnection().createStatement();
			String sql = "INSERT INTO postsMySugar(username, post) " 
					+ "VALUES ('" + p.getPost() + "', '" + p.getUsername()+"')";
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());

		}
	}
	// READ METHODS
	@Override
	public int returnIdUser(User u) throws Exception {
		int idUser = 0;
		try {
			statement = con.getConnection().createStatement();
			if (u instanceof Patient) {
				//User is a patient
				String sql = "SELECT patientID FROM patientsMySugar WHERE username='" + u.getUsername() + "' AND password='" + u.getPassword()+"'";
				ResultSet rs = statement.executeQuery(sql);
				rs.next();
				idUser = rs.getInt("patientId");
				statement.close();
			}else {
				//User is a doctor
				String sql = "SELECT doctorId FROM doctorsMySugar WHERE username='" + u.getUsername() + "' AND password='" + u.getPassword()+"'";
				ResultSet rs = statement.executeQuery(sql);
				rs.next();
				idUser = rs.getInt("doctorId");
				statement.close();
			}
			statement.close();
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
		return idUser;
	}

	@Override
	public int returnIdMeasure(Measure m) throws Exception {
		int idMeasure = 0;
		try {
			statement = con.getConnection().createStatement();
			String sql = "SELECT measureId FROM measuresMySugar WHERE measure='" + m.getMeasure() + "'AND patient='" 
					+ m.getPatient() + "' AND month='" + m.getMonth() + "' AND day='" + m.getDay() 
					+ "' AND year='" + m.getYear() + "'";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			idMeasure = rs.getInt("measureId");
			statement.close();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return idMeasure;
	}
	
	public String returnDoctor (User u) throws Exception{
		statement = con.getConnection().createStatement();
		try {
			String doctor = "";
			String sql = "SELECT doctor FROM patientsMySugar WHERE username='" + u.getUsername() + "'";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			doctor = rs.getString("doctor");
			//System.out.println("The doctor for: " + u.getUsername() +" is: " + doctor);
			statement.close();
			return doctor;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public List<Measure> getMeasuresFromPatient(User u) throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT * FROM measuresMySugar WHERE patient='" + u.getUsername() + "'";
			ResultSet rs = statement.executeQuery(sql);
			List<Measure> measures = new ArrayList<>();
			while (rs.next()) {
				Measure m = new Measure (rs.getString("measure"), rs.getString("year"), rs.getString("month"), 
						rs.getString("day"), rs.getString("comments"), rs.getString("patient"));
				measures.add(m);
			}
			statement.close();
			return measures;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public List<Measure> getMeasuresFromPatientYearMonthDay(User u, int y, int m, int d) throws Exception {
		statement = con.getConnection().createStatement();
		
		try {
			String sql = "SELECT * FROM measuresMySugar WHERE patient='" + u.getUsername() 
					+ " AND year='" + y + "' AND month='" + m + "' AND day='" + d + "'";
			ResultSet rs = statement.executeQuery(sql);
			List<Measure> measuresPYMD = new ArrayList<>();
			while (rs.next()) {
				Measure m2 = new Measure (rs.getString("measure"), rs.getString("year"), rs.getString("month"), 
						rs.getString("day"), rs.getString("comments"), rs.getString("patient"));
				measuresPYMD.add(m2);
			}
			statement.close();
			return measuresPYMD;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public List<String> getPatientsFromDoctor(String doctor) throws Exception{
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT username FROM patientsMySugar WHERE doctor='" + doctor +  "'";
			ResultSet rs = statement.executeQuery(sql);
			List <String> patients = new ArrayList<>();
			while (rs.next()) {
				String newPatientAdd = rs.getString("username");
				patients.add(newPatientAdd);
			}
			statement.close();
			return patients;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	@Override
	public String getTreatment(String username) throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String treatment = "";
			String sql = "SELECT treatment FROM patientsMySugar WHERE username='" + username + "'";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			treatment = rs.getString("treatment");
			//System.out.println("The treatment for: " + username +" is: " + treatment);
			statement.close();
			return treatment;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Measure> getMeasuresFromPatient(String patient) throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT * FROM measuresMySugar WHERE patient='" + patient + "'";
			ResultSet rs = statement.executeQuery(sql);
			List<Measure> measures = new ArrayList<>();
			while (rs.next()) {
				Measure m = new Measure (rs.getString("measure"), rs.getString("year"), rs.getString("month"), 
						rs.getString("day"), rs.getString("comments"), rs.getString("patient"));
				measures.add(m);
			}
			statement.close();
			return measures;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Patient> getPatients() throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT * FROM patientsMySugar";
			ResultSet rs = statement.executeQuery(sql);
			List <Patient> patients = new ArrayList<>();
			while (rs.next()) {
				Patient u = new Patient (rs.getString("username"), rs.getString("password"),
						false, rs.getString("treatment"), rs.getString("doctor"));
				patients.add(u);
			}
			statement.close();
			return patients;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Doctor> getDoctors() throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT * FROM doctorsMySugar";
			ResultSet rs = statement.executeQuery(sql);
			List <Doctor> docs = new ArrayList<>();
			while (rs.next()) {
				Doctor d = new Doctor (rs.getString("username"), rs.getString("password"),
						false);
				docs.add(d);
			}
			statement.close();
			return docs;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public List<Post> getPosts() throws SQLException{
		statement = con.getConnection().createStatement();
		try {
			String sql = "SELECT * FROM postsMySugar";
			ResultSet rs = statement.executeQuery(sql);
			List <Post> posts = new ArrayList<>();
			while (rs.next()) {
				Post p = new Post (rs.getString("username"), rs.getString("post"));
				posts.add(p);
			}
			statement.close();
			return posts;
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
		
	}
	
	// UPDATE METHODS
	@Override
	public void updateTreatment(String patient, String treatment) throws Exception {
		statement = con.getConnection().createStatement();
		try {
			String sql = "UPDATE patientsMySugar SET treatment='"+treatment+"' WHERE username='" + patient + "'";
			statement.executeUpdate(sql);
			//System.out.println("The updated treatment for: " + patient +" is: " + treatment);
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}	
	}
	
	// DELETE METHODS
	@Override
	public void deleteUser(User u) throws Exception {
		int idUser = returnIdUser(u);
		try {
			statement = con.getConnection().createStatement();
			statement2 = con.getConnection().createStatement();
			if (u instanceof Patient) {
				// user is a patient						
				String sql = "DELETE FROM patientsMySugar WHERE patientId = '" + idUser + "'";
				String sql2 = "DELETE FROM measuresMySugar WHERE patient='" + u.getUsername() +"'";
				statement.executeUpdate(sql);
				statement2.executeUpdate(sql2);
				System.out.println("The patient: " + u.getUsername() +"has been deleted.");
			} else {
				// user is a doctor
				String sql = "DELETE FROM doctorsMySugar WHERE doctorId = '" + idUser + "'";
				statement.executeUpdate(sql);
				System.out.println("The doctor: " + u.getUsername() +"has been deleted.");
			}
			statement.close();
			statement2.close();
			System.out.println("The user " + u.getUsername() +" has been deleted correctly");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void deleteMeasure(Measure m) throws Exception {
		int idMeasure = returnIdMeasure (m);
		try {
			statement = con.getConnection().createStatement();
			String sql = "DELETE FROM measuresMySugar WHERE measureId='" + idMeasure +"'";
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
