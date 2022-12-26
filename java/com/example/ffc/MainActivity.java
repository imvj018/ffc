package com.example.ffc;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity<Mainactivity> extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2800;

    Animation tanim, banim;
    ImageView logo;
    ImageView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tanim = AnimationUtils.loadAnimation(this, R.anim.topanim);
        banim = AnimationUtils.loadAnimation(this, R.anim.botanim);

        logo = findViewById(R.id.imageView1);
        Name = findViewById(R.id.imageView2);

        logo.setAnimation(tanim);
        Name.setAnimation(banim);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                Pair[] pairs = new  Pair[2];
//                pairs[0] = new  Pair<View, String> (logo, "logoI");
//                pairs[1] = new  Pair<View, String> (Name, "logoN");
//                ActivityOptions options = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
//                }
//                startActivity(intent, options.toBundle());
//                finish();
//            }
//        }, SPLASH_SCREEN);
    }
}
