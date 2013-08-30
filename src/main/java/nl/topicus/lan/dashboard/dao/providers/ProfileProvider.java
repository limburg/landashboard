package nl.topicus.lan.dashboard.dao.providers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.topicus.lan.dashboard.dao.filters.ProfileZoekFilter;
import nl.topicus.lan.dashboard.entities.person.Profile;

@ApplicationScoped
public class ProfileProvider extends AbstractPersistenceProvider<Profile, ProfileZoekFilter>
{

	@Override
	protected Class<Profile> getEntityClass()
	{
		return Profile.class;
	}

	@Override
	protected List<Predicate> createWhere(Root<Profile> root, CriteriaBuilder cb,
			ProfileZoekFilter filter)
	{
		PredicateBuilder builder = new PredicateBuilder(root, cb);

		if (filter.getAccountId() != null)
			builder.addEq("account", filter.getAccountId());

		return builder.build();
	}

}
