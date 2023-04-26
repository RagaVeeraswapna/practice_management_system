package com.revature.pms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALLERGY")
public class Allergy {
	@Column(name  = "ALLERGY_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int allergyId; 
	
	@Column(name = "ALLERGY_NAME")
	private String allergyName;
	
	@Column(name="ALLERGY_DESCRIPTION")
	private String allergyDescription;

	@Override
	public String toString() {
		return "Allergy [allergyId=" + allergyId + ", allergyName=" + allergyName + ", allergyDescription="
				+ allergyDescription + "]";
	}

	public int getAllergyId() {
		return allergyId;
	}
	
	public void setAllergyId(int allergyId) {
		this.allergyId = allergyId;
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}

	public String getAllergyDescription() {
		return allergyDescription;
	}

	public void setAllergyDescription(String allergyDescription) {
		this.allergyDescription = allergyDescription;
	}
}
