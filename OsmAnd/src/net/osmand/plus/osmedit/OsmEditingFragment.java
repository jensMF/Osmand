package net.osmand.plus.osmedit;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.osmand.plus.OsmAndAppCustomization;
import net.osmand.plus.R;
import net.osmand.plus.helpers.AndroidUiHelper;
import net.osmand.plus.settings.BaseSettingsFragment;
import net.osmand.plus.settings.OnPreferenceChanged;
import net.osmand.plus.settings.bottomsheets.OsmLoginDataBottomSheet;
import net.osmand.plus.settings.preferences.SwitchPreferenceEx;

public class OsmEditingFragment extends BaseSettingsFragment implements OnPreferenceChanged {

	private static final String OPEN_OSM_EDITS = "open_osm_edits";
	private static final String OSM_LOGIN_DATA = "osm_login_data";

	@Override
	protected void setupPreferences() {
		Preference vehicleParametersInfo = findPreference("osm_editing_info");
		vehicleParametersInfo.setIcon(getContentIcon(R.drawable.ic_action_info_dark));

		setupNameAndPasswordPref();
		setupOfflineEditingPref();
		setupOsmEditsDescrPref();
		setupOsmEditsPref();
	}

	@Override
	protected void createToolbar(LayoutInflater inflater, View view) {
		super.createToolbar(inflater, view);

		TextView toolbarSubtitle = view.findViewById(R.id.toolbar_subtitle);
		toolbarSubtitle.setText(getPreferenceScreen().getSummary());
		AndroidUiHelper.updateVisibility(toolbarSubtitle, true);
	}

	private void setupNameAndPasswordPref() {
		Preference nameAndPasswordPref = findPreference(OSM_LOGIN_DATA);
		nameAndPasswordPref.setSummary(settings.USER_NAME.get());
		nameAndPasswordPref.setIcon(getContentIcon(R.drawable.ic_action_openstreetmap_logo));
	}

	private void setupOfflineEditingPref() {
		SwitchPreferenceEx offlineEditingPref = (SwitchPreferenceEx) findPreference(settings.OFFLINE_EDITION.getId());
		offlineEditingPref.setDescription(getString(R.string.offline_edition_descr));
	}

	private void setupOsmEditsDescrPref() {
		Preference nameAndPasswordPref = findPreference("osm_edits_description");
		nameAndPasswordPref.setTitle(getText(R.string.osm_edits_view_descr));
	}

	private void setupOsmEditsPref() {
		Preference createProfile = findPreference(OPEN_OSM_EDITS);
		createProfile.setIcon(getActiveIcon(R.drawable.ic_action_folder));
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (OPEN_OSM_EDITS.equals(preference.getKey())) {
			OsmAndAppCustomization appCustomization = app.getAppCustomization();
			Intent favorites = new Intent(preference.getContext(), appCustomization.getFavoritesActivity());
			favorites.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			app.getSettings().FAVORITES_TAB.set(R.string.osm_edits);
			startActivity(favorites);
			return true;
		} else if (OSM_LOGIN_DATA.equals(preference.getKey())) {
			FragmentManager fragmentManager = getFragmentManager();
			if (fragmentManager != null) {
				OsmLoginDataBottomSheet.showInstance(fragmentManager, OSM_LOGIN_DATA, this, false, getSelectedAppMode());
				return true;
			}
		}
		return super.onPreferenceClick(preference);
	}

	@Override
	public void onPreferenceChanged(String prefId) {
		if (OSM_LOGIN_DATA.equals(prefId)) {
			Preference nameAndPasswordPref = findPreference(OSM_LOGIN_DATA);
			nameAndPasswordPref.setSummary(settings.USER_NAME.get());
		}
	}
}