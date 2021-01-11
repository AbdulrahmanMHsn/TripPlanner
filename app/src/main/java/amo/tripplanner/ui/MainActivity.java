package amo.tripplanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import amo.tripplanner.R;
import amo.tripplanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

}