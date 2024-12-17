package com.example.studentapp2.ui.enrolllment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        // Update the text to reflect the enrollment process
        mText.setValue("Welcome to the Enrollment section! Please choose your courses.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
