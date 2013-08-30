package nl.topicus.lan.dashboard.panels;

import javax.inject.Inject;

import nl.topicus.lan.dashboard.pages.personal.ProfilePage;
import nl.topicus.lan.dashboard.security.AuthenticationUtil;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class LoginPanel extends Panel
{

	@Inject
	private nl.topicus.lan.dashboard.dao.providers.AccountProvider provider;

	private static final long serialVersionUID = 1L;

	public LoginPanel(String id)
	{
		super(id);

		if (AuthenticationUtil.isLoggedIn())
		{
			setResponsePage(ProfilePage.class);
			return;
		}

		Form<Void> form = new Form<Void>("form")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				String username = get("username").getDefaultModelObjectAsString();
				String password = get("password").getDefaultModelObjectAsString();

				boolean authenticated = true;
				try
				{
					AuthenticationUtil.loggedOn(provider, username, password);

				}
				catch (AuthenticationException e)
				{
					authenticated = false;
				}
				catch (Exception e)
				{
					authenticated = false;
					error("Unknown login error");
				}

				if (authenticated)
				{
					throw new RestartResponseAtInterceptPageException(ProfilePage.class);
				}
				else
				{
					error("Geen correcte username/password combinatie");
				}

			}
		};
		add(form);
		form.add(new TextField<String>("username", new Model<String>()).setRequired(true));
		form.add(new PasswordTextField("password", new Model<String>()));
		form.add(new SubmitLink("login", form));
	}
}
