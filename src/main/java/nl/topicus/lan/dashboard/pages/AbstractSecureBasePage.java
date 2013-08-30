package nl.topicus.lan.dashboard.pages;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class AbstractSecureBasePage extends AbstractBasePage
{

	private static final long serialVersionUID = 1L;

	public AbstractSecureBasePage()
	{
		authenticate(null);
	}

	public AbstractSecureBasePage(PageParameters parameters)
	{
		authenticate(parameters);
	}

	private void authenticate(PageParameters parameters)
	{
		Subject user = SecurityUtils.getSubject();

		if (!user.isAuthenticated())
		{
			throw new RestartResponseAtInterceptPageException(HomePage.class, parameters);
		}
	}
}
