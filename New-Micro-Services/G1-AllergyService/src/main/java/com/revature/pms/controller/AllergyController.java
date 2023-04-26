package com.revature.pms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.pms.model.Allergy;
import com.revature.pms.model.AuthToken;
import com.revature.pms.model.User;
import com.revature.pms.service.AllergyService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AllergyController {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private AllergyService allergyService;
	
	@GetMapping("/allergy")
	public ResponseEntity<List<Allergy>> getAll(){
		List<Allergy> allergies = null;
		try {
			 allergies= allergyService.getAllergies();
			if(allergies.isEmpty())
				return new ResponseEntity<List<Allergy>>(allergies,HttpStatus.NO_CONTENT);
		}
		catch (NullPointerException e){
			return new ResponseEntity<>(allergies, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Allergy>>(allergies,HttpStatus.OK);
	}
	
	@GetMapping("/allergy/{id}")
	public  ResponseEntity<Allergy> getAllergy(@PathVariable int id){
		Allergy allergy = null;
		try {
			allergy = allergyService.getAllergy(id);
		}catch (NullPointerException e){
			return new ResponseEntity<>(allergy,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(NoSuchElementException e){
			return new ResponseEntity<Allergy>(allergy,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Allergy>(allergy,HttpStatus.OK);
	}
	
}
