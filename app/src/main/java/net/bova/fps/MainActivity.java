package net.bova.fps;

import android.app.Activity;

import android.os.Build;
import android.os.Bundle;

import android.view.WindowManager;


public class MainActivity extends Activity {
    private View view;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        view= new View(this);
        setContentView(view); //R.layout.activity_main);
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            android.view.View decor = getWindow().getDecorView();
            int type = getWindow().getDecorView().getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) type |= android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) type |= android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) type |= android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decor.setSystemUiVisibility(type);
        }
    }

    @Override protected void onStart() {
        super.onStart();
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onPause() {
        view.pause();
        super.onPause();
    }

    @Override protected void onStop() {
        super.onStop();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

//    @Override public void onBackPressed() {
        // Comment out "super.onBackPressed()" to disable button
        //super.onBackPressed();
//    }

}
