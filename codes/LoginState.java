package com.example.alfappcomp710;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LoginState implements AppUIState {

    //Singleton
    private static LoginState instance = new LoginState();
    private Intent intent;
    private boolean RegistrationStateAction = false;
    private boolean OwnerProfileStateAction = false;
    private boolean GetLostItemListStateAction = false;
    private Context context;
    private LoginState(){
    }

    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
    }
    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public static LoginState instance() {
        return instance;
    }

    public void transition2NextState(Bundle bundle){
        if(bundle!=null){
            context.startActivity(this.intent,bundle);
        }else{
            context.startActivity(this.intent,null);
        }
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

        if(RegistrationStateAction){
            System.out.println("RegistrationStateAction triggered transitioning to RegistrationState");
            cxt.setCurrentState(RegistrationState.instance());
            //transition2NextState();
        }else if(OwnerProfileStateAction){
            System.out.println("OwnerProfileStateAction triggered transitioning to OwnerProfileState");
            cxt.setCurrentState(OwnerProfileState.instance());
            //transition2NextState();
        }else if(GetLostItemListStateAction){
            System.out.println("GetLostItemListStateAction triggered transitioning to GetLostItemListState");
            cxt.setCurrentState(GetLostItemListState.instance());
            //transition2NextState();
        }

    }


}

