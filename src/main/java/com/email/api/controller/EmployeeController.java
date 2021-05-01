package com.email.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.email.api.model.EmployeeDetails;
import com.email.api.service.EmployeeService;
import com.email.api.utilities.BaseResponse;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@RequestMapping(value = "/retrieveEmployeeDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmployeeDetails(@RequestParam String employeeLName, @RequestParam String employeeId) {
		try {
		List<EmployeeDetails>	emp = employeeService.getDetails(employeeLName, employeeId);
			BaseResponse response = new BaseResponse();
			response.setMessage("200 Success");
			return new ResponseEntity<Object>(emp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Occured:");
			BaseResponse response = new BaseResponse();
			response.setMessage("Internal server error");
			return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/saveEmployeeDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<BaseResponse> saveEmployeeDetails(@RequestBody EmployeeDetails emp) {
		try {
			employeeService.saveDetails(emp);
			BaseResponse response = new BaseResponse();
			response.setMessage("200 Success");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Occured:");
			BaseResponse response = new BaseResponse();
			response.setMessage("Internal server error");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
