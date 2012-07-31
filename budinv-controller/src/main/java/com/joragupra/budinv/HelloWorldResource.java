package com.joragupra.budinv;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Hello world!
 * 
 */
@Path("/helloworld")
public class HelloWorldResource {
	
	@GET
	@Produces("text/plain")
	public String main() {
		return "Hello World!";
	}
}
