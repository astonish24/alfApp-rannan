package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class findItemUpdateList extends AppCompatActivity {

    private String itemType;
    //int[] images = {};
    //String[] version = {};
    //String[] versionNumber = {};
    public ArrayList<Integer> images ;
    public ArrayList<String> imagePaths;
    public ArrayList<String> version;
    public  ArrayList<String> versionNumber;
    ArrayList<ArrayList<Object>> itemData;
    Integer cnt;

    ListView lView;
    ListAdapter lAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        images = new ArrayList<Integer>();
        imagePaths = new ArrayList<String>();
        version = new ArrayList<String>();
        versionNumber = new ArrayList<String>();
        cnt = 0;

        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_find_item_update_list);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Intent intent = getIntent();
        itemType = intent.getStringExtra("itemType");
        System.out.println(itemType);
        getItemListData(itemType);

    }

    public void getItemListData(String itemType){//Fetch Image path and details from DB
        // ArrayList<ArrayList<Object>> itemData= new ArrayList<ArrayList<Object>>();
        Query checkItems;
        switch (itemType){
            case "Wallet_Bags":
                reference = FirebaseDatabase.getInstance().getReference("ItemImages").child("Wallet_Bags");
                checkItems =  reference.orderByValue();

                checkItems.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            ArrayList<Object> data = new ArrayList<>();//[id,ownwerNum,path]
                            // TODO: handle the post
                            String types = postSnapshot.getValue().getClass().getName();
                            if(types.equals("java.util.HashMap")){
                                //System.out.println(postSnapshot.getKey().toString());
                                //td = (HashMap<String, Object>) postSnapshot.getValue();
                                // System.out.println("xxx: " + td.get("path").toString());
                                Map<String, Object> td = null;
                                td = (HashMap<String, Object>) postSnapshot.getValue();

                                version.add(postSnapshot.getKey().toString());
                                versionNumber.add(td.get("imageId").toString());
                                imagePaths.add(td.get("path").toString());
                                cnt = 0;//0 is
                                images.add(cnt);

                            }
                        }
                        lView = (ListView) findViewById(R.id.androidList);
                        lAdapter = new ListAdapter(findItemUpdateList.this, version, versionNumber, images,imagePaths);
                        lView.setAdapter(lAdapter);

                        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(findItemUpdateList.this, version.get(i)+" "+versionNumber.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Getting Post failed, log a message
                        // ...
                    }
                });
                break;//just added 2023

            case "Electronics":
                reference = FirebaseDatabase.getInstance().getReference("ItemImages").child("Electronics");
                checkItems =  reference.orderByKey();
                checkItems.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            ArrayList<Object> data = new ArrayList<Object>();//[id,ownwerNum,path]
                            // TODO: handle the post

                            String types = postSnapshot.getValue().getClass().getName();
                            if(types.equals("java.util.HashMap")){
                                //System.out.println(postSnapshot.getKey().toString());
                                //td = (HashMap<String, Object>) postSnapshot.getValue();
                                // System.out.println("xxx: " + td.get("path").toString());
                                Map<String, Object> td = null;
                                td = (HashMap<String, Object>) postSnapshot.getValue();
                                version.add(postSnapshot.getKey().toString());
                                versionNumber.add(td.get("imageId").toString());
                                imagePaths.add(td.get("path").toString());
                                cnt = 2;//2 is
                                images.add(cnt);
                                //cnt = cnt +1;

                            }
                        }
                        lView = (ListView) findViewById(R.id.androidList);
                        lAdapter = new ListAdapter(findItemUpdateList.this, version, versionNumber, images,imagePaths);
                        lView.setAdapter(lAdapter);

                        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(findItemUpdateList.this, version.get(i)+" "+versionNumber.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Getting Post failed, log a message
                        // ...
                    }
                });
                break;
            case "Id_Bank_Cards":
                reference = FirebaseDatabase.getInstance().getReference("ItemImages").child("Id_Bank_Cards");
                checkItems =  reference.orderByKey();
                checkItems.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            ArrayList<Object> data = new ArrayList<Object>();//[id,ownwerNum,path]
                            // TODO: handle the post
                            String types = postSnapshot.getValue().getClass().getName();
                            if(types.equals("java.util.HashMap")){
                                System.out.println(postSnapshot.getValue().toString());
                                Map<String, Object> td = null;
                                td = (HashMap<String, Object>) postSnapshot.getValue();
                                System.out.println("xxx: " + td.get("path").toString());
                                version.add(postSnapshot.getKey().toString());
                                versionNumber.add(td.get("imageId").toString());
                                imagePaths.add(td.get("path").toString());
                                cnt = 1;//1 is
                                images.add(cnt);

                            }
                        }
                        lView = (ListView) findViewById(R.id.androidList);
                        lAdapter = new ListAdapter(findItemUpdateList.this, version, versionNumber, images,imagePaths);
                        lView.setAdapter(lAdapter);

                        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(findItemUpdateList.this, version.get(i)+" "+versionNumber.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Getting Post failed, log a message
                        // ...
                    }
                });
                break;
            default:
                break;
        }
        //return itemData;
    }
}