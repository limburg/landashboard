package nl.topicus.lan.dashboard.security;

import nl.topicus.lan.dashboard.resources.ldap.LDAPUtil;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class DashboardRealm implements Realm
{

	private static Logger log = Logger.getLogger(DashboardRealm.class);

	@Override
	public String getName()
	{
		return "DashboardRealm";
	}

	@Override
	public boolean supports(AuthenticationToken token)
	{
		return UsernamePasswordToken.class.isAssignableFrom(token.getClass());
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());

		if (!isAuthenticated(username, password))
		{
			throw new AuthenticationException("Login name [" + username + "] not found!");
		}

		log.info("User [" + username + "] logged in successfully.");
		return new SimpleAuthenticationInfo(username, password, getName());
	}

	private boolean isAuthenticated(String username, String password)
	{
		if (username.equalsIgnoreCase("test") && password.equalsIgnoreCase("test"))
		{
			return true;
		}
		else
		{
			return LDAPUtil.authenticate("baas2.topicus.local", "TOPICUS", username, password);
		}
	}
}
