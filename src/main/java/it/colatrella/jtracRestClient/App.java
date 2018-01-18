package it.colatrella.jtracRestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

/**
	Auth: http://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring
 *
 */
public class App 
{
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main( String[] args )
	{
		log.info("Starting");
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("header_name", "header_value");
		headers.setContentType(MediaType.APPLICATION_XML);
		
		String body = ""
				+ "<item prefixCode=\"INC\">\r\n" + 
					"<summary>From Java</summary>\r\n" + 
					"<detail>Attività eseguita da Giorgio Rappo</detail>\r\n" + 
					"<loggedBy>admin</loggedBy>\r\n" + 
					"<assignedTo>admin</assignedTo>\r\n" + 
				"</item>";
			
		HttpEntity<String> request = new HttpEntity<String>(body, headers);
		
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
		
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/jtrac/api?method=itemPut", request, String.class);
		
		log.info("Finish with status:"+ response.getStatusCodeValue()+" and "+response.getBody());
	}
}
