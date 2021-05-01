package com.email.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.email.api.model.EmployeeDetails;
import com.email.api.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<EmployeeDetails> getDetails(String employeeLName, String employeeId){
		List<EmployeeDetails> emp = new ArrayList<EmployeeDetails>();
		emp = employeeRepository.getEmailDetails(employeeLName, employeeId);
		return emp;
	}
	
	public void saveDetails(EmployeeDetails emp) {
		employeeRepository.saveDetails(emp);
	}

}
