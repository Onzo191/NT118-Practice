package com.nguyenhungtuan.lab2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenhungtuan.lab2.Model.Employee;
import com.nguyenhungtuan.lab2.R;

import java.util.List;

public class RecyclerEmployeeAdapter extends RecyclerView.Adapter<RecyclerEmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employees;

    public RecyclerEmployeeAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.tvFullName.setText(employee.getFullName());
        if (employee.isManager()) {
            holder.ivManager.setVisibility(View.VISIBLE);
            holder.tvPosition.setVisibility(View.GONE);
        } else {
            holder.ivManager.setVisibility(View.GONE);
            holder.tvPosition.setVisibility(View.VISIBLE);
            holder.tvPosition.setText(holder.itemView.getContext().getString(R.string.staff));
        }

        // Alternate row colors (optional)
        holder.itemView.setBackgroundResource(position % 2 == 0 ? R.color.white : R.color.light_blue);

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName;
        TextView tvPosition;
        ImageView ivManager;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.item_employee_tv_fullname);
            tvPosition = itemView.findViewById(R.id.item_employee_tv_position);
            ivManager = itemView.findViewById(R.id.item_employee_iv_manager);
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
