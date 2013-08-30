package nl.topicus.lan.dashboard.models;

import nl.topicus.cobra.entities.IdObject;

import org.apache.wicket.model.IModel;

public class ELModelFactory
{
	public static <T extends IdObject> IModel<T> getModel(T entiteit)
	{
		return new EclipseLinkModel<T>(entiteit);
	}
}
