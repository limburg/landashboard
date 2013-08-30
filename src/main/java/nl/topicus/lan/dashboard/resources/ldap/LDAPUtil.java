package nl.topicus.lan.dashboard.resources.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import com.sun.jndi.ldap.LdapCtxFactory;

/**
 * Authentiseer een gebruiker met LDAP
 * 
 * @author Joost
 */
public class LDAPUtil
{
	public static boolean authenticate(String server, String domain, String username,
			String password)
	{
		boolean authenticated = false;

		Hashtable<String, String> props = new Hashtable<String, String>();
		String principalName = username + "@" + domain;
		props.put(Context.SECURITY_PRINCIPAL, principalName);
		props.put(Context.SECURITY_CREDENTIALS, password);

		DirContext context = null;
		try
		{
			context = LdapCtxFactory.getLdapCtxInstance("ldap://" + server + '/', props);

			// Authenticated:
			authenticated = true;

		}
		catch (AuthenticationException a)
		{
			// Authenticating failed"
			authenticated = false;
		}
		catch (NamingException e)
		{
			authenticated = false;
		}

		if (context == null)
			authenticated = false;

		return authenticated;
	}
}
