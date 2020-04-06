package com.sapient.rulesengine;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sapient.model.RulesBean;

@FeignClient(name = "rulesservice",url = "http://localhost:8763/rulesservice/rules")
public interface RulesServiceProxy {
	
	@GetMapping(value = "/getall", produces = "application/json") 
	public List<RulesBean> getAllRules() ;

}
