package com.skillstorm.ms_gateway.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class InfoDisplay implements InfoContributor {

	@Override
	public void contribute(Builder builder) {
		// TODO: Add useful info to be displayed about this on the Eureka server
		Map<String, Object> details = new HashMap<>();
		details.put("General: ", "Hello! This is our gateway! This is where the Front-end of our application calls our Back-end Microservices!");
		builder.withDetails(details);
		
	}

}
