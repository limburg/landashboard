package nl.topicus.lan.dashboard.entities;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import nl.topicus.lan.dashboard.dao.filters.LanZoekFilter;
import nl.topicus.lan.dashboard.dao.providers.LanProvider;

import org.hibernate.annotations.AccessType;

@MappedSuperclass
@AccessType("field")
public class BaseLanEntity extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	public static final String defaultLan = "TopiLan";

	@JoinColumn(nullable = false)
	private Lan lan;

	public BaseLanEntity()
	{
		// setDefaultLan();
	}

	public void setDefaultLan()
	{
		if (getLan() == null)
		{
			LanProvider provider = new LanProvider();
			LanZoekFilter zf = new LanZoekFilter();
			zf.setName(defaultLan);

			setLan(provider.list(new LanZoekFilter()).get(0));
		}
	}

	public Lan getLan()
	{
		return lan;
	}

	public void setLan(Lan lan)
	{
		this.lan = lan;
	}

	@Override
	public void save()
	{
		setDefaultLan();
		super.save();
	}

	@Override
	public void update()
	{
		setDefaultLan();
		super.update();
	}
}
