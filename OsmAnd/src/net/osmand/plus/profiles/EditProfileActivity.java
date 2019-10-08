package net.osmand.plus.profiles;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import net.osmand.plus.R;
import net.osmand.plus.activities.OsmandActionBarActivity;

public class EditProfileActivity extends OsmandActionBarActivity {

	public static final int DELETE_ID = 1010;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		getMyApplication().applyTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_fragment_layout);

		if (savedInstanceState == null) {
			EditProfileFragment editProfileFragment = new EditProfileFragment();
			editProfileFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(android.R.id.content,
					editProfileFragment, EditProfileFragment.TAG).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int activeButtonsAndLinksTextResId = getMyApplication().getSettings().isLightContent() ?
				R.color.active_buttons_and_links_text_light : R.color.active_buttons_and_links_text_dark;
		Drawable icDelete = getMyApplication().getUIUtilities().getIcon(R.drawable.ic_action_delete_dark, activeButtonsAndLinksTextResId);
		MenuItem m = menu.add(0, DELETE_ID, 0, R.string.action_delete)
			.setIcon(icDelete);
		MenuItemCompat.setShowAsAction(m, MenuItem.SHOW_AS_ACTION_ALWAYS);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();
		switch (itemId) {
			case android.R.id.home:
				onBackPressed();
				return true;
			case DELETE_ID:
				((EditProfileFragment) getSupportFragmentManager().findFragmentByTag(
						EditProfileFragment.TAG)).onDeleteProfileClick();
				return true;

		}
		return false;
	}

	@Override
	public void onBackPressed() {
		final EditProfileFragment epf = (EditProfileFragment) getSupportFragmentManager()
			.findFragmentByTag(EditProfileFragment.TAG);
		if (epf.onBackPressedAllowed()) {
			super.onBackPressed();
		} else {
			epf.confirmCancelDialog(this);
		}

	}
}
