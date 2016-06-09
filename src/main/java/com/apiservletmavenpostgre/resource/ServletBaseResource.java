/**
 * 
 */
package com.apiservletmavenpostgre.resource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author eloi eloibilek@gmail.com
 */
public class ServletBaseResource extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * User id catch for using in methods find or delete.
	 */
	public Long getIdParameter(HttpServletRequest request) throws Exception {

		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.equals("/")) {
			return null;
		} else {
			String[] splits = pathInfo.split("/");
			Long id = Long.parseLong(splits[1]);
			return id;
		}
	}

}
