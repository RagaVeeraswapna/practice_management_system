package com.revature.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.repository.PatientRepository;
import com.revature.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public PatientDto loginService(String email, String password) {
		Patient patient=patientRepository.findPatientByEmailAndPassword(email,password);
		if(patient!=null) {
			return modelMapper.map(patient, PatientDto.class);
		}
		else {
			return null;
		}
	}
	
	public void updatepssword(String email,String password) {
		patientRepository.updatePassword(password,email);
	}
}
