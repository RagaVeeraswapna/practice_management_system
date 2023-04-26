package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dao.AppointmentInterface;
import com.revature.dto.AppoinmentDto;
import com.revature.model.Appointment;
import com.revature.service.AppointmentService;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
public class PhysicianController {
	
	Appointment appoint = new Appointment();
	AppoinmentDto appointmetdto=new AppoinmentDto();
	
	@Autowired
	AppointmentService appointmentservice;

	
	@GetMapping("/appointment/{patientnId}/{status}/{index}/{size}")
	public List<Appointment> patientAppointments(@PathVariable Integer patientnId,@PathVariable String status,@PathVariable int index,@PathVariable int size){
		return appointmentservice.getPatientsAppointments(patientnId,status,index,size);
	}
	
	@GetMapping("/appointment/{appointmentId}")
	public Appointment getAppointments(@PathVariable int appointmentId){
		return appointmentservice.getAppointmentsService(appointmentId);
		}
	
	@PutMapping("/appointment/{appointmentId}/{status}")
	@Transactional		
	public void updateStatusById(@PathVariable Integer appointmentId,@PathVariable String status) {
		System.out.println(appointmentId+" "+status);
		appointmentservice.updateById(appointmentId, status);
	}
	@PostMapping("/appointment")
	public Appointment appointments(@RequestBody Appointment appointments){
		return appointmentservice.getAppointment(appointments);
	}
	
	@GetMapping("/appointments/{physcianEmail}/{date}/{acceptance}")
	public List<Appointment> patientsByStatus(@PathVariable String physcianEmail,@PathVariable String date,@PathVariable String acceptance){
		return appointmentservice.getAppointments(physcianEmail,date,acceptance);		
	}
	
	
	@GetMapping("/indexed-appointments/{status}/{index}/{size}")
	public List<Appointment> patientsByStatus(@PathVariable String status,@PathVariable int index,@PathVariable int size){
	
		return appointmentservice.getAppointments(status,index,size);		
	}
//	@PutMapping("/appointment")
//	public Appointment updateAppointment(@RequestBody Appointment appointment) {
//		return appointmentservice.updateStatus(appointment);
//	}
	
	@GetMapping("/patient-appointment/{id}")
	public List<Appointment> getAppointment(@PathVariable int id) {
		return appointmentservice.getAppointmentByPatientId(id);
	}
	
//	@DeleteMapping("/appointment/{id}")
//	public void deletebyid(@PathVariable Integer id){
//		appointmentservice.deleteByid(id);
//	}
	
	@GetMapping("/appointment-count/{email}")
	public long getAppointmentCount(@PathVariable String email) {
		return appointmentservice.getAppointmentCount(email);
	}
	
	@GetMapping("/pending-count/{email}")
	public long getPendingAppointmentCount(@PathVariable String email) {
		return appointmentservice.getPendingAppointmentCount(email);
	}
	
	@GetMapping("/accepted-count/{email}")
	public long getAcceptedAppointmentCount(@PathVariable String email) {
		return appointmentservice.getAcceptedAppointmentCount(email);
	}
	
	@GetMapping("/completed/appointment/patient/{id}")
	public long getCompletedAppointmentCountByPatientId(@PathVariable int id) {
		return appointmentservice.getCompletedAppointmentByPatientId(id);
	}
	
	@GetMapping("pending/appointment/patient/{id}")
	public long getPendingAppointmentCountByPatientId(@PathVariable int id) {
		return appointmentservice.getPendingAppointmentByPatientId(id);
	}
	
	@GetMapping("acceptance/appointment/count")
	public long  getAllAcceptanceAppointment() {
		return appointmentservice.countAllAcceptanceAppointment();
	}
}
