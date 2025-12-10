package com.example.alfappcomp710;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

public class AppContext implements Parcelable {
    private AppUIState currentState;
    private String stateName;

    public AppContext(AppUIState currentState, String stateName)
    {
        super();
        this.currentState = currentState;
        this.stateName = stateName;

        if(currentState == null) {
            this.currentState = LoginState.instance();
        }
    }

    protected AppContext(Parcel in) {
        stateName = in.readString();
    }

    public static final Creator<AppContext> CREATOR = new Creator<AppContext>() {
        @Override
        public AppContext createFromParcel(Parcel in) {
            return new AppContext(in);
        }

        @Override
        public AppContext[] newArray(int size) {
            return new AppContext[size];
        }
    };

    public AppUIState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AppUIState currentState) {
        this.currentState = currentState;
    }


    public void update(){
        currentState.updateState(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(stateName);
    }
}
