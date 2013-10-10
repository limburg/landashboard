package nl.topicus.lan.dashboard.pages.personal;

import nl.topicus.cobra.web.components.form.AutoFieldSet;
import nl.topicus.cobra.web.components.form.RenderMode;
import nl.topicus.cobra.web.components.form.modifier.LabelModifier;
import nl.topicus.lan.dashboard.DashboardSession;
import nl.topicus.lan.dashboard.entities.person.Account;
import nl.topicus.lan.dashboard.entities.person.Profile;
import nl.topicus.lan.dashboard.models.ELModelFactory;
import nl.topicus.lan.dashboard.pages.AbstractSecureBasePage;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ProfilePage extends AbstractSecureBasePage
{
	private static final long serialVersionUID = 1L;

	private Form<Void> form;

	private IModel<Profile> profileModel;

	public ProfilePage()
	{
		super();

		initializeModel();
		initializeForm();
	}

	private void initializeModel()
	{
		Profile newProfile;
		if (DashboardSession.get().getAccount().getObject().getProfile() != null)
		{
			newProfile = DashboardSession.get().getAccount().getObject().getProfile();
			profileModel = ELModelFactory.getModel(newProfile);
		}
		else
		{
			newProfile = new Profile();
			newProfile.setAccount(DashboardSession.get().getAccount().getObject());
			profileModel = new Model<Profile>(newProfile);
		}
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

				Account acc = DashboardSession.get().getAccount().getObject();
				if (acc.getProfile() == null)
				{
					acc.setProfile(profileModel.getObject());
					acc.updateAndCommit();
				}

				setResponsePage(TablePage.class);
			}
		};

		form.add(fieldset.setOutputMarkupId(true));
		add(form.setOutputMarkupId(true));
	}

	@Override
	public void onDetach()
	{
		if (profileModel != null)
			this.profileModel.detach();
		super.onDetach();
	}

}
