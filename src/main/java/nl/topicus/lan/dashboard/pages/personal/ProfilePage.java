package nl.topicus.lan.dashboard.pages.personal;

import nl.topicus.cobra.web.components.form.AutoFieldSet;
import nl.topicus.cobra.web.components.form.RenderMode;
import nl.topicus.cobra.web.components.form.modifier.LabelModifier;
import nl.topicus.lan.dashboard.DashboardSession;
import nl.topicus.lan.dashboard.entities.person.Profile;
import nl.topicus.lan.dashboard.models.ELModelFactory;
import nl.topicus.lan.dashboard.pages.AbstractSecureBasePage;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class ProfilePage extends AbstractSecureBasePage
{

	private static final long serialVersionUID = 1L;

	private Form<Void> form;

	private IModel<Profile> profileModel;

	public ProfilePage()
	{
		super();

		if (profileModel == null)
			profileModel = initializeModel();

		initializeForm();
	}

	/**
	 * Initialize form
	 */
	private void initializeForm()
	{
		AutoFieldSet<Profile> fieldset =
			new AutoFieldSet<Profile>("fieldset", profileModel, "Profile");
		fieldset.setRenderMode(RenderMode.EDIT);
		fieldset.setPropertyNames("nickname", "name", "surname", "lastname", "tableNumber");
		fieldset.addFieldModifier(new LabelModifier("gender", "Gender"));
		fieldset.setSortAccordingToPropertyNames(true);

		form = new Form<Void>("form")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{

				profileModel.getObject().saveOrUpdateAndCommit();
			}
		};

		form.add(fieldset.setOutputMarkupId(true));

		add(form.setOutputMarkupId(true));
	}

	/**
	 * Returns new profile, or current profile as model.
	 */
	private IModel<Profile> initializeModel()
	{
		IModel<Profile> model = null;
		/*
		 * if (DashboardSession.get().getAccount().getObject().getProfile() != null) {
		 * model = ELModelFactory.getModel(DashboardSession.get().getAccount().getObject()
		 * .getProfile()); } else
		 */
		{
			Profile newProfile = new Profile();
			newProfile.setAccount(DashboardSession.get().getAccount().getObject());
			model = ELModelFactory.getModel(newProfile);
		}
		return model;
	}

	@Override
	public void onDetach()
	{
		profileModel.detach();
	}
}
