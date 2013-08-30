package nl.topicus.lan.dashboard.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

@Provider
@Path("/tableservice")
public class TableService
{
	@GET
	@Path("/")
	public String getMessage()
	{
		return "HelloWorld";
	}
}
