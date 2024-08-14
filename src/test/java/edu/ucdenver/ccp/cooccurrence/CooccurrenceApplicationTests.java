package edu.ucdenver.ccp.cooccurrence;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.ncats.trapi.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@AutoConfigureCache
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CooccurrenceApplicationTests {

	
//	@Autowired
//    private CooccurrenceController myController;

//    @Autowired
//    private MyService myService;
	
//	@Autowired
//	WebApplicationContext wac;

	@Autowired
	private WebTestClient webTestClient;

//	@BeforeEach
//	void setUp() {  
////		client = WebTestClient.bindToApplicationContext(context).build(); 
//		client = WebTestClient.bindToController(myController).build(); 
//	}

//	@Test
//	void contextLoads() {
//		assertThat(myController).isNotNull();
//	}

	String getSampleQueryJson() {
		/* @formatter:off */
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\"message\": {\n");
		sb.append("\"query_graph\": {\n");
		sb.append("\"edges\": {\n");
		sb.append("\"e00\": {\n");
		sb.append("\"object\": \"n01\",\n");
		sb.append("\"predicates\": [\n");
		sb.append("\"biolink:occurs_together_in_literature_with\"\n");
		sb.append("],\n");
		sb.append("\"subject\": \"n00\"\n");
		sb.append("}\n");
		sb.append("},\n");
		sb.append("\"nodes\": {\n");
		sb.append("\"n00\": {\n");
		sb.append("\"ids\": [\n");
		sb.append("\"MONDO:0009020\"\n");
		sb.append("]\n");
		sb.append("},\n");
		sb.append("\"n01\": {\n");
		sb.append("\"ids\": [\n");
		sb.append("\"DRUGBANK:DB12411\"\n");
		sb.append("]\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("}\n");
		sb.append("}\n");
		return sb.toString();
		/* @formatter:on */
	}

	@Test
	void testQuery() {
//		webTestClient= WebTestClient.bindToController(myController).build();

		 FluxExchangeResult<Message> result = webTestClient.post().uri("/query")
		.bodyValue(getSampleQueryJson().getBytes())
		.accept(MediaType.APPLICATION_JSON)
		.header("Content-type", "application/json")
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.returnResult(Message.class);
		 
//		 Flux<Message> responseBody = result.getResponseBody();
//		 Message message = responseBody.blockFirst();
//		 
//		 System.out.println("Response Body: " + responseBody.);
//		 System.out.println("Message toString: " + message.toString());
//		 System.out.println("Query Graph is present: " + message.getQueryGraph().isPresent());
//		 System.out.println("Query Graph toString: " + message.getQueryGraph().toString());
		 
		
		 List<String> responseBodyJson = result.getResponseBody()
				    .map(message -> {
				        try {
				            return new ObjectMapper().writeValueAsString(message);
				        } catch (JsonProcessingException e) {
				            return "Error converting to JSON: " + e.getMessage();
				        }
				    })
				    .collectList()
				    .block();

				System.out.println("Response JSON:");
				System.out.println(responseBodyJson.toString());

//		Response JSON:
//		[{"results":{"present":false},"query_graph":{"present":false},"knowledge_graph":{"present":false},"auxiliary_graphs":{"present":false}}]
				
				
	}

}
