package nl.topicus.lan.dashboard.dao.filters;

import nl.topicus.lan.dashboard.entities.Lan;

public class LanZoekFilter extends AbstractZoekFilter<Lan>
{
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
