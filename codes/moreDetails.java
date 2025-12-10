package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class moreDetails extends AppCompatActivity {

    TextView ownerId,itemInfo,ownInfo;
    ImageView icon;
    DatabaseReference reference;
    TextInputLayout finderName, finderAddress, finderPhoneNum;
    String OwnName_DB, ownPhone_DB, OwnAddress_DB;
    String itemBrand_DB, itemName_DB, itemStatus_DB, itemId;
    Button Btn_addFinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_more_details);

        //hooks
        ownerId = findViewById(R.id.label3);
        icon = findViewById(R.id.profileImage);
        finderName = findViewById(R.id.fullname);
        finderAddress = findViewById(R.id.address);
        finderPhoneNum = findViewById(R.id.phoneNum);
        itemInfo = findViewById(R.id.itemInfo);
        ownInfo = findViewById(R.id.ownerInfo);
        Btn_addFinder = findViewById(R.id.postFinder);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        String itemType = intent.getStringExtra("itemType");
        ownerId.setText(intent.getStringExtra("ownerId"));
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        icon.setImageBitmap(bitmap);

        //getting Owner Info from DB
        reference = FirebaseDatabase.getInstance().getReference("Owner");
        Query checkOwner = reference.orderByValue();

        checkOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot);
                Map<String, Object> td = null;
                td = (HashMap<String, Object>) snapshot.getValue();
//                System.out.println(ownerId.getText().toString());
                String ownId = ownerId.getText().toString().trim().substring(15,ownerId.getText().length());
                td = (HashMap<String, Object>) td.get(ownId);
                OwnName_DB = td.get("name").toString();
                OwnAddress_DB = td.get("address").toString();
                ownPhone_DB = td.get("phoneNum").toString();
                //System.out.println(td.get("password"));

                ownInfo.setText(
                        "Owner Name: " + OwnName_DB + "\n"+
                                "Owner Address: " + OwnAddress_DB + "\n"+
                                "Owner Phone Number: " + ownPhone_DB +"\n"
                );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //getting item Info from DB
        System.out.println(itemType.substring(0,itemType.length()-1));
        reference = FirebaseDatabase.getInstance().getReference().child(itemType);
        Query checkItem = reference.orderByKey();

        checkItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot);
                Map<String, Object> td = null;
                td = (HashMap<String, Object>) snapshot.getValue();
                System.out.println(itemId);

                String itemId_ = itemId.substring(4,itemId.length());
                td = (HashMap<String, Object>) td.get(itemId_);
                if(itemType.substring(0,itemType.length()).equals("Wallet_Bags")) {
                    itemBrand_DB = td.get("breed").toString();
                    itemName_DB = td.get("name").toString();
                    itemStatus_DB = td.get("status").toString();
                    System.out.println("1: " + itemBrand_DB +"2: " + itemName_DB);
                }if(itemType.substring(0,itemType.length()).equals("Id_Bank_Cards")) {
                    itemBrand_DB = td.get("breed").toString();
                    itemName_DB = td.get("name").toString();
                    itemStatus_DB = td.get("status").toString();
                    System.out.println("1: " + itemBrand_DB +"2: " + itemName_DB);
                }else if(itemType.substring(0,itemType.length()).equals("Electronics")){
                    System.out.println(itemType.substring(0,itemType.length()-1));
                    itemBrand_DB = td.get("breed").toString();
                    itemStatus_DB = td.get("status").toString();
                }else{

                }

                itemInfo.setText(
                        "Item Name:" + itemName_DB +"\n" +
                                "Item Brand: " + itemBrand_DB +"\n"+
                                "Item Status: " + itemStatus_DB +"\n"
                );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Btn_addFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regFinder(view);
                Toast.makeText(moreDetails.this, "Finder Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean validateName(){
        String val = finderName.getEditText().getText().toString();
        if (val.isEmpty()){
            finderName.setError("Field cannot be empty");
            return false;
        }else{
            finderName.setError(null);
            finderName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo(){
        String val = finderPhoneNum.getEditText().getText().toString();

        if(val.isEmpty()){
            finderPhoneNum.setError("Field cannot be empty");
            return false;
        }else if(val.length() != 10){
            finderPhoneNum.setError("Phone number not 10digits");
            return false;
        }else{
            finderPhoneNum.setError(null);
            finderPhoneNum.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateAddress(){
        String val = finderAddress.getEditText().getText().toString();

        if(val.isEmpty()){
            finderAddress.setError("Field cannot be empty");
            return false;
        }else{
            finderAddress.setError(null);
            finderAddress.setErrorEnabled(false);
            return true;
        }
    }

    public void regFinder(View view){

        if(!validateName()|!validateAddress()|!validatePhoneNo()){
            return;
        }
        //get Finder Data to DB
        String findName2DB = finderName.getEditText().getText().toString();
        String findAddress2DB = finderAddress.getEditText().getText().toString();
        String findPhoneNum2DB = finderPhoneNum.getEditText().getText().toString();

        String ownId = ownerId.getText().toString().trim().substring(15,ownerId.getText().length());
        reference = FirebaseDatabase.getInstance().getReference("Owner").child(ownId);

        Finder finderHelper = new Finder(findName2DB,findPhoneNum2DB,findAddress2DB);
        reference.child("finder"+findPhoneNum2DB).setValue(finderHelper);
    }
}