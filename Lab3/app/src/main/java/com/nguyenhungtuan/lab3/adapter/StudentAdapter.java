package com.nguyenhungtuan.lab3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenhungtuan.lab3.R;
import com.nguyenhungtuan.lab3.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> studentList;
    private OnStudentClickListener onStudentClickListener;

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
        void onDeleteClick(int studentId);
    }

    public StudentAdapter(List<Student> studentList, OnStudentClickListener listener) {
        this.studentList = studentList;
        this.onStudentClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvStudentId.setText(student.getStudentId());
        holder.tvName.setText(student.getName());
        holder.tvPhoneNumber.setText(student.getPhoneNumber());
        holder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(student));
        holder.btnDelete.setOnClickListener(v -> onStudentClickListener.onDeleteClick(student.getId()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentId, tvName, tvPhoneNumber;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentId = itemView.findViewById(R.id.tv_student_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
