package nl.topicus.lan.dashboard.resources.bootstrap;

import org.apache.wicket.request.resource.CssResourceReference;

public class BootstrapCssReference extends CssResourceReference
{
	private static final long serialVersionUID = 1L;

	private static BootstrapCssReference instance = new BootstrapCssReference();

	private BootstrapCssReference()
	{
		super(BootstrapCssReference.class, "bootstrap.css");
	}

	public static BootstrapCssReference get()
	{
		return instance;
	}
}
