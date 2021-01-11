package amo.tripplanner.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import amo.tripplanner.R;


public class SplashFragment extends Fragment {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        auth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null){
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Navigation.findNavController(container).navigate(R.id.action_splashFragment_to_homeFragment);
                        }
                    }, 3000);
                }

                else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Navigation.findNavController(container).navigate(R.id.action_splashFragment_to_loginFragment);
                        }
                    }, 3000);

                }
            }
        };

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
}