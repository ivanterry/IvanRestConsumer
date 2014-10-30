package com.ivan.webservices;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ivan.common.model.User;

public class FlightsWebService {
	
	private RestTemplate restTemplate = null;
	private HttpHeaders headers = null;

	public FlightsWebService(MediaType mediaType) {
		super();
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(mediaType);
	}

    /**
     * @param inputUrl
     * @return Flights authorisation header string
     */
    public ResponseEntity<String> getFlightsAuth(String inputUrl) {
    	ResponseEntity<String> result = null;
        try {
        	//make the object
        	User obj = new User();
        	obj.setUser("test");
        	obj.setPass("secret");
        	//set your entity to send
        	HttpEntity<User> entity = new HttpEntity<User>(obj, headers);
        	// send it!
        	result = restTemplate.exchange(inputUrl, HttpMethod.POST, entity, String.class);
        } catch (HttpStatusCodeException e) {
            System.out.println("Get FAILED with HttpStatusCode: " + e.getStatusCode() + "|" + e.getStatusText());
        } catch (RuntimeException e) {
            System.out.println("Get FAILEDn" + e.getMessage());
        }
        return result;
    }
    
    /**
     * @param inputUrl
     * @param authorisation
     * @return Flight response information
     */
    public ResponseEntity<String> getFlightAvailablity(String inputUrl, String authorisation) {
    	ResponseEntity<String> result = null;
        try {
        	headers.set("Authorization", authorisation);
        	HttpEntity<?> entity = new HttpEntity<Object>(headers);
        	result = restTemplate.exchange(inputUrl, HttpMethod.GET, entity, String.class);
//        	Object jSONObject = restTemplate.getForObject(inputUrl, Object.class);
        } catch (HttpStatusCodeException e) {
            System.out.println("Get FAILED with HttpStatusCode: " + e.getStatusCode() + "|" + e.getStatusText());
        } catch (RuntimeException e) {
            System.out.println("Get FAILEDn" + e.getMessage());
        }
        return result;
    }

}
