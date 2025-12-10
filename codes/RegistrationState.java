package com.example.alfappcomp710;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationState implements AppUIState{

    //Singleton
    private static RegistrationState instance = new RegistrationState();
    private Intent intent;
    private boolean RegistrationStateAction = false;
    private boolean OwnerProfileStateAction = false;
    private boolean GetLostItemListStateAction = false;

    private RegistrationState(){

    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public static RegistrationState instance() {
        return instance;
    }

    //Business logic and state transition
    public void setActiveActionTrigger(boolean RegistrationStateAction,boolean OwnerProfileStateAction, boolean GetLostItemListStateAction ){
        if (RegistrationStateAction == true && OwnerProfileStateAction == false && GetLostItemListStateAction == false){
            this.RegistrationStateAction = true;
        }else if( RegistrationStateAction == false && OwnerProfileStateAction == true && GetLostItemListStateAction == false){
            this.OwnerProfileStateAction = true;
        }else if(RegistrationStateAction == false && OwnerProfileStateAction == false && GetLostItemListStateAction == true){
            this.GetLostItemListStateAction = true;
        }else{
            this.RegistrationStateAction = false;
            this.OwnerProfileStateAction = false;
            this.GetLostItemListStateAction = false;
        }
    }
    @Override
    public void updateState(AppContext cxt)
    {
        System.out.println("Login Success !!");
        Context context = new AppCompatActivity();
        cxt.setCurrentState(RegistrationState.instance());
        if(RegistrationStateAction){
            //cxt.setCurrentState(RegistrationState.instance());
            //context.startActivity(this.intent);
        }else{

        }

    }


}
