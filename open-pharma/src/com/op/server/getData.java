 package com.op.server;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//http://barappli.appspot.com/getData?password=hh&json

@SuppressWarnings("serial")
public class getData extends BaseServlet {
	
	public void doGet(HttpServletRequest req, final HttpServletResponse resp) throws IOException {	
			
		resp.setContentType("text/plain");
	}		
}