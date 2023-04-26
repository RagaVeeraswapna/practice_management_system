package com.revature.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;
import com.revature.entity.Prescription;
import com.revature.entity.VisitDetails;
import com.revature.exception.VisitNotFound;
import com.revature.payload.PrescriptionDto;
import com.revature.repository.NurseRepo;
import com.revature.repository.PrescriptionRepository;
import com.revature.repository.VisitRepository;
import com.revature.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private NurseRepo nurseRepo;

	@Override
	public List<PrescriptionDto> getPrescriptionDetails(int visitId) {
		VisitDetails v = visitRepository.findById(visitId)
				.orElseThrow(() -> new VisitNotFound(String.format("Visit Id %d not found", visitId)));
		List<Prescription> medicines = prescriptionRepository.findAllByVisitId(v);
		return medicines.stream().map(medicine -> modelMapper.map(medicine, PrescriptionDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PrescriptionDto> saveVisit(List<PrescriptionDto> prescriptionDto) {
		List<Prescription> list = prescriptionDto.stream()
				.map(entity->modelMapper.map(entity,Prescription.class))
				.collect(Collectors.toList()); 
		List<PrescriptionDto> dtoList = prescriptionRepository.saveAll(list).stream()
			    .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
			    .collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public PrescriptionDto updatePrescription(PrescriptionDto prescriptionDto, int prescriptionId) {
		Prescription p = prescriptionRepository.findById(prescriptionId).get();
		p.setPrescriptionName(prescriptionDto.getPrescriptionName());
		p.setDosage(prescriptionDto.getDosage());
		p.setPrescriptionNotes(prescriptionDto.getPrescriptionNotes());
		prescriptionRepository.saveAndFlush(p);
		return modelMapper.map(p, PrescriptionDto.class);
	}

	@Override
	public void deletePrescription(int prescriptionId) {
		prescriptionRepository.deleteById(prescriptionId);

	}

	public List<Nurse> getNurseFromAuth() throws UnirestException {
		
		HttpResponse<String> response = Unirest.post("https://dev-ipfjfrntvhuq88xm.us.auth0.com/oauth/token")
				  .header("content-type", "application/json")
				  .body("{\"client_id\":\"zYOAGwF013k2DhZK0b4xuUohLV64j1TR\",\"client_secret\":\"K1NBFuYqxIZ0R5o7djsR5-RpcbuHSjXGSb8SWO_kJ7FuVXANZvd1jW0FNnVv63tf\",\"audience\":\"https://dev-ipfjfrntvhuq88xm.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
				  .asString();
		System.out.println(response.getBody());
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			System.out.println(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String value  = jsonNode.get("access_token").textValue();
		String request = "https://dev-ipfjfrntvhuq88xm.us.auth0.com/api/v2/users";
		HttpResponse<String> response1 = Unirest.get(request)
				.header("authorization",
						"Bearer "+value)
				.asString();
		System.out.println(response.getBody());
		ArrayList<Nurse> nurses = new ArrayList<>();
		try {
			jsonNode = objectMapper.readTree(response1.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonNode.size(); i++) {
			System.out.println(jsonNode.get(i) + "/n");
			Nurse nurse = new Nurse();
			String role = jsonNode.get(i).get("user_metadata").get("role").textValue();
			if (role.equalsIgnoreCase("Nurse")) {
				nurse.setNurseRole(role);
				nurse.setNurseEmail(jsonNode.get(i).get("email").textValue());
				nurse.setNurseName(jsonNode.get(i).get("name").textValue());
				nurses.add(nurse);
			}
		}
		return nurses;
	}
	public List<Nurse> addNurse(List<Nurse> nurses) {
		for (Nurse nurse : nurses) {
			if(!nurseRepo.existsById(nurse.getNurseEmail()))
				nurseRepo.save(nurse);
		}
		return nurseRepo.findAll();
	}
}
