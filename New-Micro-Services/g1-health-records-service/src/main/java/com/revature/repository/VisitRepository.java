package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.entity.VisitDetails;

public interface VisitRepository extends JpaRepository<VisitDetails, Integer> {

	List<VisitDetails> findAllByPatientId(int p);
	
	VisitDetails findByAppointmentId(int id);
	
	 VisitDetails getLastVisitDetailsByPatientId(int patientId);
}