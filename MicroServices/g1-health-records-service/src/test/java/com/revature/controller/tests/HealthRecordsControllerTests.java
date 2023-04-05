package com.revature.controller.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.HealthRecordsController;
import com.revature.entity.Prescription;
import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
import com.revature.payload.PrescriptionDto;
import com.revature.payload.TestDto;
import com.revature.payload.VisitDto;
import com.revature.service.PrescriptionService;
import com.revature.service.TestService;
import com.revature.service.VisitService;

@WebMvcTest(HealthRecordsController.class)
public class HealthRecordsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VisitService visitService;

	@MockBean
	private TestService testService;

	@MockBean
	private PrescriptionService prescriptionService;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	public void getAllVisitsTest() throws Exception {
		List<VisitDto> visits = Arrays.asList(
				new VisitDto(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
						"physician1@gmail.com", 1, "high fever", 1),
				new VisitDto(2, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "O+ve", "nurse2@gmail.com",
						"physician3@gmail.com", 2, "high fever", 2));
		when(visitService.getAllVisits(1)).thenReturn(visits);
		mockMvc.perform(get("/api/v1/patient/1/visits").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].bloodGroup").value("B+ve"));
		mockMvc.perform(get("/api/v1/patient/1/visits").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[1].bloodGroup").value("O+ve"));
	}

	@Test
	public void getVisityById() throws Exception {
		VisitDto visit = new VisitDto(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
				"physician1@gmail.com", 1, "high fever", 1);
		VisitDetails visit1 = modelMapper.map(visit, VisitDetails.class);
		when(visitService.getVisit(1)).thenReturn(visit1);
		mockMvc.perform(get("/api/v1/visitdetails/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nurseEmail").value("nurse1@gmail.com"));
	}

	@Test
	public void updateVisitById() throws Exception {
		VisitDto v = new VisitDto(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
				"physician1@gmail.com", 1, "high fever", 1);
		VisitDto newVisit = new VisitDto(1, 1, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse2@gmail.com",
				"physician2@gmail.com", 1, "cold", 1);
		when(visitService.updateVisit(newVisit, 1)).thenReturn(newVisit);
		this.mockMvc
				.perform(put("/api/v1/visitdetails/1").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(newVisit)))
				.andExpect(status().isAccepted()).andExpect(jsonPath("$.nurseEmail").value("nurse2@gmail.com"));

	}

	@Test
	public void saveVisitById() throws Exception {
		VisitDto v = new VisitDto(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
				"physician1@gmail.com", 1, "high fever", 1);
		when(visitService.saveVisit(1, v)).thenReturn(v);
		mockMvc.perform(post("/api/v1/patient/1/visits").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(v))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nurseEmail").value("nurse1@gmail.com"));
	}

	/////////////////////////////////////////////////////////////////////////////

	@Test
	public void getAllTestsTest() throws Exception {
		List<TestDetails> tests = Arrays.asList(
				new TestDetails(5, "Sugar test", "Negative", "Everything looks good", new VisitDetails(1)),
				new TestDetails(6, "Blood test", "10 points", "fewer blood cells", new VisitDetails(1)));
		List<TestDto> td = Arrays.asList(modelMapper.map(tests, TestDto[].class));
		when(testService.getTestDetails(1)).thenReturn(td);
		mockMvc.perform(get("/api/v1/visitdetails/1/tests").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].testName").value("Sugar test"));
		mockMvc.perform(get("/api/v1/visitdetails/1/tests").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[1].testName").value("Blood test"));
	}

	@Test
	public void saveTestById() throws Exception {
		TestDetails t = new TestDetails(5, "Sugar test", "Negative", "Everything looks good", new VisitDetails(1));
		TestDto td = modelMapper.map(t, TestDto.class);
		when(testService.saveTest(eq(1), any(TestDetails.class))).thenReturn(td);
		mockMvc.perform(post("/api/v1/visitdetails/1/Tests", 1, t).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(td))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.testName").value("Sugar test"));
	}

	@Test
	public void deleteTest() throws Exception {
		TestDetails t = new TestDetails(5, "Sugar test", "Negative", "Everything looks good", new VisitDetails(1));
		doNothing().when(testService).deleteTest(any(Integer.class));
		MvcResult rs = mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/v1/testdetails/5").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted()).andReturn();
		String expected = rs.getResponse().getContentAsString();
		assertEquals(expected, "Test deleted Successfully");

	}

	////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void getAllPrescriptionTest() throws Exception {
		List<Prescription> prescription = Arrays.asList(
				new Prescription(5, "Dolo", "1-0-1", "After eat", new VisitDetails(1)),
				new Prescription(6, "fortamet", "1-0-0", "Before eat", new VisitDetails(1)));
		List<PrescriptionDto> pd = Arrays.asList(modelMapper.map(prescription, PrescriptionDto[].class));
		when(prescriptionService.getPrescriptionDetails(1)).thenReturn(pd);
		mockMvc.perform(get("/api/v1/visitdetails/1/prescription").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].prescriptionName").value("Dolo"));
		mockMvc.perform(get("/api/v1/visitdetails/1/prescription").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[1].prescriptionName").value("fortamet"));
	}

	@Test
	public void savePrescriptionById() throws Exception {
		Prescription p = new Prescription(5, "Dolo", "1-0-0", "After eat", new VisitDetails(1));
		PrescriptionDto pd = modelMapper.map(p, PrescriptionDto.class);
		when(prescriptionService.savePrescription(eq(1), any(Prescription.class))).thenReturn(pd);
		mockMvc.perform(post("/api/v1/visitdetails/1/prescription", 1, p).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(pd))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.prescriptionName").value("Dolo"));
	}

	@Test
	public void deletePrescriptionTest() throws Exception {
		Prescription p = new Prescription(5, "Dolo", "1-0-0", "After eat", new VisitDetails(1));
		doNothing().when(prescriptionService).deletePrescription(any(Integer.class));
		MvcResult rs = mockMvc
				.perform(
						MockMvcRequestBuilders.delete("/api/v1/prescription/5").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted()).andReturn();
		String expected = rs.getResponse().getContentAsString();
		assertEquals(expected, "Prescription deleted Successfully");

	}

}
