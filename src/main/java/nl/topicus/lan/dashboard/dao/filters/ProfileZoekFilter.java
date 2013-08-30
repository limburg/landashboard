package nl.topicus.lan.dashboard.dao.filters;

import nl.topicus.lan.dashboard.entities.person.Profile;

public class ProfileZoekFilter extends AbstractZoekFilter<Profile>
{
	private static final long serialVersionUID = 1L;

	private Long accountId;

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}

}
