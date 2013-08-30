package nl.topicus.lan.dashboard.entities;

import javax.persistence.Column;

public class Lan extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String name;

	public Lan(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
