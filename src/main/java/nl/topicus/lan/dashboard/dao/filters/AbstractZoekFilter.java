package nl.topicus.lan.dashboard.dao.filters;

import java.util.ArrayList;
import java.util.List;

import nl.topicus.cobra.entities.IdObject;
import nl.topicus.cobra.zoekfilters.DetachableZoekFilter;

public class AbstractZoekFilter<T extends IdObject> implements DetachableZoekFilter<T>
{

	private static final long serialVersionUID = 1L;

	private boolean resultCacheable = true;

	private boolean ascending = true;

	private List<String> orderByList = new ArrayList<String>();

	@Override
	public String likeGeneration(String searchString)
	{
		searchString = searchString.replace('*', '%');

		if (!searchString.endsWith("%"))
			searchString = searchString + "%";

		return searchString;
	}

	@Override
	public boolean isResultCacheable()
	{
		return resultCacheable;
	}

	@Override
	public void setResultCacheable(boolean cacheable)
	{
		this.resultCacheable = cacheable;
	}

	@Override
	public void addOrderByProperty(String property)
	{
		orderByList.add(property);
	}

	@Override
	public String getOrderBy()
	{
		return orderByList.isEmpty() ? null : orderByList.get(0);
	}

	@Override
	public boolean isAscending()
	{
		return ascending;
	}

	@Override
	public void setAscending(boolean ascending)
	{
		this.ascending = ascending;
	}

	@Override
	public List<String> getOrderByList()
	{
		return orderByList;
	}

	@Override
	public void setOrderByList(List<String> orderByList)
	{
		this.orderByList = orderByList;
	}

	@Override
	public void detach()
	{

	}

}
