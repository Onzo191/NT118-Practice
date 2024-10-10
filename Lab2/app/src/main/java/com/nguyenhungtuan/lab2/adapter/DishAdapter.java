package com.nguyenhungtuan.lab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyenhungtuan.lab2.Model.Dish;
import com.nguyenhungtuan.lab2.R;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    private final Context context;
    private final List<Dish> dishes;

    public DishAdapter(Context context, List<Dish> dishes) {
        super(context, 0, dishes);
        this.context = context;
        this.dishes = dishes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item__dish, parent, false);
            holder = new ViewHolder();
            holder.ivDish = convertView.findViewById(R.id.ivDish);
            holder.tvDishName = convertView.findViewById(R.id.tvDishName);
            holder.ivPromotionStar = convertView.findViewById(R.id.ivPromotionStar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current dish and set its data
        Dish dish = getItem(position);
        if (dish != null) {
            holder.ivDish.setImageResource(dish.getImageResId());
            holder.tvDishName.setText(dish.getName());

            // Show or hide promotion star
            holder.ivPromotionStar.setVisibility(dish.isPromotion() ? View.VISIBLE : View.GONE);
        }

        return convertView;
    }

    // ViewHolder class
    static class ViewHolder {
        ImageView ivDish;
        TextView tvDishName;
        ImageView ivPromotionStar; // Assuming you have this view in your layout
    }
}

