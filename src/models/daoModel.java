package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface daoModel {
	
	// CREATE METHODS
	public void createTables() throws Exception, SQLException;
	public void insertUser(User u) throws Exception;
	public void insertMeasure(Measure m, User u) throws Exception;
	public void insertAdminUser() throws Exception;
	public void insertPost(Post p) throws Exception;
	
	// READ METHODS
	public int returnIdUser(User u) throws Exception;
	public int returnIdMeasure(Measure m) throws Exception;
	public String returnDoctor (User u) throws Exception;
	public List<Measure> getMeasuresFromPatient(User u) throws Exception;
	public List<Measure> getMeasuresFromPatientYearMonthDay(User u, int y, int m, int d) throws Exception;
	public List<String> getPatientsFromDoctor(String doctor) throws Exception;
	public String getTreatment(String u) throws Exception;
	public List<Measure> getMeasuresFromPatient(String patient) throws Exception;
	public List<Patient> getPatients() throws Exception;
	public List<Doctor> getDoctors() throws Exception;
	public List<Post> getPosts() throws Exception;

	
	// UPDATE METHODS
	public void updateTreatment(String patient, String treatment) throws Exception;
	
	// DELETE METHODS
	public void deleteUser(User u) throws Exception;
	public void deleteMeasure(Measure m) throws Exception;


	


}
