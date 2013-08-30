package nl.topicus.lan.dashboard.components;

import java.io.Serializable;
import java.util.Iterator;

import nl.topicus.cobra.web.components.dataview.ExportableDataView;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class ClickableDataView<T extends Serializable> extends ExportableDataView<T>
{

	private static final long serialVersionUID = 1L;

	public ClickableDataView(String id, IDataProvider<T> dataProvider)
	{
		super(id, dataProvider);
	}

	public abstract void onClick(Item<T> item);

	@Override
	protected Item<T> newItem(String id, int index, IModel<T> model)
	{
		return new LinkItem<T>(id, index, model);
	}

	/**
	 * Items worden alleen hoverable gemaakt als het item clickable is.
	 */
	@Override
	protected void addItems(Iterator<Item<T>> items)
	{
		while (items.hasNext())
		{
			LinkItem<T> linkItem = (LinkItem<T>) items.next();
			add(linkItem);
			linkItem.addOnClickAttribute();
		}
	}

	public static class LinkItem<T extends Serializable> extends Item<T> implements ILinkListener
	{
		private static final long serialVersionUID = 1L;

		public LinkItem(String id, int index, IModel<T> model)
		{
			super(id, index, model);
		}

		@Override
		protected boolean getStatelessHint()
		{
			return false;
		}

		@Override
		public void onLinkClicked()
		{
			ClickableDataView<T> dataView = getDataview();
			dataView.onClick(this);
		}

		@SuppressWarnings("unchecked")
		protected ClickableDataView<T> getDataview()
		{
			ClickableDataView<T> dataView = (ClickableDataView<T>) this.getParent();
			return dataView;
		}

		/**
		 * Voegt de onClick event toe aan de listitem. Indien dit is toegestaan.
		 */
		public void addOnClickAttribute()
		{
			// creeer de attribuut die de 'onClick' afhandelt.
			add(new AttributeModifier("onclick", new Model<String>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public String getObject()
				{
					CharSequence urlForLink = LinkItem.this.urlFor(ILinkListener.INTERFACE, null);
					return "javascript:location.href='" + urlForLink + "';";
				}
			}));
			add(new AttributeAppender("style", "cursor: pointer;"));
		}

	}

}
