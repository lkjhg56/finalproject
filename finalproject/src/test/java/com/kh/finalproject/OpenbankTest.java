package com.kh.finalproject;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class OpenbankTest {
	
	@Test
	public void balance() {
		RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer  5a965cd7-0ec3-4312-a7aa-dc8da4838e18");
			headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

		
			
	
			
			
			
			
	}
}
