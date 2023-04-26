package com.revature.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;
import com.revature.entity.VisitDetails;
import com.revature.payload.PrescriptionDto;
import com.revature.payload.TestDto;
import com.revature.payload.VisitDto;
import com.revature.service.PrescriptionService;
import com.revature.service.TestService;
import com.revature.service.VisitService;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class HealthRecordsController {
	@Autowired
	private VisitService visitService;

	@Autowired
	private TestService testService;

	@Autowired
	private PrescriptionService prescriptionService;

	@GetMapping("/nurse")
	public ResponseEntity<List<Nurse>> getAllNurse() {
		try {
			return new ResponseEntity<List<Nurse>>(prescriptionService.addNurse(prescriptionService.getNurseFromAuth()),
					HttpStatus.OK);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<List<Nurse>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// get visits details
	@GetMapping("/patient/{patientId}/visits")
	public ResponseEntity<List<VisitDto>> getAllVisits(@PathVariable(name = "patientId") int patientId) {
		return new ResponseEntity<>(visitService.getAllVisits(patientId), HttpStatus.OK);
	}

	// save visits
	@PostMapping("/patient/{patientId}/visits")
	public ResponseEntity<VisitDto> saveVisit(@PathVariable(name = "patientId") int patientId,
			@RequestBody VisitDto visitDto) {
		return new ResponseEntity<>(visitService.saveVisit(patientId, visitDto), HttpStatus.CREATED);
	}

	// delete visit
	@DeleteMapping("/visitdetails/{visitid}")
	public ResponseEntity<String> deleteVisit(@PathVariable(name = "visitid") int visitId) {
		visitService.deleteVisit(visitId);
		return new ResponseEntity<>("Visit deleted Successfully", HttpStatus.ACCEPTED);
	}

	// update visit
	@PutMapping("/visitdetails/{visitid}")
	public ResponseEntity<VisitDto> updateAppointment(@RequestBody VisitDto visitDto,
			@PathVariable(name = "visitid") int visitId) {
		return new ResponseEntity<>(visitService.updateVisit(visitDto, visitId), HttpStatus.ACCEPTED);
	}

	// get all test details
	@GetMapping("/tests/{visitId}")
	public ResponseEntity<List<TestDto>> getTestDetails(@PathVariable(name = "visitId") int visitId) {
		return new ResponseEntity<>(testService.getTestDetails(visitId), HttpStatus.OK);

	}

	// save tests
	@PostMapping("/visitdetails/tests")
	public ResponseEntity<List<TestDto>> saveTest(@RequestBody List<TestDto> testDto) {
		return new ResponseEntity<>(testService.saveVisit(testDto), HttpStatus.CREATED);
	}

	// delete test
//	@DeleteMapping("/testdetails/{testid}")
//	public ResponseEntity<String> deleteTest(@PathVariable(name = "testid") int testId) {
//		testService.deleteTest(testId);
//		return new ResponseEntity<>("Test deleted Successfully", HttpStatus.ACCEPTED);
//	}

	// update test
//	@PutMapping("/testdetails/{testid}")
//	public ResponseEntity<TestDto> updateTest(@RequestBody TestDto testDto, @PathVariable(name = "testid") int testId) {
//		return new ResponseEntity<>(testService.updateTest(testDto, testId), HttpStatus.ACCEPTED);
//	}

	// get all prescription details
	@GetMapping("/prescriptions/{visitId}")
	public ResponseEntity<List<PrescriptionDto>> getPrescriptionDetails(@PathVariable int visitId) {
		return new ResponseEntity<>(prescriptionService.getPrescriptionDetails(visitId), HttpStatus.OK);
	}
	
	// save prescription
	@PostMapping("/visitdetails/prescription")
	public ResponseEntity<List<PrescriptionDto>> savePrescription(
			@RequestBody List<PrescriptionDto> prescriptionDto) {
		return new ResponseEntity<>(prescriptionService.saveVisit(prescriptionDto), HttpStatus.CREATED);
	}

	// delete prescription
//	@DeleteMapping("/prescription/{prescriptionid}")
//	public ResponseEntity<String> deletePrescription(@PathVariable(name = "prescriptionid") int prescriptionId) {
//		prescriptionService.deletePrescription(prescriptionId);
//		return new ResponseEntity<>("Prescription deleted Successfully", HttpStatus.ACCEPTED);
//	}

	// update Prescription
//	@PutMapping("/prescription/{prescriptionid}")
//	public ResponseEntity<PrescriptionDto> updatePrescription(@RequestBody PrescriptionDto prescriptionDto,
//			@PathVariable(name = "prescriptionid") int prescriptionId) {
//		return new ResponseEntity<>(prescriptionService.updatePrescription(prescriptionDto, prescriptionId),
//				HttpStatus.ACCEPTED);
//	}

	@GetMapping("/visitdetail/{visitId}")
	public ResponseEntity<VisitDetails> getVisit(@PathVariable(name = "visitId") int visitId) {
		return new ResponseEntity<>(visitService.getVisit(visitId), HttpStatus.OK);
	}

	@GetMapping("/visit/{appointmentId}")
	public ResponseEntity<VisitDetails> getAppointmentVisit(@PathVariable int appointmentId) {
		return new ResponseEntity<>(visitService.findByAppointmentId(appointmentId), HttpStatus.OK);
	}
	
	

}
