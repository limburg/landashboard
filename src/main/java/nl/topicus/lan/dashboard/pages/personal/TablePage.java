package nl.topicus.lan.dashboard.pages.personal;

import nl.topicus.lan.dashboard.pages.AbstractSecureBasePage;
import nl.topicus.lan.dashboard.panels.TablePanel;

public class TablePage extends AbstractSecureBasePage
{

	private static final long serialVersionUID = 1L;

	public TablePage()
	{
		super();
		add(new TablePanel("tablePanel"));
	}
}
