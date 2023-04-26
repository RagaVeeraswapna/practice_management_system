package com.revature.service;

import java.util.List;

import com.revature.payload.TestDto;

public interface TestService {

	public List<TestDto> saveVisit(List<TestDto> prescriptionDto);

	public List<TestDto> getTestDetails(int visitId);

	public TestDto updateTest(TestDto testDto, int testId);

	public void deleteTest(int testId);
}
