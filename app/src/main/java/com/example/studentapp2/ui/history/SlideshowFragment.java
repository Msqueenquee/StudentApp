package com.example.studentapp2.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentapp2.R;
import com.example.studentapp2.model.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private TextView textView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<Course> courseList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        textView = root.findViewById(R.id.text_slideshow);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Fetch selected courses from Firebase
        fetchSelectedCourses();

        return root;
    }

    private void fetchSelectedCourses() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userCoursesRef = mDatabase.child("users").child(userId).child("selected_courses");
        DatabaseReference courseCreditsRef = mDatabase.child("users").child(userId).child("course_credits");

        userCoursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                // Use AtomicInteger to allow modification inside anonymous class
                final int[] totalCredits = {0};

                // Loop through the courses and add to courseList
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String courseName = courseSnapshot.getKey();
                    boolean isSelected = courseSnapshot.getValue(Boolean.class);

                    // Only add selected courses
                    if (isSelected) {
                        // Fetch SKS for the selected course
                        courseCreditsRef.child(courseName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot creditsSnapshot) {
                                int courseCredits = creditsSnapshot.getValue(Integer.class);
                                Course course = new Course(courseName, courseCredits);
                                courseList.add(course);
                                totalCredits[0] += courseCredits;  // Add SKS to total

                                // After adding all courses, update the text
                                updateText(totalCredits[0]);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), "Failed to load course credits", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load courses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateText(int totalCredits) {
        if (courseList.size() > 0) {
            StringBuilder historyText = new StringBuilder("Selected Courses:\n");
            for (Course course : courseList) {
                historyText.append(course.getName()).append(" - ").append(course.getCredits()).append(" SKS\n");
            }
            historyText.append("\nTotal SKS: ").append(totalCredits);
            textView.setText(historyText.toString());
        } else {
            textView.setText("No courses selected.");
        }
    }
}
