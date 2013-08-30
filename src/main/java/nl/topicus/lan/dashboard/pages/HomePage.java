package nl.topicus.lan.dashboard.pages;

import nl.topicus.lan.dashboard.panels.LoginPanel;

public class HomePage extends AbstractBasePage
{
	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		add(new LoginPanel("loginPanel"));
	}

}
