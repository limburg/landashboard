package nl.topicus.lan.dashboard;

import java.math.BigDecimal;

import javax.enterprise.inject.spi.BeanManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.ftlines.wicket.cdi.CdiConfiguration;
import nl.topicus.cobra.converters.BigDecimalConverter;
import nl.topicus.cobra.converters.DoubleConverter;
import nl.topicus.cobra.converters.DutchDateConverter;
import nl.topicus.cobra.converters.TimeConverter;
import nl.topicus.cobra.entities.Time;
import nl.topicus.lan.dashboard.entities.person.Account;
import nl.topicus.lan.dashboard.pages.HomePage;
import nl.topicus.lan.dashboard.pages.personal.ProfilePage;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.jboss.weld.environment.servlet.Listener;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

/**
 * Application object for your web application. If you want to run this application
 * without deploying, run the Start class.
 * 
 * @see nl.topicus.lan.dashboard.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	private static final String PERSISTENCE_UNIT_NAME = "todos";

	private static EntityManagerFactory factory = Persistence
		.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		Application.get().getMarkupSettings().setStripWicketTags(true);

		BeanManager manager =
			(BeanManager) getServletContext().getAttribute(Listener.BEAN_MANAGER_ATTRIBUTE_NAME);

		new CdiConfiguration(manager).configure(this);

		// Test data:
		addTestData();
		setSecuritySettings();
		setBookmarkablePages();
	}

	private void setSecuritySettings()
	{
		// Configure Shiro
		AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(authz);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
			new ShiroUnauthorizedComponentListener(HomePage.class, HomePage.class, authz));

	}

	private void addTestData()
	{
		Account newAccount = new Account("test");
		newAccount.saveOrUpdateAndCommit();
	}

	@Override
	protected IConverterLocator newConverterLocator()
	{
		return new ConverterFactory();
	}

	public static final class ConverterFactory extends ConverterLocator
	{
		private static final long serialVersionUID = 1L;

		public ConverterFactory()
		{
			DateConverter dateConverter = new DutchDateConverter();

			// voeg alle java.util.Date afgeleidingen toe, aangezien onze
			// grote Hibernate vriend van alles terug kan geven.
			set(java.util.Date.class, dateConverter);
			set(java.sql.Date.class, dateConverter);
			set(java.sql.Timestamp.class, dateConverter);

			set(BigDecimal.class, new BigDecimalConverter());

			DoubleConverter doubleConverter = new DoubleConverter();
			set(Double.TYPE, doubleConverter);
			set(Double.class, doubleConverter);
			set(Time.class, new TimeConverter());
			set(nl.topicus.cobra.entities.Time.class,
				new nl.topicus.cobra.converters.TimeConverter());
		}
	}

	private void setBookmarkablePages()
	{
		mountPage("start/profile", ProfilePage.class);
		mountPage("start/", HomePage.class);
	}

	public EntityManagerFactory getPersistenceFactory()
	{
		return factory;
	}

	@Override
	public DashboardSession newSession(Request request, Response response)
	{
		return new DashboardSession(request);
	}
}
