package nl.topicus.lan.dashboard.dao.providers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.topicus.cobra.entities.IdObject;
import nl.topicus.lan.dashboard.WicketApplication;
import nl.topicus.lan.dashboard.dao.filters.AbstractZoekFilter;

import org.apache.wicket.protocol.http.WebApplication;

import com.google.common.collect.Lists;

@ApplicationScoped
public abstract class AbstractPersistenceProvider<T extends IdObject, ZF extends AbstractZoekFilter<T>>
{
	private EntityManager em = null;

	public void begin()
	{
		WicketApplication app = ((WicketApplication) WebApplication.get());
		em = app.getPersistenceFactory().createEntityManager();
		em.getTransaction().begin();
	}

	public Query createQuery(CriteriaQuery< ? > query)
	{
		return em.createQuery(query);
	}

	public Query createQuery(String query)
	{
		return em.createQuery(query);
	}

	public void commit()
	{
		em.getTransaction().commit();
	}

	public void persist(T entity)
	{
		em.persist(entity);
	}

	public void remove(T entity)
	{
		em.remove(entity);
	}

	public void end()
	{
		em.close();
	}

	public List<T> list(ZF filter)
	{
		return list(filter, null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> list(ZF filter, Integer first, Integer count)
	{
		List<T> list = null;
		begin();
		CriteriaQuery<T> criteria = createCriteria(filter);
		if (criteria == null)
		{
			end();
			return Lists.newArrayList();
		}
		Query query = createQuery(criteria);
		if (first != null && count != null)
		{
			query.setFirstResult(first);
			query.setMaxResults(count);
		}
		list = query.getResultList();
		end();
		return list;
	}

	public int count(ZF filter)
	{
		int result = 0;
		begin();
		CriteriaQuery<Long> criteria = createCountCriteria(filter);
		if (criteria == null)
		{
			end();
			return 0;
		}
		result = ((Number) createQuery(criteria).getSingleResult()).intValue();
		end();
		return result;
	}

	@SuppressWarnings("unchecked")
	public T get(Long id)
	{
		T entiteit = null;
		String queryString =
			"select e from " + getEntityClass().getSimpleName() + " e where e.id = :id";
		begin();
		Query query = createQuery(queryString);
		query.setParameter("id", id);
		entiteit = (T) query.getSingleResult();
		end();
		return entiteit;
	}

	@SuppressWarnings("unchecked")
	public T getReference(Class< ? > entityClass, Long primaryKey)
	{
		return (T) em.getReference(entityClass, primaryKey);
	}

	protected CriteriaBuilder getCriteriaBuilder()
	{
		return em.getCriteriaBuilder();
	}

	protected CriteriaQuery<T> createCriteria(ZF filter)
	{
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
		Root<T> root = cq.from(getEntityClass());
		cq.select(root);
		List<Predicate> where = createWhere(root, builder, filter);
		if (where != null)
		{
			cq.where(where.toArray(new Predicate[where.size()]));
		}

		if (filter.getOrderByList() != null && filter.getOrderByList().size() > 0)
		{
			List<Order> orderingList = new ArrayList<Order>();

			for (String field : filter.getOrderByList())
			{
				if (filter.isAscending())
					orderingList.add(builder.asc(root.get(field)));
				else
					orderingList.add(builder.desc(root.get(field)));
			}
			cq.orderBy(orderingList);
		}
		return cq;
	}

	protected CriteriaQuery<Long> createCountCriteria(ZF filter)
	{
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<T> root = cq.from(getEntityClass());
		cq.select(builder.count(cq.from(getEntityClass())));
		List<Predicate> where = createWhere(root, builder, filter);
		if (where != null)
		{
			cq.where(where.toArray(new Predicate[where.size()]));
		}
		return cq;
	}

	protected abstract List<Predicate> createWhere(Root<T> root, CriteriaBuilder builder, ZF filter);

	protected abstract Class<T> getEntityClass();

}
