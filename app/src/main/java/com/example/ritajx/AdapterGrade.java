package com.example.ritajx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdapterGrade extends RecyclerView.Adapter<AdapterGrade.MyViewHolder> {
    private List<GradeObject> grades; // Update to use GradeObject

    // Constructor updated to use List<GradeObject>
    public AdapterGrade(List<GradeObject> grades) {
        this.grades = grades;
    }

    @NonNull
    @Override
    public AdapterGrade.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Update to bind the GradeObject data to the view
        GradeObject grade = grades.get(position);
        holder.textViewCourseName.setText(grade.getCourseName());
        holder.textViewGradeValue.setText(grade.getGradeDescription());
        holder.textViewGradeType.setText(String.valueOf(grade.getGradeValue())); // Assuming gradeValue is a double/float
        holder.textViewGradeDescription.setText(grade.getGradeType());
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    // Updated MyViewHolder to include references to all needed views
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCourseName, textViewGradeType, textViewGradeValue, textViewGradeDescription;

        public MyViewHolder(View v) {
            super(v);
            textViewCourseName = v.findViewById(R.id.textViewCourseName);
            textViewGradeType = v.findViewById(R.id.textViewGradeType);
            textViewGradeValue = v.findViewById(R.id.Value); // Make sure the ID matches your layout XML
            textViewGradeDescription = v.findViewById(R.id.gradeDiscreption); // Make sure the ID matches your layout XML
        }
    }

    // Method to update the data in the adapter
    public void updateGrades(List<GradeObject> newGrades) {
        grades.clear();
        grades.addAll(newGrades);
        notifyDataSetChanged();
    }
}
