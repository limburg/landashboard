package nl.topicus.lan.dashboard.security;

import java.util.List;

import nl.topicus.lan.dashboard.DashboardSession;
import nl.topicus.lan.dashboard.dao.filters.AccountZoekFilter;
import nl.topicus.lan.dashboard.entities.person.Account;
import nl.topicus.lan.dashboard.models.ELModelFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class AuthenticationUtil
{
	public static boolean isLoggedIn()
	{
		Subject currentUser = SecurityUtils.getSubject();
		return currentUser.isAuthenticated();
	}

	public static void loggedOff()
	{
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		DashboardSession.get().setAccount(null);
	}

	public static void loggedOn(nl.topicus.lan.dashboard.dao.providers.AccountProvider accProvider,
			String username, String password) throws Exception
	{
		// Try login
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(new UsernamePasswordToken(username, password));

		// Set account

		Account acc = null;
		AccountZoekFilter filter = new AccountZoekFilter();
		filter.setUsername(username);

		List<Account> accounts = accProvider.list(filter);

		if (accounts.isEmpty())
		{
			acc = new Account(username);
			acc.saveAndCommit();
		}
		else if (accounts.size() == 1)
		{
			acc = accounts.get(0);
		}
		else
		{
			throw new Exception("Unknown login error.");
		}

		// Set session
		DashboardSession.get().setAccount(ELModelFactory.getModel(acc));

	}
}
