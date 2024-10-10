package com.nguyenhungtuan.lab2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nguyenhungtuan.lab2.Model.Employee;
import com.nguyenhungtuan.lab2.R;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private Activity context;
    private List<Employee> employees;

    public EmployeeAdapter(Activity context, int layoutID, List<Employee> objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.employees = objects;
    }

    static class ViewHolder {
        TextView tvFullName;
        TextView tvPosition;
        ImageView ivManager;
        LinearLayout llParent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // reuse existing view if available
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item__employee, parent, false);
            holder = new ViewHolder();
            holder.tvFullName = convertView.findViewById(R.id.item_employee_tv_fullname);
            holder.tvPosition = convertView.findViewById(R.id.item_employee_tv_position);
            holder.ivManager = convertView.findViewById(R.id.item_employee_iv_manager);
            holder.llParent = (LinearLayout) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set employee data
        setEmployeeData(holder, getItem(position));

        // set background color for alternating rows
        holder.llParent.setBackgroundResource(position % 2 == 0 ? R.color.white : R.color.light_blue);

        return convertView;
    }

    private void setEmployeeData(ViewHolder holder, Employee employee) {
        holder.tvFullName.setText(employee.getFullName());
        if (employee.isManager()) {
            holder.ivManager.setVisibility(View.VISIBLE);
            holder.tvPosition.setVisibility(View.GONE);
        } else {
            holder.ivManager.setVisibility(View.GONE);
            holder.tvPosition.setVisibility(View.VISIBLE);
            holder.tvPosition.setText(context.getString(R.string.staff));
        }
    }

    public boolean doesIdExist(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return true; // ID already exists
            }
        }
        return false; // ID is unique
    }
}
