package amo.tripplanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import amo.tripplanner.R;
import amo.tripplanner.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//
//    private static final int POS_DASHBOARD = 0;
//    private static final int POS_ACCOUNT = 1;
//    private static final int POS_MESSAGES = 2;
//    private static final int POS_CART = 3;
//    private static final int POS_LOGOUT = 5;
//
//    private String[] screenTitles;
//    private Drawable[] screenIcons;
//
//    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//        {
//            return true;
//        }
//
//        return false;
//        // Disable back button..............
//    }

}