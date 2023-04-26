package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer>{
	@Query(value="select * from Patient where email=?1 and password=?2",nativeQuery=true)
	public Patient findPatientByEmailAndPassword(String email,String password);
	
	
	@Modifying
	@Query( value="update Patient set password=:password WHERE email=:email" ,nativeQuery = true)
	void updatePassword(@Param(value = "password") String password, @Param(value = "email") String email);
}
