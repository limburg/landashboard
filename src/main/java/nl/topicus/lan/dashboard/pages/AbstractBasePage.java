package nl.topicus.lan.dashboard.pages;

import nl.topicus.lan.dashboard.resources.bootstrap.BootstrapHeaderItem;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class AbstractBasePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public AbstractBasePage()
	{
		this(null);
	}

	public AbstractBasePage(@SuppressWarnings("unused") PageParameters parameters)
	{
		add(new FeedbackPanel("feedback")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String getCSSClass(FeedbackMessage message)
			{
				if (message.isError())
				{
					return "alert alert-error";
				}
				if (message.isInfo())
				{
					return "alert alert-info";
				}
				return super.getCSSClass(message);
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.render(BootstrapHeaderItem.get());
		super.renderHead(response);
	}

}
