package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;

import java.util.ArrayList;

public class FindersInfo extends AppCompatActivity {


    public String[] findersPhone;
    public String[] findersName;
    public String[] findersAddress;
    private AutoCompleteTextView autoCompleteText;
    private RelativeLayout sizeRelativeLayout;
    TextInputLayout finderName, finderAddress, finderPhoneNum;
    ArrayAdapter<String> adapterItems;
    String findPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_finders_info);
        autoCompleteText = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);


        Intent intent = getIntent();
        findersPhone=intent.getStringArrayExtra("FinderPhones");
        findersName=intent.getStringArrayExtra("FinderNames");
        findersAddress=intent.getStringArrayExtra("FinderAddresses");
        //Hooks
        finderName = findViewById(R.id.fullname);
        finderName.setVisibility(View.INVISIBLE);
        finderAddress = findViewById(R.id.address);
        finderAddress.setVisibility(View.INVISIBLE);
        finderPhoneNum = findViewById(R.id.phoneNum);
        finderPhoneNum.setVisibility(View.INVISIBLE);

        //code for dropdown list
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,findersPhone);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                findPhone = item;
                for(int i=0;i<findersPhone.length;i++){
                    if(findersPhone[i].equals(findPhone)){
                        finderName.setVisibility(View.VISIBLE);
                        finderName.getEditText().setText(findersName[i]);
                        finderAddress.setVisibility(View.VISIBLE);
                        finderAddress.getEditText().setText(findersAddress[i]);
                        finderPhoneNum.setVisibility(View.VISIBLE);
                        finderPhoneNum.getEditText().setText(findersPhone[i]);
                    }
                }
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

    }
}