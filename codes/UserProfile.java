package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName,email,password, address,phoneNum;
    TextView fullNameLabel, usernameLabel, foundPetCount;

    String user_username,user_email,user_phoneNum,user_name,user_address,user_password;//fromDB
    String findName_DB, findAdd_DB, findPhone_DB;
    Button btn_update, btn_postDelAd,btn_viewFinders;


    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("Owner");
        //hooks
        fullName = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        phoneNum = findViewById(R.id.phoneNum);
        fullNameLabel = findViewById(R.id.fullname_label);
        usernameLabel = findViewById(R.id.username_label);
        btn_update = findViewById(R.id.update);
        btn_postDelAd = findViewById(R.id.postDelAd);
        btn_viewFinders = findViewById(R.id.viewFinders);
        foundPetCount = findViewById(R.id.found_pets_count);
        //show All Data
        showAllUserData();
        countFoundPet();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        btn_postDelAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOrDelAd();
            }
        });

        btn_viewFinders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfFinder();
            }
        });

    }

    private void showAllUserData() {
        Intent intent = getIntent();
        user_username = intent.getStringExtra("username");
        user_email = intent.getStringExtra("email");
        user_phoneNum = intent.getStringExtra("phoneNum");
        user_address = intent.getStringExtra("address");
        user_name = intent.getStringExtra("name");
        user_password = intent.getStringExtra("password");



        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        email.getEditText().setText(user_email);
        fullName.getEditText().setText(user_name);
        phoneNum.getEditText().setText(user_phoneNum);
        address.getEditText().setText(user_address);
        password.getEditText().setText(user_password);
    }

    public void update(View view){
        if(isNameChanged()||isPasswordChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data is same or password error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordChanged() {

        if(!user_password.equals(password.getEditText().getText().toString())){

            String pass = password.getEditText().getText().toString();
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
                password.setError("Field cannot be empty");
                return false;
            }else if(!pass.matches(passVal)){
                password.setError("Password is too weak");
                return false;
            }else{
                password.setError(null);
                password.setErrorEnabled(false);
                reference.child(user_phoneNum).child("password").setValue(password.getEditText().getText().toString());
                user_password = password.getEditText().getText().toString();
                return true;
            }
//            reference.child(user_phoneNum).child("password").setValue(password.getEditText().getText().toString());
//            user_password = password.getEditText().getText().toString();
//            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!user_name.equals(fullName.getEditText().getText().toString())){
            //user want to make changes
            String val=fullName.getEditText().getText().toString();
            if(val.isEmpty()){
                fullName.setError("Field cannot be empty");
                return false;
            }
            fullName.setError("null");
            fullName.setErrorEnabled(false);
            reference.child(user_phoneNum).child("name").setValue(fullName.getEditText().getText().toString());
            user_name = fullName.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    public void postOrDelAd(){
        Intent intent = new Intent(getApplicationContext(),PostOrDelAd.class);
        intent.putExtra("name",user_name);
        intent.putExtra("username",user_username);
        intent.putExtra("phoneNum",user_phoneNum);
        startActivity(intent);
    }

    public void listOfFinder(){


        reference = FirebaseDatabase.getInstance().getReference("Owner").child(user_phoneNum);
        Query checkFinder = reference.orderByValue();

        checkFinder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Finder> finderArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String types = postSnapshot.getValue().getClass().getName();
                    if(types.equals("java.util.HashMap")){
                        //System.out.println(postSnapshot);
                        Map<String, Object> td = null;
                        td = (HashMap<String, Object>) postSnapshot.getValue();
                        findName_DB = td.get("name").toString();
                        findAdd_DB = td.get("address").toString();
                        findPhone_DB = td.get("phoneNum").toString();
                        Finder finderObj = new Finder(findName_DB,findPhone_DB,findAdd_DB);
                        finderArrayList.add(finderObj);
                    }
                }

                String []Phones = new String[finderArrayList.size()];
                String []Names = new String[finderArrayList.size()];
                String []Addresses = new String[finderArrayList.size()];
                for(int i=0; i< finderArrayList.size();i++){
                    Phones[i] = finderArrayList.get(i).getPhoneNum();
                    Names[i] = finderArrayList.get(i).getName();
                    Addresses[i] = finderArrayList.get(i).getAddress();
                }
                Intent intent = new Intent(UserProfile.this,FindersInfo.class);
                intent.putExtra("FinderPhones",Phones);
                intent.putExtra("FinderNames",Names);
                intent.putExtra("FinderAddresses",Addresses);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void countFoundPet(){

        reference = FirebaseDatabase.getInstance().getReference("Owner").child(user_phoneNum);
        Query checkFinder = reference.orderByValue();

        checkFinder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Finder> finderArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String types = postSnapshot.getValue().getClass().getName();
                    if(types.equals("java.util.HashMap")){
                        //System.out.println(postSnapshot);
                        Map<String, Object> td = null;
                        td = (HashMap<String, Object>) postSnapshot.getValue();
                        findName_DB = td.get("name").toString();
                        findAdd_DB = td.get("address").toString();
                        findPhone_DB = td.get("phoneNum").toString();
                        Finder finderObj = new Finder(findName_DB,findPhone_DB,findAdd_DB);
                        finderArrayList.add(finderObj);
                    }
                }
                foundPetCount.setText(String.valueOf(finderArrayList.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}