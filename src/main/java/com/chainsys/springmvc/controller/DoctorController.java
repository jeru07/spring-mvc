package com.chainsys.springmvc.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.springmvc.dao.DoctorRepository;
import com.chainsys.springmvc.pojo.Appointment;
import com.chainsys.springmvc.pojo.Doctor;
import com.chainsys.springmvc.pojo.DoctorAppointmentsDTO;
import com.chainsys.springmvc.service.DoctorService;
@Controller
@RequestMapping("/doctor")
public class DoctorController
{  
	@Autowired
    DoctorService drservice;
	@GetMapping("/list")
	public String getDoctors(Model model)
	{  
		List<Doctor> theDr=drservice.getDoctors();
		model.addAttribute("alldoctor",theDr);
		return "list-doctor";
	}
	@GetMapping("/addform")
	public String showAddForm(Model model)
	{
		Doctor theDr=new Doctor();
		model.addAttribute("adddoctor", theDr);
		return "add-doctor-form";
	}
    @PostMapping("/add")
	public String addNewDoctors(@ModelAttribute("adddoctor") Doctor theDr) {
	    drservice.save(theDr);
		return "redirect:/doctor/list";
    }
    @GetMapping("/updateform")
   	public String showUpdateForm(@RequestParam("docid") int id,Model model)
   	{
   		Doctor theDr=drservice.findByid(id);
   		model.addAttribute("updatedoctor", theDr);
   		return "update-doctor-form";
   	}
       @PostMapping("/updatedoctor")
   	public String updateDoctors(@ModelAttribute("updatedoctor") Doctor theDr) {
   		drservice.save(theDr);
   		return "redirect:/doctor/list";
   	}
       @GetMapping("/deletedoctor")
   	public String deleteDoctor(@RequestParam("docid") int id) {
       drservice.deleteById(id);
   		return "redirect:/doctor/list";
   	}

	@GetMapping("/getdoctorbyid")
	public String getDoctor(@RequestParam("id") int id,Model model)
	{
		Doctor theDr=drservice.findByid(id);
		model.addAttribute("getdoctorbyid", theDr);
		return "find-doctor-id-form";
	}
	@GetMapping("/getdocapp")
	public String getAppointments(@RequestParam("id") int id,Model model)
	{
		DoctorAppointmentsDTO dto=drservice.getDoctorAndAppointments(id);
		model.addAttribute("getdoc", dto.getDoctor());
		model.addAttribute("applist",dto.getAppointments());
		return "list-doctor-appointments";
	}
	// localhost:8080/doctor/trans?id=22
	@GetMapping("/trans")
	public void testTransaction(@RequestParam("id") int id)
	{
		DoctorAppointmentsDTO dto=new DoctorAppointmentsDTO();
		Doctor dr=new Doctor();
		dr.setDoctor_id(id);
		dr.setDoctor_name("jerusha");
		Date drdob=new Date(92,7,14);
		dr.setDob(drdob);
		dr.setCity("madurai");
		dr.setPhone_no(123456789);
		dr.setSpeciality("heart");
		dr.setStandard_fees(700);
		dto.setDoctor(dr);
		List<Appointment> applist= new ArrayList<Appointment>();
		int nextAppId=drservice.getNextAppointmentId();
		for(int i=nextAppId;i<=nextAppId+2;i++) 
		{
			Appointment app=new Appointment();
			app.setAppointment_id(i);
			Date appdt=new Date(22,7,25);
			app.setAppointment_date(appdt); 
			app.setDoctor_id(id);
			app.setPatient_name("jeru");
			app.setFees_collected(dr.getStandard_fees());
			dto.addAppointment(app);
		}
		drservice.addDoctorAndAppointments(dto);
		System.out.println("Sucessfully Completed");
	}
}
