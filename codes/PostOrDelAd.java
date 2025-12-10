package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class PostOrDelAd extends AppCompatActivity {

    private TextView fullNameLabel, usernameLabel,phoneNumeLabel;
    private TextInputLayout itemName, itemColor,itemBrand;
    private RadioGroup rgBodySize, rgStatus;
    private ShapeableImageView itemImage;
    private  static final int PICK_IMAGE =1;
    private Button uploadImage,postItem;
    private RelativeLayout sizeRelativeLayout;
    Uri imageUri;
    String prevUsername, prevName,prevPhone, bodySize, itemStatus,itemType,itemClassification;

    String[] itemTypesList = {"Wallet_Bags","Id_Bank_Cards","Electronics","Others"};//"Wallet_Bags","Id_Bank_Cards","Electronics","Others"
    AutoCompleteTextView autoCompleteText;

    ArrayAdapter<String> adapterItems;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Bitmap bitmapImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post_or_del_ad);

        //hooks
        itemType = "";
        fullNameLabel = findViewById(R.id.fullname_label);
        usernameLabel = findViewById(R.id.username_label);
        phoneNumeLabel = findViewById(R.id.phoneNmm_label);
        itemName = findViewById(R.id.itemName);
        itemColor = findViewById(R.id.itemColor);
        itemBrand = findViewById(R.id.itemBrand);
        rgBodySize = findViewById(R.id.itemBodySize);
        rgStatus = findViewById(R.id.itemStatus);
        itemImage = (ShapeableImageView) findViewById(R.id.itemImage);
        uploadImage = findViewById(R.id.btnUploaditemImage);
        autoCompleteText = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);
        sizeRelativeLayout = findViewById(R.id.radioRelativeLayout_size);
        postItem = findViewById(R.id.post);


        //show Owner Data
        showOwnerData();

        //code for dropdown list
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,itemTypesList);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                itemType = item;
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
                if(itemType.equals("Electronics")){
                    itemName.setVisibility(View.GONE);
                    sizeRelativeLayout.setVisibility(View.GONE);
                }else{
                    itemName.setVisibility(View.VISIBLE);
                    sizeRelativeLayout.setVisibility(View.VISIBLE);
                }
                if(itemType.equals("Electronics")){
                    itemClassification = "Electronics";
                }else if (itemType.equals("Wallet_Bags")||itemType.equals("Id_Bank_Cards")){
                    itemClassification = "Non_Electronics";
                }else{
                    itemClassification = "Other";
                }
            }
        });


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),PICK_IMAGE);
            }
        });

        rgBodySize.check(R.id.radioBtnSmall);
        bodySize = "Small";
        rgBodySize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.radioBtnLarge:
                        bodySize = "Large";
                        Toast.makeText(PostOrDelAd.this, "Item size is large", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBtnSmall:
                        bodySize = "Small";
                        Toast.makeText(PostOrDelAd.this, "Item size is small", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBtnMedium:
                        bodySize = "Medium";
                        Toast.makeText(PostOrDelAd.this, "Item size is medium", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        rgStatus.check(R.id.radioBtnLost);
        itemStatus = "Lost";
        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.radioBtnFound:
                        itemStatus = "Found";
                        Toast.makeText(PostOrDelAd.this, "Item status set to found", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBtnLost:
                        itemStatus = "Lost";
                        Toast.makeText(PostOrDelAd.this, "Item status set to Lost", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemType.isEmpty()){
                    Toast.makeText(PostOrDelAd.this, "Item Type Not Selected", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    postLostItem(view);
                    Toast.makeText(PostOrDelAd.this, "Item Successfully Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showOwnerData(){
        Intent intent = getIntent();
        prevName = intent.getStringExtra("name");
        prevUsername = intent.getStringExtra("username");
        prevPhone = intent.getStringExtra("phoneNum");
        fullNameLabel.setText(prevName);
        usernameLabel.setText(prevUsername);
        phoneNumeLabel.setText(prevPhone);
    }

    public boolean validateitemName(){
        String val = itemName.getEditText().getText().toString();
        if (val.isEmpty()){
            itemName.setError("Field cannot be empty");
            return false;
        }else{
            itemName.setError(null);
            itemName.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateitemColor(){
        String val = itemColor.getEditText().getText().toString();
        if (val.isEmpty()){
            itemColor.setError("Field cannot be empty");
            return false;
        }else{
            itemColor.setError(null);
            itemColor.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateitemBrand(){
        String val = itemBrand.getEditText().getText().toString();
        if (val.isEmpty()){
            itemBrand.setError("Field cannot be empty");
            return false;
        }else{
            itemBrand.setError(null);
            itemBrand.setErrorEnabled(false);
            return true;
        }
    }

    public void postLostItem(View view){

        //get values to post

        String toDB_itemName = itemName.getEditText().getText().toString();
        String toDB_itemColor = itemColor.getEditText().getText().toString();
        String toDB_itemBrand = itemBrand.getEditText().getText().toString();
        String toDB_itemBodySize = bodySize;
        String toDB_itemStatus = itemStatus;


        if(itemType.equals("Electronics")){
            if(!validateitemBrand()|!validateitemColor()){
                return;
            }
            rootNode =  FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Electronics");
            String id = prevPhone+toDB_itemColor.substring(0,3);
            Electronics ElectronicsPost = new Electronics(itemClassification,toDB_itemColor,toDB_itemBrand,toDB_itemStatus);
            reference.child(id).setValue(ElectronicsPost);
            uploadImage(bitmapImages,id,itemType);
        }else if(itemType.equals("Wallet_Bags")) {
            if(!validateitemBrand()|!validateitemColor()|!validateitemName()){
                return;
            }
            rootNode =  FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Wallet_Bags");
            String id = prevPhone+toDB_itemName.substring(0,4);
            Wallet_Bags Wallet_BagsPost = new Wallet_Bags(itemClassification,toDB_itemColor,toDB_itemName,toDB_itemBodySize,toDB_itemBrand,toDB_itemStatus);
            reference.child(id).setValue(Wallet_BagsPost);
            uploadImage(bitmapImages,id,itemType);
//            System.out.println(Wallet_BagsPost.getBreed());
//            System.out.println(Wallet_BagsPost.getStatus());
//            System.out.println(Wallet_BagsPost.getBodySize());
        }else if(itemType.equals("Id_Bank_Cards")){
            rootNode =  FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Id_Bank_Cards");
            if(!validateitemBrand()|!validateitemColor()|!validateitemName()){
                return;
            }

            //director.makeCatPost(itemClassification,toDB_itemColor,toDB_itemName,toDB_itemBodySize,toDB_itemBrand,toDB_itemStatus);
            String id = prevPhone+toDB_itemName.substring(0,4);
//            Map<String, Id_Bank_Cards> Id_Bank_CardsAd = new HashMap<>();
//            Id_Bank_CardsAd.put(id,Id_Bank_CardsPost.getId_Bank_CardsObj());
            Id_Bank_Cards Id_Bank_CardsPostTry = new Id_Bank_Cards(itemClassification,toDB_itemColor,toDB_itemName,toDB_itemBodySize,toDB_itemBrand,toDB_itemStatus);
            reference.child(id).setValue(Id_Bank_CardsPostTry);
            uploadImage(bitmapImages,id,itemType);
        }else if(itemType.equals("Others")){
            if(!validateitemBrand()|!validateitemColor()|!validateitemName()){
                return;
            }
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                itemImage.setImageBitmap(bitmap);
                bitmapImages = bitmap;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void uploadImage(Bitmap bitmap,String myImageName,String itemType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        com.google.firebase.storage.StorageReference storageRef = storage.getReferenceFromUrl("gs://alfappcomp710.appspot.com");
        String imageUrl = "";
        if(itemType.equals("Wallet_Bags")){
            imageUrl = "MyImages/Wallet_Bags/"+myImageName;
            writeImageData(myImageName,imageUrl,itemType);
        }else if(itemType.equals("Electronics")){
            imageUrl = "MyImages/Electronics/"+myImageName;
            writeImageData(myImageName,imageUrl,itemType);
        }else if(itemType.equals("Id_Bank_Cards")){
            imageUrl = "MyImages/Id_Bank_Cards/"+myImageName;
            writeImageData(myImageName,imageUrl,itemType);
        }else if(itemType.equals("Others")){

        }

        com.google.firebase.storage.StorageReference imagesRef = storageRef.child(imageUrl);

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //Uri downloadUrl = (Uri)taskSnapshot.getMetadata();
                // Do what you want
                System.out.println( taskSnapshot.getMetadata().getPath());
                System.out.println( taskSnapshot.getMetadata().getName());

            }
        });
    }

    public void writeImageData(String id, String path,String itemType){
        reference = FirebaseDatabase.getInstance().getReference();
        String key = id.substring(0,10);
        Image2DB image = new Image2DB(key,path);

        if(itemType.equals("Wallet_Bags")){
            reference.child("ItemImages").child("Wallet_Bags").child(id).setValue(image);
        }else if(itemType.equals("Electronics")){
            reference.child("ItemImages").child("Electronics").child(id).setValue(image);
        }else if(itemType.equals("Id_Bank_Cards")){
            reference.child("ItemImages").child("Id_Bank_Cards").child(id).setValue(image);
        }else if(itemType.equals("Others")){
            reference.child("ItemImages").child("Others").child(id).setValue(image);
        }

    }

}