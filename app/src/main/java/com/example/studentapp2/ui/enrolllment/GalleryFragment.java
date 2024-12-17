package com.example.studentapp2.ui.enrolllment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp2.R;
import com.example.studentapp2.model.Course;
import com.example.studentapp2.CourseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private Button btnConfirm;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // Max SKS limit
    private static final int MAX_SKSS = 24;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.recycler_courses);
        btnConfirm = root.findViewById(R.id.btn_confirm);

        // Firebase initialization
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize course list
        initializeCourses();

        // Set up RecyclerView
        adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Confirm button action
        btnConfirm.setOnClickListener(v -> validateAndSaveSelectedCourses());

        return root;
    }

    private void initializeCourses() {
        // Add sample courses with different SKS values
        courseList.add(new Course("Mathematics", 4));
        courseList.add(new Course("Computer Science", 3));
        courseList.add(new Course("Physics", 3));
        courseList.add(new Course("English Literature", 2));
        courseList.add(new Course("History", 2));
    }

    private void validateAndSaveSelectedCourses() {
        int totalCredits = 0;

        // Calculate total SKS
        for (Course course : courseList) {
            if (course.isSelected()) {
                totalCredits += course.getCredits();
            }
        }

        // Check if the total SKS exceeds the maximum limit
        if (totalCredits > MAX_SKSS) {
            // Show error message if it exceeds
            Toast.makeText(getContext(), "You can select a maximum of " + MAX_SKSS + " SKS.", Toast.LENGTH_LONG).show();
        } else {
            // Save selected courses to Firebase if valid
            saveSelectedCoursesToFirebase();
        }
    }

    private void saveSelectedCoursesToFirebase() {
        String userId = mAuth.getCurrentUser().getUid(); // Get user ID from Firebase Auth
        DatabaseReference userCoursesRef = mDatabase.child("users").child(userId).child("selected_courses");

        for (Course course : courseList) {
            // Save the course name and its selected status along with SKS
            userCoursesRef.child(course.getName()).setValue(course.isSelected());
            // Optionally save the credits for each course
            mDatabase.child("users").child(userId).child("course_credits").child(course.getName()).setValue(course.getCredits());
        }

        Toast.makeText(getContext(), "Courses saved to Firebase!", Toast.LENGTH_SHORT).show();
    }
}
