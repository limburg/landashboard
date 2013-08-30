package nl.topicus.lan.dashboard.entities.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import nl.topicus.cobra.entities.RestrictedAccess;
import nl.topicus.lan.dashboard.entities.BaseEntity;

import org.hibernate.annotations.AccessType;

@Entity
@AccessType("field")
public class Account extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@RestrictedAccess(hasSetter = false)
	private String username;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
	@JoinColumn(nullable = true)
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
