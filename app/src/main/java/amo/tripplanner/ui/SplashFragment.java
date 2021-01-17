package amo.tripplanner.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import amo.tripplanner.Helper.FirebaseHelper;
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
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (user != null) {
//                            FirebaseHelper.getInstance(requireContext()).
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
                    }
                }
            }, 3000);
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null)
            auth.removeAuthStateListener(authStateListener);
    }
}