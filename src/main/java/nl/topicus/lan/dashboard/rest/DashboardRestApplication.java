package nl.topicus.lan.dashboard.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import nl.topicus.lan.dashboard.rest.services.TableService;

public class DashboardRestApplication extends Application
{
	private Set<Object> singletons = new HashSet<Object>();

	public DashboardRestApplication()
	{
		singletons.add(new TableService());
	}

	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
