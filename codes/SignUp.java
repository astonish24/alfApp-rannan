package com.example.alfappcomp710;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    Button Backlogin_btn, regBtn;
    TextInputLayout regName,regUsername,regEmail, regPhone, regPassword,regAddress;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        Backlogin_btn = findViewById(R.id.back2Login_btn);
        regBtn = findViewById(R.id.Login_btn);
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regAddress = findViewById(R.id.address);



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode =  FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Owner");

                registerUser(view);
                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUp.this, "Click Already Have Account to Proceed", Toast.LENGTH_SHORT).show();
            }
        });

        Backlogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUserName(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace ="\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if(val.length() >= 20){
            regUsername.setError("Username too long");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            regUsername.setError("Whitespaces not allowed");
            return false;
        }else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo(){
        String val = regPhone.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }else if(val.length() != 10){
            regPhone.setError("Phone number not 10digits");
            return false;
        }else{
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateAddress(){
        String val = regAddress.getEditText().getText().toString();

        if(val.isEmpty()){
            regAddress.setError("Field cannot be empty");
            return false;
        }else{
            regAddress.setError(null);
            regAddress.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String pass = regPassword.getEditText().getText().toString();
        String passVal = "^" +
                //"(?=.*[0-9])"+
                //"(?=.*[a-z])"+
                //"(?=.*[A-Z])"+
                "(?=.*[a-zA-Z])"+ //any Letter
                "(?=.*[@#$%&^+-+])"+ //at least one Special character
                "(?=\\S+$)"+ //no whitespace
                ".{4,}"+
                "$";
        if(pass.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if(!pass.matches(passVal)){
            regPassword.setError("Password is too weak");
            return false;
        }else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    // Save Data in Firebase after on Button Click

    public void registerUser(View view){
        //get all values in Strings

        if(!validateName()|!validateUserName()|!validateEmail()|!validatePhoneNo()|!validateAddress()|!validatePassword()){
            return;
        }
        String name = regName.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String address = regAddress.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();

        Owner helperOwner = new Owner(name,email,phone,address,username,password);
        reference.child(phone).setValue(helperOwner);

    }
}