package com.revature.service;

import java.util.List;

import com.revature.entity.Prescription;
import com.revature.payload.PrescriptionDto;

public interface PrescriptionService {

	public PrescriptionDto savePrescription(int visitId, Prescription prescription);

	public List<PrescriptionDto> getPrescriptionDetails(int visitId);

	public void deletePrescription(int prescriptionId);
}