package nl.topicus.lan.dashboard.dao.filters;

import nl.topicus.lan.dashboard.entities.person.Account;

public class AccountZoekFilter extends AbstractZoekFilter<Account>
{
	private static final long serialVersionUID = 1L;

	private String username;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

}
