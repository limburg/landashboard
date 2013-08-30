package nl.topicus.lan.dashboard;

import nl.topicus.lan.dashboard.entities.person.Account;

import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class DashboardSession extends WebSession
{
	private static final long serialVersionUID = 1L;

	private boolean loggedIn;

	private IModel<Account> account = null;

	public static DashboardSession get()
	{
		return (DashboardSession) WebSession.get();
	}

	public DashboardSession(Request request)
	{
		super(request);
	}

	public IModel<Account> getAccount()
	{
		return account;
	}

	public void setAccount(IModel<Account> account)
	{
		this.account = account;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn)
	{
		this.loggedIn = loggedIn;
	}

}
