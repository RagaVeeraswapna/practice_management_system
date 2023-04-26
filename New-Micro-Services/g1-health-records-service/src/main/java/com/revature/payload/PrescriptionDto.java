package com.revature.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionDto {
	private int prescriptionId;
	private String prescriptionName;
	private String dosage;
	private String prescriptionNotes;
	private VisitDto visitId;
}

//{
//"prescriptionName" :"fludrocortisone",
//"dosage" :"1-0-1",
//"prescriptionNotes" : "after eat"
//}