package com.apiservletmavenpostgre.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apiservletmavenpostgre.model.entity.User;
import com.apiservletmavenpostgre.model.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author eloi eloibilek@gmail.com
 */
@WebServlet("/v1/users/*")
public class UserController extends ServletBaseController {

	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
	}

	static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@Inject
	IUserService userService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = new User();
		List<User> users = new ArrayList<User>();
		ObjectMapper mapper = new ObjectMapper();

		resp.setContentType("application/json");

		try {
			Long id = this.getIdParameter(req);

			if (null == id) {
				users = userService.findAll();
				mapper.writeValue(resp.getOutputStream(), users);
			} else {
				user = userService.findById(id);
				mapper.writeValue(resp.getOutputStream(), user);
			}

		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
			mapper.writeValue(resp.getOutputStream(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder json = new StringBuilder();
		BufferedReader reader = req.getReader();
		User user = new User();
		ObjectMapper mapper = new ObjectMapper();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line).append('\n');
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
		} finally {
			reader.close();
		}

		user = mapper.readValue(json.toString(), User.class);

		resp.setContentType("application/json");

		try {
			user = userService.create(user);
			mapper.writeValue(resp.getOutputStream(), user);
		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
			mapper.writeValue(resp.getOutputStream(), e);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder json = new StringBuilder();
		BufferedReader reader = req.getReader();
		User user = new User();
		ObjectMapper mapper = new ObjectMapper();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line).append('\n');
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
		} finally {
			reader.close();
		}

		user = mapper.readValue(json.toString(), User.class);

		resp.setContentType("application/json");

		try {
			user = userService.update(user);
			mapper.writeValue(resp.getOutputStream(), user);
		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
			mapper.writeValue(resp.getOutputStream(), e);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		resp.setContentType("application/json");

		try {
			Long id = this.getIdParameter(req);

			if (null == id) {
				mapper.writeValue(resp.getOutputStream(), "Inform user id to the delete!");
			} else {
				userService.deleteById(id);
				mapper.writeValue(resp.getOutputStream(), "User deleted sucefull.");
			}

		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " - " + e.getCause());
			mapper.writeValue(resp.getOutputStream(), e);
		}
	}
}
