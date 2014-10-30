package com.ivan.webservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ivan.common.model.CheckSum;
import com.ivan.common.model.IpAdress;

public class TestWebService {
	
	
    /**
     * @param inputUrl
     * @return Checksum object
     */
    public CheckSum getChecksum(String inputUrl) {
    	CheckSum result = null;
        try {
        	RestTemplate restTemplate = new RestTemplate();
        	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        	messageConverters.add(new MappingJacksonHttpMessageConverter());
        	restTemplate.setMessageConverters(messageConverters); 
        	CheckSum jSONObject = restTemplate.getForObject(inputUrl, CheckSum.class);
            result = jSONObject;
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        }
        return result;
    }

    /**
     * This method retrieves the IP address given the correct input url
     * @return the message String
     */
    public String getIP(String inputUrl) {
        String result;
        try {
        	RestTemplate restTemplate = new RestTemplate();
        	//Create a list for the message converters
        	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        	//Add the Jackson Message converter
        	messageConverters.add(new MappingJacksonHttpMessageConverter());
        	//Add the message converters to the restTemplate
        	restTemplate.setMessageConverters(messageConverters); 
        	
        	IpAdress httpResult = restTemplate.getForObject(inputUrl, IpAdress.class);
            result = "IP address is: " + httpResult.getIp();
        } catch (HttpStatusCodeException e) {
            result = "Get FAILED with HttpStatusCode: " + e.getStatusCode() + "|" + e.getStatusText();
        } catch (RuntimeException e) {
            result = "Get FAILEDn" + e.getMessage();
        }
        return result;
    }

}
