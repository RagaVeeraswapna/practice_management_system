package com.revature.service;

import java.util.List;

import com.revature.entity.TestDetails;
import com.revature.payload.TestDto;

public interface TestService {

	public TestDto saveTest(int visitId, TestDetails testDetails);

	public List<TestDto> getTestDetails(int visitId);

	public void deleteTest(int testId);
}
