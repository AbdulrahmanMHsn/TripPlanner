package amo.tripplanner.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class SplashFragment extends Fragment {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private Animation textAnimation, layoutAnimation;
    private RelativeLayout relativeLayout;

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

        CircleImageView imageView = view.findViewById(R.id.logo);
        relativeLayout = view.findViewById(R.id.relMain);
        textAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bottom_to_top);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setVisibility(View.VISIBLE);

                        imageView.setAnimation(textAnimation);
                    }
                }, 900);

            }
        }, 500);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    imageView.setAnimation(textAnimation);
                    if (user != null) {
                        FirebaseHelper.getInstance(requireContext()).setmUID(auth.getCurrentUser().getUid());
                        FirebaseHelper.getInstance(requireContext()).setmEmail(auth.getCurrentUser().getEmail());
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