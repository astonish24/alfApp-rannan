package com.example.alfappcomp710;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<String> values;
    private final ArrayList<String>  numbers;
    private final ArrayList<Integer> images;
    private final ArrayList<String> imagesPaths;

    public ListAdapter(Context context, ArrayList<String> values, ArrayList<String> numbers, ArrayList<Integer> images,ArrayList<String>imagesPaths){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.numbers = numbers;
        this.images = images;
        this.imagesPaths = imagesPaths;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.btnDetails = (Button) convertView.findViewById(R.id.btnMoreDetails);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText("Id: " +values.get(position));
        viewHolder.txtVersion.setText("Owner Contact: "+numbers.get(position));
        //viewHolder.icon.setImageResource(images.get(position));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imagesPaths.get(position));
        System.out.println(storageReference);
        System.out.println(imagesPaths.get(position));
        Glide.with(this.context).load(storageReference).into(viewHolder.icon);

        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,moreDetails.class);
                intent.putExtra("itemId",viewHolder.txtName.getText().toString());
                intent.putExtra("ownerId", viewHolder.txtVersion.getText().toString());
                viewHolder.icon.buildDrawingCache();
                Bitmap bitmap = viewHolder.icon.getDrawingCache();
                intent.putExtra("BitmapImage", bitmap);
                //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage"); in other view to get image
                int itemCheckVal = images.get(0);
                if(itemCheckVal == 0){
                    intent.putExtra("itemType","Wallet_Bags");
                }else if(itemCheckVal==1){
                    intent.putExtra("itemType","Id_Bank_Cards");
                }else if(itemCheckVal == 2){
                    intent.putExtra("itemType","Electronics");
                }
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        ImageView icon;
        Button btnDetails;

    }

}
