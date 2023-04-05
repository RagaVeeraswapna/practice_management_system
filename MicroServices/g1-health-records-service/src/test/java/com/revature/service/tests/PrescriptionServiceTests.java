package com.revature.service.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.Prescription;
import com.revature.entity.VisitDetails;
import com.revature.repository.PrescriptionRepository;
import com.revature.repository.VisitRepository;
import com.revature.service.PrescriptionService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PrescriptionServiceTests {

	@Autowired
	private PrescriptionService prescriptionService;

	@MockBean
	private PrescriptionRepository prescriptionRepository;

	@MockBean
	private VisitRepository visitRepository;

	@Test
	public void getAllPrescriptionTest() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		when(visitRepository.findById(1)).thenReturn(v);
		VisitDetails visit = new VisitDetails(1);
		List<Prescription> prescription = Arrays.asList(new Prescription(5, "Dolo", "1-0-1", "After eat", visit),
				new Prescription(6, "fortamet", "1-0-0", "Before eat", visit));
		when(prescriptionRepository.findAllByVisitId(v)).thenReturn(prescription);
		assertEquals(2, prescriptionService.getPrescriptionDetails(visit.getVisitId()).size());
	}

	@Test
	public void savePrescriptionById() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		when(visitRepository.findById(1)).thenReturn(v);
		Prescription p = new Prescription(5, "Dolo", "1-0-1", "After eat", new VisitDetails(1));
		when(prescriptionRepository.save(p)).thenReturn(p);
		Assertions.assertEquals("Dolo", prescriptionService.savePrescription(1, p).getPrescriptionName());
		Assertions.assertEquals("After eat", prescriptionService.savePrescription(1, p).getPrescriptionNotes());
	}

	@Test
	public void deletePrescriptionTest() {
		Prescription p = new Prescription(5, "Dolo", "1-0-1", "After eat", new VisitDetails(1));
		prescriptionService.deletePrescription(5);
		verify(prescriptionRepository, times(1)).deleteById(5);
	}

}
