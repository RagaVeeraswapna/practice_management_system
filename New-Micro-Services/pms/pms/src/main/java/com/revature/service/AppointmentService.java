package com.revature.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.revature.dao.AppointmentInterface;
import com.revature.dto.AppoinmentDto;
import com.revature.model.Appointment;


@Service
public class AppointmentService {
	@Autowired
	private AppointmentInterface appointment;
	
	Appointment appo=new Appointment();
	
	public Appointment getAppointment(Appointment appointmentDto){
		return appointment.save(appointmentDto);
	}
	public List<Appointment> getAppointments(String physcianEmail,String date,String accptence){
		return appointment.findBystatusanddate(physcianEmail,date,accptence);
		
	}
	
	public List<Appointment> getAppointments(String acceptence ,int index,int size){
		return appointment.findByAcceptance(acceptence,PageRequest.of(index, size));	
	}
	
	public List<Appointment> getPatientsAppointments(Integer id,String status,int index ,int size){
		return appointment.findByPatientIdAndAcceptanceOrderByDateDesc(id,status,PageRequest.of(index, size));
	}
	public Appointment getAppointmentsService(int id){
		return appointment.findById(id).get();
	}
	public void updateById(Integer id,String status) {
		appointment.updatebyid(status, id);
	}
	
	public List<Appointment> store(List<Appointment> list){
		return appointment.saveAll(list);
	}
	public Appointment updateStatus(Appointment appointment){
		return appointment;
	}
	
	public long getAppointmentCount(String email) {
		return this.appointment.countAppointment(email);
	}
	
	public long getPendingAppointmentCount(String email) {
		return this.appointment.countPendingAppointment(email);
	}
	
	public long getAcceptedAppointmentCount(String email) {
		return this.appointment.countAcceptedAppointment(email);
	}
	
	public long  getCompletedAppointmentByPatientId(int id) {
		return this.appointment.countCompletedAppointmentByPatientId(id);
	}
	
	public long  getPendingAppointmentByPatientId(int id) {
		return this.appointment.countPendingAppointmentByPatientId(id);
	}
	
	public long countAllAcceptanceAppointment() {
		return this.appointment.countAllAcceptanceAppointment();
	}
	public List<Appointment>getAppointmentByPatientId(int id){
		return appointment.findByPatientId(id);
	}
	
	public Appointment updateStatusById(int ID,Appointment appointment){
		Appointment details=this.appointment.findById(ID).orElse(null);
		System.out.println(details);
		details.setAcceptance(appointment.getAcceptance());
		return this.appointment.save(details);
		
	}
	
	public void deleteByid(Integer id) {
		appointment.deleteById(id);
	}
	}
