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

import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
import com.revature.repository.TestRepository;
import com.revature.repository.VisitRepository;
import com.revature.service.TestService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestServiceTests {

	@Autowired
	private TestService testService;

	@MockBean
	private VisitRepository visitRepository;

	@MockBean
	private TestRepository testRepository;

	@Test
	public void getAllTestsTest() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		when(visitRepository.findById(1)).thenReturn(v);
		VisitDetails visit = new VisitDetails(1);
		List<TestDetails> tests = Arrays.asList(
				new TestDetails(5, "Sugar test", "Negative", "Everything looks good", visit),
				new TestDetails(6, "Blood test", "10 points", "fewer blood cells", visit));
		when(testRepository.findAllByVisitId(v)).thenReturn(tests);
		assertEquals(2, testService.getTestDetails(visit.getVisitId()).size());
	}

	@Test
	public void saveTestById() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		when(visitRepository.findById(1)).thenReturn(v);
		VisitDetails visit = new VisitDetails(1);
		TestDetails t = new TestDetails(5, "Sugar test", "Negative", "Everything looks good", visit);
		when(testRepository.save(t)).thenReturn(t);
		Assertions.assertEquals("Sugar test", testService.saveTest(visit.getVisitId(), t).getTestName());
		Assertions.assertEquals("Negative", testService.saveTest(visit.getVisitId(), t).getResult());
	}

	@Test
	public void deleteTest() {
		TestDetails t = new TestDetails(5, "Sugar test", "Negative", "Everything looks good", new VisitDetails(1));
		testService.deleteTest(5);
		verify(testRepository, times(1)).deleteById(5);
	}
}
