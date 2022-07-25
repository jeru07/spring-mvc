package com.chainsys.springmvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chainsys.springmvc.pojo.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
	Appointment findById(int id);

	Appointment save(Appointment app);

	void deleteById(int app_id);

	List<Appointment> findAll();
	
	// Define a native query for this method
	@Query(value="select * from appoint u where u.id=?1",nativeQuery = true) 
	int getNextId( );
}
