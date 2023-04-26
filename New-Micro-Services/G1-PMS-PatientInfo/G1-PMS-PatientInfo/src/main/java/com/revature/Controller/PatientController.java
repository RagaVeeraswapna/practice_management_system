package com.revature.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.Model.BasicPatientInfo;
import com.revature.Service.PatientInfoService;
import com.revature.pms.dto.BasicPatientInfoDto;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins	 =  "http://localhost:4200")
public class PatientController {

	@Autowired
	private PatientInfoService patientInfoService; 
	
	@GetMapping("/patient")
	public List<BasicPatientInfoDto> getall(){
		return patientInfoService.getPatientInfo();
	}
	@GetMapping("/patient/{id}")
	public BasicPatientInfoDto getpatient(@PathVariable int id) {
		return patientInfoService.getPatientById(id);
	}
	@PutMapping("/patient/{id}")
	public BasicPatientInfoDto updatePatient(@RequestBody BasicPatientInfoDto basic,@PathVariable int id) {
		return patientInfoService.updateById(basic, id);
	}
	@GetMapping("/patient/count")
	public long getCount(){
		return patientInfoService.getCount();
	}
}
