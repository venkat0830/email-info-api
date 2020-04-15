package com.email.api.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.email.api.model.MissedProvider;
import com.email.api.model.MissedProviderEmailList;

@Component
public class ParameterValidation {
	public List<String> validationRequestParam(MissedProviderEmailList missedProviderEmailList){
		 List<String> s = new ArrayList<>();
			
			for (MissedProvider missedProvider: missedProviderEmailList.getMissedProviderEmailList()) {
				
				if(StringUtils.isBlank(missedProvider.getCorporateTaxID())) {
					s.add("FAULT_1A");
				}
				if(StringUtils.isBlank(missedProvider.getProviderTin())){
					s.add("FAULT_2A");
				}
				if(StringUtils.isBlank(missedProvider.getUuID())) {
					s.add("FAULT_3A");
				}
			}
			return s;
	}
	

}
