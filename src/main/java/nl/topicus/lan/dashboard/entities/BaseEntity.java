package nl.topicus.lan.dashboard.entities;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import nl.topicus.cobra.entities.TransientIdObject;
import nl.topicus.lan.dashboard.WicketApplication;

import org.apache.wicket.protocol.http.WebApplication;
import org.hibernate.annotations.AccessType;

@MappedSuperclass
@AccessType("field")
public class BaseEntity implements Serializable, TransientIdObject
{
	private static final long serialVersionUID = 1L;

	@Transient
	private EntityManager em = null;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdSequence")
	@AccessType("property")
	private Long id;

	@Transient
	private Serializable temporaryId;

	@Version
	private Long version = 0L;

	public BaseEntity()
	{

	}

	private void startTransaction()
	{
		if (em == null)
		{
			WicketApplication app = ((WicketApplication) WebApplication.get());
			em = app.getPersistenceFactory().createEntityManager();
		}

		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
	}

	private void endTransaction()
	{
		if (em != null && em.getTransaction().isActive())
		{
			em.close();
			em = null;
		}
	}

	@Override
	public Serializable getIdAsSerializable()
	{
		return getId();
	}

	@Override
	public boolean isSaved()
	{
		return (getId() != null && getId().longValue() > 0);
	}

	@Override
	public Long getVersion()
	{
		return version;
	}

	@Override
	public void setVersion(Long version)
	{
		this.version = version;
	}

	@Override
	public Serializable getTemporaryId()
	{
		return temporaryId;
	}

	@Override
	public void setTemporaryId(Serializable tempId)
	{
		this.temporaryId = tempId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		if (getTemporaryId() != null)
			return getTemporaryId().hashCode();
		if (getId() != null)
			return getId().hashCode();
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj instanceof BaseEntity)
		{
			BaseEntity other = (BaseEntity) obj;
			if (getTemporaryId() != null)
				return getTemporaryId().equals(other.getTemporaryId());

			if (getId() != null)
				return getId().equals(other.getId());
			// Else fallthrough naar false...
		}
		return false;
	}

	public void save()
	{
		startTransaction();
		em.persist(this);
	}

	public void update()
	{
		startTransaction();
		em.merge(this);
	}

	public void saveOrUpdate()
	{
		if (id != null && id > 0)
			update();
		else
			save();
	}

	public void delete()
	{
		startTransaction();
		em.remove(this);
	}

	public void commit()
	{
		em.getTransaction().commit();
		endTransaction();
	}

	public void saveAndCommit()
	{
		save();
		commit();
	}

	public void updateAndCommit()
	{
		update();
		commit();
	}

	public void saveOrUpdateAndCommit()
	{
		saveOrUpdate();
		commit();
	}

	public void deleteAndCommit()
	{
		delete();
		commit();
	}

	public void rollback()
	{
		em.getTransaction().rollback();
		endTransaction();
	}
}
