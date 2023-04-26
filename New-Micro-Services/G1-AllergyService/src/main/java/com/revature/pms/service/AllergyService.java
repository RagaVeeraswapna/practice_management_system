package com.revature.pms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.revature.pms.model.Allergy;
import com.revature.pms.repo.AllergyRepository;

@Service
public class AllergyService {
	
	@Autowired
	private AllergyRepository allergyRepository;
	
	public List<Allergy> getAllergies() {
		List<Allergy> allergies = allergyRepository.findAll();
		return allergies;
	}
	
	public Allergy getAllergy(int id){
		Allergy allergy =  allergyRepository.findById(id).get();
		return allergy;
	}
	
	public List<Allergy> getPaginatedAllergy(int pageNo,int pageSize){
		return allergyRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
	}
}
