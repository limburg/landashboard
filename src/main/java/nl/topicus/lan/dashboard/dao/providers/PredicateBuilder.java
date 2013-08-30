package nl.topicus.lan.dashboard.dao.providers;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;

public class PredicateBuilder
{
	private List<Predicate> predicates = Lists.newArrayList();

	private final Root< ? > root;

	private final CriteriaBuilder builder;

	public PredicateBuilder(Root< ? > root, CriteriaBuilder builder)
	{
		this.root = root;
		this.builder = builder;
	}

	public PredicateBuilder addEq(String property, Object obj)
	{
		predicates.add(builder.equal(root.<String> get(property), obj));
		return this;
	}

	public PredicateBuilder addLike(String property, Object obj)
	{
		predicates.add(builder.like(root.<String> get(property), "%" + obj + "%"));
		return this;
	}

	public PredicateBuilder addGe(String property, Date obj)
	{
		predicates.add(builder.greaterThanOrEqualTo(root.<Date> get(property), obj));
		return this;
	}

	public PredicateBuilder addLower(String property, Date obj)
	{
		predicates.add(builder.lessThan(root.<Date> get(property), obj));
		return this;
	}

	public PredicateBuilder addIn(String property, List<Long> ids)
	{
		predicates.add(builder.in(root.get(property).in(ids)));
		return this;
	}

	public List<Predicate> build()
	{
		return predicates;
	}
}
