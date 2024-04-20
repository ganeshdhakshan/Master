package GET;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Git_Hub {
	
	
	public void User_Activites() throws JsonMappingException, JsonProcessingException {

		RestAssured.baseURI = End_Points.baseUrl;

		// headers

		Header header1 = new Header("Accept", "application/vnd.github+json");
		Header header2 = new Header("Authorization", "Bearer " + Token);
		Header header3 = new Header("X-GitHub-Api-Version", "2022-11-28");
		List<Header> list = new ArrayList<Header>();
		list.add(header1);
		list.add(header2);
		list.add(header3);
		Headers headers = new Headers(list);

		// initialization
		Response response = RestAssured.given().headers(headers).auth().preemptive().oauth2(Token).log().all()
				.get("/repos/sakthimathan/cucumber");

		System.out.println("Respose---> " + response);

		System.out.println("Response Code --> " + response.statusCode());

		System.out.println("Response Message --> " + response.statusLine());

		String body = response.getBody().asPrettyString();

		int length = body.getBytes().length;
		System.out.println("length -->" + length);

		System.out.println("Body--> " + body);
		System.out.println("--------------------");

		// Method 1
		ObjectMapper om = new ObjectMapper();
		
		for (Git_Root Git_Root : x) {
			System.out.println("User-ID ----> " + Git_Root.getId());
			System.out.println("GIT-URL-----> " + Git_Root.getActor().getAvatar_url());
			System.out.println("LOGIN Name------>>" + Git_Root.getActor().getLogin());
		}

	}
}
