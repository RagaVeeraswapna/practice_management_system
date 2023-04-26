package com.revature.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.Model.BasicPatientInfo;
import com.revature.Repository.PatientRepository;
import com.revature.pms.dto.BasicPatientInfoDto;

@Service
public class PatientInfoService {
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	public List<BasicPatientInfoDto> getPatientInfo(){
		List<BasicPatientInfoDto> dtoList = patientRepo.findAll().stream()
			    .map(entity -> modelMapper.map(entity, BasicPatientInfoDto.class))
			    .collect(Collectors.toList());
	return dtoList;
}

	public BasicPatientInfoDto getPatientById(int id){
		return modelMapper.map(patientRepo.findById(id).get(),BasicPatientInfoDto.class);
	}
	
	public BasicPatientInfoDto updateById(BasicPatientInfoDto basicpatientinfo, int id) {
		
		BasicPatientInfo basicInfo = patientRepo.findById(id).orElse(null);
		basicInfo.setEmail(basicpatientinfo.getEmail());
		basicInfo.setGender(basicpatientinfo.getGender());
		basicInfo.setFirstName(basicpatientinfo.getFirstName());
		basicInfo.setLastName(basicpatientinfo.getLastName());
		return modelMapper.map(patientRepo.saveAndFlush(basicInfo),BasicPatientInfoDto.class);	
	}
	public long getCount(){
		return patientRepo.count();
	}
	
}
