package models;

public class Patient extends User {
	
	protected String treatment;
	protected String doctor;
	
	public Patient(String username, String password, boolean isAdmin, String treatment, String doctor) {
		super(username, password, isAdmin);
		this.treatment=treatment;
		this.doctor = doctor;
	}
	
	public String getTreatment () {
		return treatment;
	}
	
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public String getDoctor () {
		return doctor;
	}
	
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	
}
