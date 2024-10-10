package com.nguyenhungtuan.lab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenhungtuan.lab2.Model.Thumbnail;
import com.nguyenhungtuan.lab2.R;

import java.util.List;

public class ThumbnailAdapter extends BaseAdapter {
    private final Context context;
    private final List<Thumbnail> thumbnails;
    private int selectedPosition = -1;

    public ThumbnailAdapter(Context context, List<Thumbnail> thumbnails) {
        this.context = context;
        this.thumbnails = thumbnails;
    }

    @Override
    public int getCount() {
        return thumbnails.size();
    }

    @Override
    public Thumbnail getItem(int position) {
        return thumbnails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getThumbnailView(position, convertView, parent, true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getThumbnailView(position, convertView, parent, false);
    }

    private View getThumbnailView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
        ViewHolder holder;

        if (convertView == null) {
            int layoutId = isDropDown ? R.layout.item__selected_thumbnail : R.layout.item__thumbnail;
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new ViewHolder();
            holder.tvThumbnailName = convertView.findViewById(R.id.tvThumbnailName);
            holder.ivThumbnail = convertView.findViewById(R.id.ivThumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Thumbnail thumbnail = getItem(position);
        if (thumbnail != null) {
            holder.tvThumbnailName.setText(thumbnail.getName());
            holder.ivThumbnail.setImageResource(thumbnail.getImg());
        }

        // Set visibility for dropdown selection
        if (isDropDown && position == selectedPosition) {
            convertView.setBackgroundResource(R.color.light_blue); // Set selected background color if needed
        } else {
            convertView.setBackgroundResource(android.R.color.transparent); // Reset background
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvThumbnailName;
        ImageView ivThumbnail;
    }
}
