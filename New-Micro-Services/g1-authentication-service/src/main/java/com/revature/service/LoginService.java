package com.revature.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.entity.dto.PatientDto;


public interface LoginService {
	public PatientDto loginService(String email,String password);
	public void updatepssword(String email,String password);
}
