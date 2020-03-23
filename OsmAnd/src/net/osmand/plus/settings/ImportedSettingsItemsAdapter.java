package net.osmand.plus.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.osmand.AndroidUtils;
import net.osmand.plus.OsmandApplication;
import net.osmand.plus.R;
import net.osmand.plus.UiUtilities;
import net.osmand.plus.settings.ExportImportSettingsAdapter.Type;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ImportedSettingsItemsAdapter extends
		RecyclerView.Adapter<ImportedSettingsItemsAdapter.ItemViewHolder> {
	private Map<Type, List<?>> itemsMap;
	private List<Type> itemsTypes;
	private UiUtilities uiUtils;
	private OsmandApplication app;
	private boolean nightMode;
	private OnItemClickListener listener;

	ImportedSettingsItemsAdapter(@NonNull OsmandApplication app, Map<Type, List<?>> itemsMap,
								 boolean nightMode, OnItemClickListener listener) {
		this.app = app;
		this.itemsMap = itemsMap;
		this.nightMode = nightMode;
		this.listener = listener;
		uiUtils = app.getUIUtilities();
		itemsTypes = new ArrayList<>(itemsMap.keySet());
		Collections.sort(itemsTypes);
	}

	@NonNull
	@Override
	public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = UiUtilities.getInflater(app, nightMode);
		View view = inflater.inflate(R.layout.list_item_import, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
		final Type currentItemType = itemsTypes.get(position);
		boolean isLastItem = itemsTypes.size() - 1 == position;
		int activeColorRes = nightMode
				? R.color.active_color_primary_dark
				: R.color.active_color_primary_light;

		holder.icon.setPadding(0, 0, AndroidUtils.dpToPx(app, 16), 0);
		holder.title.setTextColor(app.getResources().getColor(activeColorRes));
		holder.divider.setVisibility(isLastItem ? View.VISIBLE : View.GONE);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onItemClick(currentItemType);
			}
		});
		holder.subTitle.setText(String.format(
				app.getString(R.string.ltr_or_rtl_combine_via_colon),
				app.getString(R.string.items_added),
				String.valueOf(itemsMap.get(currentItemType).size()))
		);

		switch (currentItemType) {
			case PROFILE:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.map_action_settings, activeColorRes));
				holder.title.setText(R.string.shared_string_settings);
				break;
			case QUICK_ACTIONS:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.map_quick_action, activeColorRes));
				holder.title.setText(R.string.configure_screen_quick_action);
				break;
			case POI_TYPES:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.ic_action_search_dark, activeColorRes));
				holder.title.setText(R.string.search_activity);
				break;
			case MAP_SOURCES:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.ic_action_layers, activeColorRes));
				holder.title.setText(R.string.configure_map);
				break;
			case CUSTOM_RENDER_STYLE:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.ic_action_map_style, activeColorRes));
				holder.title.setText(R.string.shared_string_rendering_style);
				break;
			case CUSTOM_ROUTING:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.ic_action_route_distance, activeColorRes));
				holder.title.setText(R.string.shared_string_routing);
				break;
			case AVOID_ROADS:
				holder.icon.setImageDrawable(uiUtils.getIcon(R.drawable.ic_action_alert, activeColorRes));
				holder.title.setText(R.string.avoid_road);
				break;
		}
	}

	@Override
	public int getItemCount() {
		return itemsMap.keySet().size();
	}

	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		ImageView icon;
		TextView title;
		TextView subTitle;
		View divider;

		ItemViewHolder(View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title);
			subTitle = itemView.findViewById(R.id.sub_title);
			icon = itemView.findViewById(R.id.icon);
			divider = itemView.findViewById(R.id.bottom_divider);
		}
	}

	interface OnItemClickListener {
		void onItemClick(Type type);
	}
}
