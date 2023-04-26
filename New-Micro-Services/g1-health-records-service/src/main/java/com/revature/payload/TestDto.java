package com.revature.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
	private int testId;
	private String testName;
	private String result;
	private String testNotes;
	private VisitDto visitId;
}

//{
// "testName" :"Blood test",
// "result" : "9 points",
// "testNotes" : "fewer red blood cells than normal"
//}