package com.revature.pms.model;

public class User {
	private String email;
	private String speciality;
	private String role;
	public User() {}
	
	public User(String email, String speciality, String name, String username,String role) {
		super();
		this.email = email;
		this.speciality = speciality;
		this.name = name;
		this.username = username;
		this.role = role;
	}
	public String  getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}



	private String name;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String username;
}
