package amo.tripplanner.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.adapter.NoteListAdapter;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.viewmodel.TripListViewModel;


public class FloatingWidgetService extends Service {

    int LAYOUT_FLAG;
    View mFloatingView;
    WindowManager windowManager;
    ImageView imageClose;
    TextView tvWidget;
    float width, height;
    private NoteListAdapter adapter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        List<Note> notes = new ArrayList<>();
//        notes.add(new Note("adapter"));
//        notes.add(new Note("adapter"));
//        notes.add(new Note("adapter"));
//        notes.add(new Note("adapter"));
//        List<Note> notes = intent.<Note>getParcelableArrayListExtra("ListNotes");
         notes = (List<Note>) intent.getSerializableExtra("ListNotes");

        adapter = new NoteListAdapter();

//        for(Note note:notes){
//            Log.i("FloatingWidgetService", "onStartCommand: "+note);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }


        // inflate widget layout
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);
        WindowManager.LayoutParams layoutParam = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        // initial Position
        layoutParam.gravity = Gravity.TOP | Gravity.RIGHT;
        layoutParam.x = 0;
        layoutParam.y = 100;

        // layout parameter for close button
        WindowManager.LayoutParams imageParams = new WindowManager.LayoutParams(140,
                140,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        imageParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        imageParams.y = 100;


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        imageClose = new ImageView(this);
        imageClose.setImageResource(R.drawable.ic_launcher_background);
        imageClose.setVisibility(View.INVISIBLE);
        windowManager.addView(imageClose, imageParams);
        windowManager.addView(mFloatingView, layoutParam);
        mFloatingView.setVisibility(View.VISIBLE);

        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();

        View expandedView = mFloatingView.findViewById(R.id.expanded_view);
        expandedView.setVisibility(View.GONE);
        RecyclerView recyclerView = expandedView.findViewById(R.id.recyclerViewOver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setNotes(notes);

//        ImageView imageView = mFloatingView.findViewById(R.id.fabHead);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("Mohamed", "Mohamed");

//
//            }
//        });


        /*mFloatingView.setClickable(true);
        mFloatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Mohamed", "Mohamed");
            }
        });*/
        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            int initialX, initialY;
            float initialTouchX, initialTouchY;
            long startClickTime;
            int maxClickDuration = 200;


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        imageClose.setVisibility(View.VISIBLE);
                        initialX = layoutParam.x;
                        initialY = layoutParam.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;


                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        imageClose.setVisibility(View.GONE);
                        layoutParam.x = initialX + (int) (initialTouchX - event.getRawX());
                        layoutParam.y = initialY + (int) (event.getRawY() - initialTouchY);

                        if (clickDuration < maxClickDuration) {
                            if (expandedView.getVisibility() == View.GONE){

                                expandedView.setVisibility(View.VISIBLE);
                            }
                            else if (expandedView.getVisibility() == View.VISIBLE){
                                expandedView.setVisibility(View.GONE);
                            }
                        } else {
                            if (layoutParam.y > (height * 0.6)) {
                                stopSelf();
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        layoutParam.x = initialX + (int) (initialTouchX - event.getRawX());
                        layoutParam.y = initialY + (int) (event.getRawY() - initialTouchY);

                        windowManager.updateViewLayout(mFloatingView, layoutParam);
                        if (layoutParam.y > (height * 0.6)) {
                            imageClose.setImageResource(R.drawable.ic_close);
                        } else {
                            imageClose.setImageResource(R.drawable.ic_close);
                        }

                        return true;
                }

                return false;
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mFloatingView != null) {
            windowManager.removeView(mFloatingView);
        }

        if (imageClose != null) {
            windowManager.removeView(imageClose);
        }
    }

    /*private void getNote(){
        WindowManager mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams listParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        listParams.gravity = Gravity.CENTER;
        listParams.x = 100;
        listParams.y = 100;
        List<Note> arrayList = new ArrayList<>();
        arrayList.add(new Note("AS"));
        arrayList.add(new Note("AS"));
        arrayList.add(new Note("AS"));

        View view = LayoutInflater.from(FloatingWidgetService.this).inflate(R.layout.fragment_note, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FloatingWidgetService.this));
        NoteListAdapter adapter = new NoteListAdapter();
        adapter.setNotes(arrayList);
        recyclerView.setAdapter(adapter);
        mWindowManager.addView(view, listParams);
        view.setVisibility(View.VISIBLE);
    }*/

    private void getNote(Context context,int id){

    }

}
