package nl.topicus.lan.dashboard.panels.menu;

import nl.topicus.lan.dashboard.pages.AbstractBasePage;
import nl.topicus.lan.dashboard.pages.personal.ProfilePage;

public enum MenuItem
{
	// Evenementen("Evenementen", EvenementenPage.class),
	// Persoonlijk("Persoonlijk", PersoonlijkPage.class);
	Profile("Profile", ProfilePage.class);

	private final String naam;

	private final Class< ? extends AbstractBasePage> clazz;

	MenuItem(String naam, Class< ? extends AbstractBasePage> clazz)
	{
		this.naam = naam;
		this.clazz = clazz;
	}

	public Class< ? extends AbstractBasePage> getMenuLink()
	{
		return clazz;
	}

	@Override
	public String toString()
	{
		return naam;
	}
}
