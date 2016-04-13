package videoconverter.model;



public class User {


	private String username;
	
	private String firstName;
	private String lastName;

	private String password;
	private String birthdate;
	private String email;
	public User(String firstName, String lastName, String username,
			String password, String birthdate, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.birthdate = birthdate;
		this.email = email;
	}

	public User(){}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password
				+ ", birthdate=" + birthdate + ", email=" + email + "]";
	}

	

	
	
	
}
