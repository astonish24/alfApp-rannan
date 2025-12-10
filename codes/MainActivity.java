package com.example.alfappcomp710;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView slogan;
    AppContext ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Initiating the App Context
        ctx = new AppContext(null,"App Starting");// MainState context
        ctx.update();


        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.button_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        slogan = findViewById(R.id.textView);

        image.setAnimation( topAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(MainActivity.this,Login.class);
                intent.putExtra("AppContext", (Parcelable) ctx);//passing the context to next State
//                startActivity(intent);
//                finish();
                Pair[]  pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(slogan,"logo_text");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                }
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);
    }
}