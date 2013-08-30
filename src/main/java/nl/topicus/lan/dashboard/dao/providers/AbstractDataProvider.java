package nl.topicus.lan.dashboard.dao.providers;

import java.io.Serializable;
import java.util.Iterator;

import nl.topicus.cobra.entities.IdObject;
import nl.topicus.cobra.util.ComponentUtil;
import nl.topicus.lan.dashboard.dao.filters.AbstractZoekFilter;
import nl.topicus.lan.dashboard.models.ELModelFactory;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public abstract class AbstractDataProvider<T extends IdObject, ZF extends AbstractZoekFilter<T>>
		implements IDataProvider<T>, Serializable
{

	private static final long serialVersionUID = 1L;

	private ZF filter;

	public AbstractDataProvider(ZF filter)
	{
		this.filter = filter;
	}

	@Override
	public void detach()
	{
		ComponentUtil.detachQuietly(filter);
	}

	@Override
	public Iterator< ? extends T> iterator(long first, long count)
	{
		AbstractPersistenceProvider<T, ZF> provider = getProvider();
		return provider.list(filter, new Long(first).intValue(), new Long(count).intValue())
			.iterator();
	}

	@Override
	public long size()
	{
		return getProvider().count(filter);
	}

	@Override
	public IModel<T> model(T object)
	{
		return ELModelFactory.getModel(object);
	}

	protected abstract AbstractPersistenceProvider<T, ZF> getProvider();

}
