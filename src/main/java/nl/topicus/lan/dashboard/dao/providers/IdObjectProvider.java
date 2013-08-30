package nl.topicus.lan.dashboard.dao.providers;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.topicus.cobra.entities.IdObject;
import nl.topicus.lan.dashboard.dao.filters.IdObjectZoekFilter;

@Dependent
public class IdObjectProvider<T extends IdObject> extends
		AbstractPersistenceProvider<T, IdObjectZoekFilter<T>>
{

	private Class<T> clazz;

	public IdObjectProvider(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	protected List<Predicate> createWhere(Root<T> root, CriteriaBuilder builder,
			IdObjectZoekFilter<T> filter)
	{
		return null;
	}

	@Override
	protected Class<T> getEntityClass()
	{
		return clazz;
	}

}
