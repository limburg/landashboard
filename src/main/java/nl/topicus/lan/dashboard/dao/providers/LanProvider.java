package nl.topicus.lan.dashboard.dao.providers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.topicus.lan.dashboard.dao.filters.LanZoekFilter;
import nl.topicus.lan.dashboard.entities.Lan;

@ApplicationScoped
public class LanProvider extends AbstractPersistenceProvider<Lan, LanZoekFilter>
{

	@Override
	protected Class<Lan> getEntityClass()
	{
		return Lan.class;
	}

	@Override
	protected List<Predicate> createWhere(Root<Lan> root, CriteriaBuilder cb, LanZoekFilter filter)
	{
		PredicateBuilder builder = new PredicateBuilder(root, cb);

		if (filter.getName() != null)
		{
			builder.addEq("name", filter.getName());
		}
		return builder.build();
	}

}