package nl.topicus.lan.dashboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.AccessType;

@Entity
@AccessType("field")
public class Lan extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	public Lan()
	{

	}

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
