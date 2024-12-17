package com.example.studentapp2.model;

public class Course {
    private String name;
    private int credits;
    private boolean isSelected;

    public Course(String name, int credits) {
        this.name = name;
        this.credits = credits;
        this.isSelected = false; // Default to not selected
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
