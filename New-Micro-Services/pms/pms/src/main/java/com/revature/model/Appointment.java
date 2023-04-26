package com.revature.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	private String reason;
	private String date;
	private String acceptance;
	private int patientId;
	private String physcianEmail;
	private String submissionDate;
	public  Appointment() {
	}
	public Appointment(int id, String reason, String date, String acceptance, int patientId, String physcianEmail,
			String submissionDate) {
		super();
		this.id = id;
		this.reason = reason;
		this.date = date;
		this.acceptance = acceptance;
		this.patientId = patientId;
		this.physcianEmail = physcianEmail;
		this.submissionDate = submissionDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getPhyscianEmail() {
		return physcianEmail;
	}
	public void setPhyscianEmail(String physcianEmail) {
		this.physcianEmail = physcianEmail;
	}
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
}
