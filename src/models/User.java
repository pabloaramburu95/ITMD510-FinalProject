package models;

public abstract class User {
	
	/**
	 * 
	 * ID from User
	 * Username
	 * Password
	 * IsAdmin?
	 *
	 */
	protected int id;
	protected String username;
	protected String password;
	protected boolean isAdmin;
	
	/**
	 * 
	 * Constructor from User
	 *
	 */
	public User(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;

	}
	/**
	 * 
	 * Getters and Setters from User
	 *
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


}

