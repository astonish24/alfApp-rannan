package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class Find_update_item extends AppCompatActivity {

    Button callSearch;

    private String[] itemTypesList = {"Wallet_Bags","Id_Bank_Cards","Electronics","Others"};
    private AutoCompleteTextView autoCompleteText;
    private RelativeLayout sizeRelativeLayout;
    ArrayAdapter<String> adapterItems;
    String itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_find_update_item);
        callSearch = findViewById(R.id.post);
        autoCompleteText = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);

        //code for dropdown list
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,itemTypesList);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                itemType = item;
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        callSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Find_update_item.this, findItemUpdateList.class);
                intent.putExtra("itemType",itemType);
                startActivity(intent);
            }
        });
    }

}