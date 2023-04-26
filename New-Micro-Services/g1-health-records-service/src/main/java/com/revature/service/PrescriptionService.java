package com.revature.service;

import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;
import com.revature.payload.PrescriptionDto;

public interface PrescriptionService {
	
	public List<PrescriptionDto> saveVisit(List<PrescriptionDto> prescriptionDto);
	
	public List<PrescriptionDto> getPrescriptionDetails(int visitId);
	
	public PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto, int prescriptionId);
	
	public void deletePrescription(int prescriptionId);
	
	public List<Nurse> getNurseFromAuth() throws UnirestException;
	
	public List<Nurse> addNurse(List<Nurse> nurses);
	
}