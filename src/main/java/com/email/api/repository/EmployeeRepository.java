package com.email.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.email.api.model.EmployeeDetails;

@Repository
public class EmployeeRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;
	public List<EmployeeDetails> getEmailDetails(String employeeLName, String employeeId) {
		
		Query query = new Query();
		Criteria criteria =  new Criteria();
		criteria.andOperator(Criteria.where("employeeLName").is(employeeLName),Criteria.where("employeeId").is(employeeId));
		query.addCriteria(criteria);
		List<EmployeeDetails> emp = mongoTemplate.find(query, EmployeeDetails.class,"EmployeeCollection");	
		return emp;
	}
	
	public void saveDetails(EmployeeDetails empDetails) {
		mongoTemplate.save(empDetails,"EmployeeCollection");
	}

}
