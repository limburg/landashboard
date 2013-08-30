package nl.topicus.lan.dashboard.dao.providers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.topicus.lan.dashboard.dao.filters.AccountZoekFilter;
import nl.topicus.lan.dashboard.entities.person.Account;

@ApplicationScoped
public class AccountProvider extends AbstractPersistenceProvider<Account, AccountZoekFilter>
{

	@Override
	protected Class<Account> getEntityClass()
	{
		return Account.class;
	}

	@Override
	protected List<Predicate> createWhere(Root<Account> root, CriteriaBuilder cb,
			AccountZoekFilter filter)
	{
		PredicateBuilder builder = new PredicateBuilder(root, cb);

		if (filter.getUsername() != null)
		{
			builder.addEq("username", filter.getUsername());
		}
		return builder.build();
	}

}