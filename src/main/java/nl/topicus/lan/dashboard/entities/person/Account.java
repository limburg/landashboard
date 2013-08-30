package nl.topicus.lan.dashboard.entities.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import nl.topicus.cobra.entities.RestrictedAccess;
import nl.topicus.lan.dashboard.entities.BaseLanEntity;

import org.hibernate.annotations.AccessType;

@Entity
@AccessType("field")
public class Account extends BaseLanEntity
{

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@RestrictedAccess(hasSetter = false)
	private String username;

	@OneToOne(mappedBy = "account")
	private Profile profile;

	public String getUsername()
	{
		return username;
	}

	protected Account()
	{
	}

	public Account(String username)
	{
		this.username = username;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

}
