package com.example.alfappcomp710;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GetLostItemListState implements AppUIState{

    //Singleton
    private static GetLostItemListState instance = new GetLostItemListState();
    private Intent intent;
    private boolean RegistrationStateAction = false;
    private boolean OwnerProfileStateAction = false;
    private boolean GetLostItemListStateAction = false;
    private Context context;

    private GetLostItemListState(){

    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
    }
    public void transition2NextState(Bundle bundle){
        if(bundle!=null){
            context.startActivity(this.intent,bundle);
        }else{
            context.startActivity(this.intent,null);
        }
    }

    public static GetLostItemListState instance() {
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
