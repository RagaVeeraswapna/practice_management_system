package com.revature.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
import com.revature.exception.VisitNotFound;
import com.revature.payload.TestDto;
import com.revature.repository.TestRepository;
import com.revature.repository.VisitRepository;
import com.revature.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;
	@Autowired
	private VisitRepository visitRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<TestDto> getTestDetails(int visitId) {
		Optional<VisitDetails> v = Optional.of(visitRepository.findById(visitId)
				.orElseThrow(() -> new VisitNotFound(String.format("Visit Id %d not found", visitId))));
		List<TestDetails> tests = testRepository.findAllByVisitId(v);
		return (tests.stream().map(test -> modelMapper.map(test, TestDto.class)).collect(Collectors.toList()));
	}

	@Override
	public TestDto saveTest(int visitId, TestDetails testDetails) {
		VisitDetails v = visitRepository.findById(visitId)
				.orElseThrow(() -> new VisitNotFound(String.format("Visit id %d not found", visitId)));
//		TestDetails test = modelMapper.map(testDto, TestDetails.class);
		testDetails.setVisitId(v);
		TestDetails savedTest = testRepository.save(testDetails);
		return modelMapper.map(savedTest, TestDto.class);
	}

	@Override
	public void deleteTest(int testId) {
		testRepository.deleteById(testId);
	}

}
