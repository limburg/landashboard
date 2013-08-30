package nl.topicus.lan.dashboard;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start
{
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception
	{
		int timeout = (int) Duration.ONE_HOUR.getMilliseconds();

		Server server = new Server();
		ServerConnector http = new ServerConnector(server, new HttpConnectionFactory());
		http.setPort(8080);
		http.setIdleTimeout(30000);
		http.setSoLingerTime(-1);
		http.setPort(8080);
		server.setConnectors(new Connector[] {http});

		Resource keystore = Resource.newClassPathResource("/keystore");
		if (keystore != null && keystore.exists())
		{
			// if a keystore for a SSL certificate is available, start a SSL
			// connector on port 8443.
			// By default, the quickstart comes with a Apache Wicket Quickstart
			// Certificate that expires about half way september 2021. Do not
			// use this certificate anywhere important as the passwords are
			// available in the source.

			SslContextFactory factory = new SslContextFactory();
			factory.setKeyStoreResource(keystore);
			factory.setKeyStorePassword("wicket");
			factory.setTrustStoreResource(keystore);
			factory.setKeyManagerPassword("wicket");

			// HTTPS Configuration
			HttpConfiguration httpsConfig = new HttpConfiguration();
			httpsConfig.addCustomizer(new SecureRequestCustomizer());

			// HTTPS connector
			ServerConnector sslConnector =
				new ServerConnector(server, new SslConnectionFactory(factory, "http/1.1"),
					new HttpConnectionFactory(httpsConfig));
			sslConnector.setIdleTimeout(500000);

			sslConnector.setIdleTimeout(timeout);
			sslConnector.setPort(8443);
			server.addConnector(sslConnector);

			System.out.println("SSL access to the quickstart has been enabled on port 8443");
			System.out
				.println("You can access the application using SSL on https://localhost:8443");
			System.out.println();
		}

		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");

		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		server.setHandler(bb);

		try
		{
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			server.stop();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
