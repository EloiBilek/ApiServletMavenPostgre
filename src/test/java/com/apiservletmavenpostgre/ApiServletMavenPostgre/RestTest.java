/**
 * 
 */
package com.apiservletmavenpostgre.ApiServletMavenPostgre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.apiservletmavenpostgre.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author eloi
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestTest {

	private static String URL_TEST = "http://localhost:8080/ApiServletMavenPostgre/v1/users";

	private static long idUser;

	private HttpPost httpPost;

	private HttpGet httpGet;

	private HttpPut httpPut;

	private HttpDelete httpDelete;

	private HttpClient httpClient;

	@Test
	public void a_postTest() {
		User user = new User();
		String responseEntityString = "";

		// Json
		JsonNodeFactory factory = new JsonNodeFactory(false);
		ObjectNode userJson = factory.objectNode();
		userJson.put("id", "");
		userJson.put("name", "Teste JUnit Servlet");
		userJson.put("dateBirth", "2016-05-24T15:02:02");
		userJson.put("email", "testeJunitServlet@test.com");
		userJson.put("active", true);

		// Http Method
		httpPost = new HttpPost(URL_TEST);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
		try {
			httpPost.setEntity(new StringEntity(userJson.toString()));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}

		// Execute call
		httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Response
		HttpEntity responseEntity = httpResponse.getEntity();
		if (responseEntity != null) {
			try {
				responseEntityString = EntityUtils.toString(responseEntity);
			} catch (ParseException | IOException e) {
				fail(e.getMessage());
			}
		}

		// Parse json in response to object user
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(responseEntityString, User.class);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Test of JUnit...
		assertEquals(201, httpResponse.getStatusLine().getStatusCode());
		assertEquals("Teste JUnit Servlet", user.getName());
		assertTrue(user.getId() > 0);

		// Set global atribute
		idUser = user.getId();

		// Dataset print in console
		System.out.println(httpResponse.toString());
		System.out.println(user.toString());
	}

	@Test
	public void b_getTest() {
		List<User> users = new ArrayList<User>();
		ObjectMapper mapper = new ObjectMapper();

		// Http Method
		httpGet = new HttpGet(URL_TEST);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json; charset=UTF-8");

		// Execute call
		httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Loading stream data in response
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		} catch (UnsupportedOperationException | IOException e) {
			fail(e.getMessage());
		}

		// Parse buffer to string
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Parse string to object
		try {
			users = mapper.readValue(response.toString(), new TypeReference<List<User>>() {
			});
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Test of JUnit...
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		assertTrue(users.size() > 0);

		// Dataset print in console
		System.out.println(httpResponse.toString());
		for (User user : users) {
			System.out.println(user.toString());
		}
	}

	@Test
	public void c_putTest() {
		User user = new User();
		String responseEntityString = "";

		// Json
		JsonNodeFactory factory = new JsonNodeFactory(false);
		ObjectNode userJson = factory.objectNode();
		userJson.put("id", idUser);
		userJson.put("name", "Teste JUnit Servlet Put");
		userJson.put("dateBirth", "2016-05-27T11:02:02");
		userJson.put("email", "testeJunitServletPut@test.com");
		userJson.put("active", true);

		// Http Method
		httpPut = new HttpPut(URL_TEST);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json; charset=UTF-8");
		try {
			httpPut.setEntity(new StringEntity(userJson.toString()));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}

		// Execute call
		httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Response
		HttpEntity responseEntity = httpResponse.getEntity();
		if (responseEntity != null) {
			try {
				responseEntityString = EntityUtils.toString(responseEntity);
			} catch (ParseException | IOException e) {
				fail(e.getMessage());
			}
		}

		// Parse json in response to object user
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(responseEntityString, User.class);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Test of JUnit...
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		assertEquals("Teste JUnit Servlet Put", user.getName());
		assertTrue(user.getId() == idUser);

		// Dataset print in console
		System.out.println(httpResponse.toString());
		System.out.println(user.toString());
	}

	@Test
	public void d_deleteTest() {
		// Http Method
		httpDelete = new HttpDelete(URL_TEST+"/"+idUser);
		httpDelete.setHeader("Accept", "application/json");
		httpDelete.setHeader("Content-type", "application/json; charset=UTF-8");

		// Execute call
		httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpDelete);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// JUnit test...
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());

		// Dataset print in console
		System.out.println(httpResponse.toString());
	}
}
