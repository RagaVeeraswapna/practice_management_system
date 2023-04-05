package com.revature.integration.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.G1HealthRecordsServiceApplication;
import com.revature.entity.Prescription;
import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
import com.revature.payload.PrescriptionDto;
import com.revature.payload.TestDto;
import com.revature.payload.VisitDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1HealthRecordsServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthRecordsIntegrationTest {

	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;

	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@Test
	public void getVisitDetailsByVisitId() {
		String uri="http://localhost:" + port + "/api/v1/visitdetails/4";
		Map<String,Integer> pathVariable=new HashMap<>();
		pathVariable.put("visitId",4);
		HttpEntity entity = new HttpEntity<>(null, null);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
		ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
		System.out.println(res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	public void testGetAllVisitsByPatientId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<VisitDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/1/visits"),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<VisitDto>>() {
				});
		List<VisitDto> v = response.getBody();
		assertEquals(9, v.size());

	}

	@Test
	public void saveVisits() throws JsonMappingException, JsonProcessingException {
		VisitDto v = new VisitDto(12, 2, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse3@gmail.com",
				"physician3@gmail.com", 1, "cold", 1);
		HttpEntity<VisitDto> entity = new HttpEntity<>(v, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/2/visits"),
				HttpMethod.POST, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		VisitDto found = mapper.readValue(response.getBody(), VisitDto.class);
		Assertions.assertEquals("physician3@gmail.com", found.getPhysicianEmail());

	}

	@Test
	public void updateVisit() throws JsonMappingException, JsonProcessingException {
		String uri = "http://localhost:" + port + "/api/v1/visitdetails/3";
		VisitDto v = new VisitDto(4, 3, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse3@gmail.com",
				"physician3@gmail.com", 1, "cold", 1);
		Map<String, Integer> pathVariable = new HashMap<>();
		pathVariable.put("visitid", 4);
		HttpEntity<VisitDto> entity = new HttpEntity<VisitDto>(v, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(),
				HttpMethod.PUT, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		VisitDto found = mapper.readValue(response.getBody(), VisitDto.class);
		Assertions.assertEquals("O+ve", found.getBloodGroup());
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testGetAllTestsByVisitId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<TestDto>> response = restTemplate.exchange(
				createURLWithPort("/api/v1/visitdetails/1/tests"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<TestDto>>() {
				});
		List<TestDto> t = response.getBody();
		assertEquals(2, t.size());

	}

	@Test
	public void saveTests() throws JsonMappingException, JsonProcessingException {
		TestDetails t = new TestDetails(5, "Sugar test", "Negative", "Everything looks good", new VisitDetails(2));
		HttpEntity<TestDetails> entity = new HttpEntity<>(t, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/visitdetails/5/Tests"),
				HttpMethod.POST, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		TestDto found = mapper.readValue(response.getBody(), TestDto.class);
		Assertions.assertEquals("Sugar test", found.getTestName());

	}

	@Test
	public void deleteTest() throws JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/testdetails/3"),
				HttpMethod.DELETE, entity, String.class);
		String found = response.getBody();
		Assertions.assertEquals("Test deleted Successfully", found);

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testGetAllPrescriptionByVisitId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<PrescriptionDto>> response = restTemplate.exchange(
				createURLWithPort("/api/v1/visitdetails/1/prescription"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<PrescriptionDto>>() {
				});
		List<PrescriptionDto> p = response.getBody();
		assertEquals(2, p.size());

	}

	@Test
	public void savePrescription() throws JsonMappingException, JsonProcessingException {
		Prescription p = new Prescription(5, "Dolo", "1-0-0", "After eat", new VisitDetails(2));
		HttpEntity<Prescription> entity = new HttpEntity<>(p, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/v1/visitdetails/5/prescription"), HttpMethod.POST, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		PrescriptionDto found = mapper.readValue(response.getBody(), PrescriptionDto.class);
		Assertions.assertEquals("Dolo", found.getPrescriptionName());

	}

	@Test
	public void deletePrescription() throws JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/prescription/3"),
				HttpMethod.DELETE, entity, String.class);
		String found = response.getBody();
		Assertions.assertEquals("Prescription deleted Successfully", found);

	}

}
