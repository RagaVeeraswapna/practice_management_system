package com.revature.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.model.Appointment;

public interface AppointmentInterface extends JpaRepository<Appointment, Integer>{

	@Query( value="Select * FROM Appointment  WHERE physcian_email=?1 AND date=?2 AND acceptance=?3" ,nativeQuery = true)
	List<Appointment> findBystatusanddate(@PathVariable("physicianEmail") String physcianEmail,@PathVariable("date") String date,@PathVariable("acceptance") String acceptance);
	
	
	List<Appointment> findByAcceptance( String status,PageRequest page);


	
	@Query(value="select * from Appointment where patient_id=?1 And acceptance=?2",nativeQuery = true)
	List<Appointment> findByPatient_ID(@PathVariable("patient_id") Integer id,String status);
	
	@Modifying
	@Query( value="update Appointment set acceptance=:status WHERE id=:id" ,nativeQuery = true)
	void updatebyid(@Param(value = "status") String status, @Param(value = "id") Integer id);
	
	public List<Appointment> findByPatientId(int id);
	
	public Integer deleteById(int id);
	
	@Query(value = "select count(acceptance) from appointment where acceptance = 'pending' and physcian_email=?1",nativeQuery = true)
	public long countPendingAppointment(@PathVariable String email);
	
	@Query(value = "select count(acceptance) from appointment where physcian_email=?1",nativeQuery = true)
	public long countAppointment(@PathVariable String email);
	
	@Query(value = "select count(acceptance) from appointment where acceptance = 'acceptance' and physcian_email=?1",nativeQuery = true)
	public long countAcceptedAppointment(@PathVariable String email);

	@Query(value= "select count(acceptance) from appointment where acceptance = 'completed' and patient_id = ?1",nativeQuery = true)
	public long countCompletedAppointmentByPatientId(@PathVariable int id);
	
	@Query(value= "select count(acceptance) from appointment where acceptance = 'pending' and patient_id = ?1",nativeQuery = true)
	public long countPendingAppointmentByPatientId(@PathVariable int id);
	
	@Query(value= "select count(acceptance) from appointment where acceptance = 'acceptance'",nativeQuery = true)
	public long countAllAcceptanceAppointment();
	
	public List<Appointment> findByPatientIdAndAcceptanceOrderByDateDesc(int id, String date,PageRequest request);
	
	
}
