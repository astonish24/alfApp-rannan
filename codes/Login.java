package com.example.alfappcomp710;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity implements Serializable {

    Button callSignup, login_btn,callSearch;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username,password;
    boolean RegistrationStateAction = false;//Login Action Trigger
    boolean OwnerProfileStateAction = false; //Login Action Trigger
    boolean GetLostItemListStateAction = false; //Login Action Trigger

    LoginState loginState;
    AppContext ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        //hooks
        callSignup = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.Login_btn);
        callSearch = findViewById(R.id.searchItem);

        //get AppContext
        loginState = LoginState.instance();//singleton instance
        ctx = getIntent().getParcelableExtra("AppContext");
        //ctx is a refence to concrete state object LoginState which would be used to set the current state
        ctx.setCurrentState(loginState);

        callSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, Find_update_item.class);
                //startActivity(intent);
                GetLostItemListStateAction = true;
                RegistrationStateAction = false;
                OwnerProfileStateAction = false;

                stateTransitionRunner(loginState,ctx,intent,view,null);
            }
        });
        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                //startActivity(intent);
                GetLostItemListStateAction = false;
                RegistrationStateAction = true;
                OwnerProfileStateAction = false;
                Pair[]  pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image,"logo_trans");
                pairs[1] = new Pair<View, String>(logoText,"logo_text");
                pairs[2] = new Pair<View, String>(sloganText,"slogan_text");
                pairs[3] = new Pair<View, String>(username,"username_tran");
                pairs[4] = new Pair<View, String>(password,"password_tran");
                pairs[5] = new Pair<View, String>(login_btn,"btnGo_transition");
                pairs[6] = new Pair<View, String>(callSignup,"btnSignUp_transition");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                }
                stateTransitionRunner(loginState,ctx,intent,view,options.toBundle());
                //startActivity(intent,options.toBundle());
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });
    }

    private Boolean validateUserName(){
        String val = username.getEditText().getText().toString();
        if(val.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String pass = password.getEditText().getText().toString();

        if(pass.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        if(!validatePassword()|!validateUserName()){
            return;
        }else{
            isUser(view);
        }
    }

    private void isUser(View view) {
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Owner");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);
                    //String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    String passwordFromDB ="";
                    Map<String, Object> td = null;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        td = (HashMap<String, Object>) child.getValue();
                        passwordFromDB =td.get("password").toString();
                        if(passwordFromDB.equals(userEnteredPassword)){break;}
                    }
                    if(passwordFromDB.equals(userEnteredPassword)){

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDB = td.get("name").toString();
                        String emailFromDB = td.get("email").toString();
                        String phoneFromDB = td.get("phoneNum").toString();
                        String userFromDB = td.get("username").toString();
                        String addressFromDB = td.get("address").toString();

                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);

                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phoneNum",phoneFromDB);
                        intent.putExtra("username",userFromDB);
                        intent.putExtra("password",passwordFromDB);
                        intent.putExtra("address",addressFromDB);
                        GetLostItemListStateAction = false;
                        RegistrationStateAction = false;
                        OwnerProfileStateAction = true;
                        stateTransitionRunner(loginState,ctx,intent,view,null);
                        //startActivity(intent);
                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else{
                    username.setError("No Such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void stateTransitionRunner(LoginState stateObject, AppContext context, Intent intent, View view,Bundle bundle){
        stateObject.setIntent(intent);
        stateObject.setContext(view.getContext());
        stateObject.setActiveActionTrigger(RegistrationStateAction,OwnerProfileStateAction,GetLostItemListStateAction);
        intent.putExtra("AppContext", (Parcelable) context);//passing the context to next State
        context.update();
        stateObject.transition2NextState(bundle);
    }
}