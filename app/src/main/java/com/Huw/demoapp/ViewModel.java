package com.Huw.demoapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private MutableLiveData<String> mText;

    public ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No data");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setText( String a) {

        mText.setValue(a);

    }

}
