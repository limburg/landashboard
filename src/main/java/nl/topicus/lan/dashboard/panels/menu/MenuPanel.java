package nl.topicus.lan.dashboard.panels.menu;

import nl.topicus.lan.dashboard.pages.HomePage;
import nl.topicus.lan.dashboard.security.AuthenticationUtil;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.google.common.collect.Lists;

public class MenuPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	public MenuPanel(String id, final MenuItem menuItem)
	{
		super(id);
		add(new ListView<MenuItem>("menuItems", Lists.newArrayList(MenuItem.values()))
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<MenuItem> item)
			{
				WebMarkupContainer container = new WebMarkupContainer("container");
				if (item.getModelObject() == menuItem)
				{
					container.add(new AttributeAppender("class", new Model<String>("active")));
				}
				AjaxLink<Void> menuLink = new AjaxLink<Void>("menuLink")
				{

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target)
					{
						setResponsePage(item.getModelObject().getMenuLink());

					}

				};
				menuLink.add(new Label("menuText", item.getModelObject().toString()));
				container.add(menuLink);
				item.add(container);
			}

		});
		add(new Link<Void>("logout")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				AuthenticationUtil.loggedOff();
				setResponsePage(HomePage.class);
			}

		});
	}
}
