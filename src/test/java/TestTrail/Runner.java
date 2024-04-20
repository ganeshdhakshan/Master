package TestTrail;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Runner {
	// below all of read and validate response
	// Jackson-databine--ObjectMapper
	// Json-simple---JSONParser
	// JSONParser--JSONObject,JSONArray

	@Test
	private void getRequest_01_Simple_JSOn() throws IOException, ParseException {

		// making request
		RestAssured.baseURI = "https://reqres.in/api/";
		RestAssured.basePath = "users/2";
		Response response = RestAssured.given().log().all().get();

		System.out.println("Status_code --> " + response.getStatusCode());
		System.out.println("Status_line --> " + response.getStatusLine());
		// System.out.println("Response_body --> "+response.getBody().asPrettyString());

		// validate response
		// using JSONObject with Json simple
		// file reader from java.io.FileReader package
		// JSONParser,JSONObject from json simple

		java.io.FileReader reader = new java.io.FileReader(
				"C:\\Users\\Lenovo\\eclipse-workspace\\Ganesh\\APIProject\\src\\test\\resources\\Trail_Get_Response.json");
		JSONParser json = new JSONParser();
		Object object = json.parse(reader);
		JSONObject obj = (JSONObject) object;
		int size = obj.size();
		System.out.println("size --> " + size);
		Object object2 = obj.get("data");
		JSONObject obj1 = (JSONObject) object2;
		Object object3 = obj1.get("email");
		String string = object3.toString();
		System.out.println("email --> " + string);

	}

	@Test
	private void getRequest_02_Nested_JSON() throws IOException, ParseException {

		// making request
		RestAssured.baseURI = "https://reqres.in/api/";
		RestAssured.basePath = "users/2";
		Response response = RestAssured.given().log().all().get();

		System.out.println("Status_code --> " + response.getStatusCode());
		System.out.println("Status_line --> " + response.getStatusLine());
		// System.out.println("Response_body --> "+response.getBody().asPrettyString());

		// validate response
		// using JSONObject with Json simple
		// file reader from java.io.FileReader package
		// JSONParser,JSONObject from json simple

		JSONParser json = new JSONParser();
		Object object = json.parse(response.getBody().asPrettyString());
		JSONObject obj = (JSONObject) object;
		int size = obj.size();
		System.out.println("size --> " + size);
		Object object2 = obj.get("data");
		JSONObject obj1 = (JSONObject) object2;
		Object object3 = obj1.get("email");
		String string = object3.toString();
		System.out.println("email --> " + string);

	}

	@Test
	private void getRequest_03_Using_JSONArray() throws IOException, ParseException {

		// making request
		RestAssured.baseURI = "https://reqres.in/api/";
		RestAssured.basePath = "users?page=2";
		Response response = RestAssured.given().log().all().get();

		System.out.println("Status_code --> " + response.getStatusCode());
		System.out.println("Status_line --> " + response.getStatusLine());
		// System.out.println("Response_body --> "+response.getBody().asPrettyString());

		JSONParser json = new JSONParser();
		Object object = json.parse(response.getBody().asPrettyString());
		JSONObject obj = (JSONObject) object;
		String string = obj.get("total").toString();
		System.out.println("total --> " + string);
		JSONArray array = (JSONArray) obj.get("data");// JSONArray using object is JSONObject
		Object object2 = array.get(2);
		JSONObject obj1 = (JSONObject) object2;
		String string2 = obj1.get("id").toString();
		System.out.println("id --> " + string2);
		System.out.println();

	}

	@Test
	private void getRequest_04_JsonPath() throws IOException, ParseException {

		// making request
		RestAssured.baseURI = "https://reqres.in/api/";
		RestAssured.basePath = "users?page=2";
		Response response = RestAssured.given().log().all().get();

		System.out.println("Status_code --> " + response.getStatusCode());
		System.out.println("Status_line --> " + response.getStatusLine());
		System.out.println("Response_body --> " + response.getBody().asPrettyString());
		String resBody = response.getBody().asPrettyString();

		// validate response using JSONPath
		// io.restassured.path.json.JsonPath package

		String expectedEmail = "charles.morris@reqres.in";
		JsonPath js = new JsonPath(resBody);
		String email = js.getString("data[2].email");
		System.out.println("eamil" + email);
		int count = js.getList("data").size();
		System.out.println("count --> " + count);

		int dataCount = js.getList("data").size();
		for (int i = 0; i < dataCount; i++) {
			String email1 = js.getString("data[" + i + "].email");
			if (email1.equals(expectedEmail)) {
				System.out.println("Email valid");
			} else {
				System.out.println("Email  invalid");
			}
		}
	}

	@Test
	private void getRequest_04_ObjectMapper() throws IOException, ParseException {

		// making request
		RestAssured.baseURI = "https://reqres.in/api/";
		RestAssured.basePath = "users?page=2";
		Response response = RestAssured.given().log().all().get();

		System.out.println("Status_code --> " + response.getStatusCode());
		System.out.println("Status_line --> " + response.getStatusLine());
//		System.out.println("Response_body --> " + response.getBody().asPrettyString());
		String resBody = response.getBody().asPrettyString();
		
		ObjectMapper mapper = new ObjectMapper();
		Root value = mapper.readValue(resBody,Root.class);
		String email = value.getData().get(2).getEmail();
		System.out.println("email--> "+email);
		
		Root as = response.as(Root.class);
		String email2 = as.getData().get(2).getEmail();
		System.out.println("email--> "+email2);
		
		

	}

}
