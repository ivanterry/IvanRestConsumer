package com.ivan.common;

import static com.ivan.constants.Constants.*;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ivan.common.model.CheckSum;
import com.ivan.webservices.FlightsWebService;
import com.ivan.webservices.TestWebService;

@Service
public class TestSpringRestExample {
	
	@Autowired
	private RestTemplate restTemplate = new RestTemplate();
	private FlightsWebService controller = null;
	private TestWebService webServices = null;

    @Before
    public void setUp() {
    	controller = new FlightsWebService(MediaType.APPLICATION_JSON);
    	webServices = new TestWebService();
//      mockServer = MockRestServiceServer.createServer(restTemplate);
    }
	
	/** Test 1: Test MD5
	 * @throws Exception
	 */
	@Test
	public void testJSONConversion() throws Exception {
		CheckSum md5 = webServices.getChecksum(MD5_URI + "Flights");
		System.out.println("MD5 recieved: " + md5.getMd5());
		assertTrue(md5.getMd5().equalsIgnoreCase("b5d8a15c9849c6d9a278d1f280f3ed5f"));
	}
	
	/** Test 2: Test IP Address
	 * @throws Exception
	 */
	@Test
	public void testIPaddress() throws Exception {
		//String localIP = (InetAddress.getLocalHost()); temp IP
		String result = webServices.getIP(IPTEST_URI);
		System.out.println(result);
		assertTrue(result.equalsIgnoreCase("IP address is: 92.51.226.38"));
	}
	
	/** Test 3: Get Flights authorisation
	 * @throws Exception
	 */
	@Test
	public void testFlightsAuthCall() throws Exception {
		ResponseEntity<String> result = controller.getFlightsAuth(Flights_LOGIN_URI);
		// System.out.println("Authorisation is: " + result);
		assertTrue(!result.getHeaders().getFirst("Authorization").isEmpty());
		
	}
	
	/** Test 4: Get Flights EI Availability TODO Change method to be non-generic and use a message converter to return bean
	 * @throws Exception 
	 */ 
	@Test
	public void testGetAvailability() throws Exception {
		String authHeader = controller.getFlightsAuth(Flights_LOGIN_URI).getHeaders().getFirst("Authorization");
		System.out.println("Authorisation is: " + authHeader);
		ResponseEntity<String> result = controller.getFlightAvailablity(Flights_AVAILABILITY_EI, authHeader);
		assertTrue(result.getBody().toString().contains("availability"));
	}
	
	/** Test 5: Get FX Exchange Rate
	 * @throws Exception
	 */
	@Test
	public void testGetFXRate() throws Exception {
		String authHeader = controller.getFlightsAuth(Flights_LOGIN_URI).getHeaders().getFirst("Authorization");
		System.out.println("Authorisation is: " + authHeader);
		ResponseEntity<String> result = null;
		JSONObject json = null;
		
		result = controller.getFlightAvailablity(Flights_FX_RATE + "EUR/USD", authHeader);
		json = (JSONObject)new JSONParser().parse(result.getBody());
		System.out.println("Test 5 FX Euro Dollar Result is: " + json.get("rate"));
		assertTrue(result.getBody().toString().contains("currency1"));
		
		result = controller.getFlightAvailablity(Flights_FX_RATE + "USD/EUR", authHeader);
		json = (JSONObject)new JSONParser().parse(result.getBody());
		System.out.println("Test 5 FX Dollar Euro Result is: " + json.get("rate"));
		assertTrue(result.getBody().toString().contains("currency1"));

		result = controller.getFlightAvailablity(Flights_FX_RATE + "EUR/GBP", authHeader);
		json = (JSONObject)new JSONParser().parse(result.getBody());
		System.out.println("Test 5 FX Euro Pound  Result is: " + json.get("rate"));
		assertTrue(result.getBody().toString().contains("currency1"));

		result = controller.getFlightAvailablity(Flights_FX_RATE + "GBP/EUR", authHeader);
		json = (JSONObject)new JSONParser().parse(result.getBody());
		System.out.println("Test 5 FX Pound Euro Result is: " + json.get("rate"));
		assertTrue(result.getBody().toString().contains("currency1"));

		result = controller.getFlightAvailablity(Flights_FX_RATE + "GBP/TRY", authHeader);
		json = (JSONObject)new JSONParser().parse(result.getBody());
		System.out.println("Test 5 FX Pound Lira Result is: " + json.get("rate"));
		assertTrue(result.getBody().toString().contains("currency1"));
		
	}
	
}
