package com.example.studentapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp2.model.Course;
import com.example.studentapp2.ui.enrolllment.GalleryFragment;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<Course> courseList;
    private final GalleryFragment fragment;

    public CourseAdapter(List<Course> courseList, GalleryFragment fragment) {
        this.courseList = courseList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseName.setText(course.getName());
        holder.courseCredits.setText(course.getCredits() + " SKS");
        holder.checkBox.setChecked(course.isSelected());

        // Update selection state and notify fragment to update credit limit
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            course.setSelected(isChecked);
            fragment.updateCreditLimit(); // Notify the fragment to update credit limit
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseCredits;
        CheckBox checkBox;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.tv_course_name);
            courseCredits = itemView.findViewById(R.id.tv_course_credits);
            checkBox = itemView.findViewById(R.id.cb_course);
        }
    }
}
