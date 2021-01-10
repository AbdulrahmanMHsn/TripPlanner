package amo.tripplanner.login;

import android.content.Context;
import android.nfc.tech.NfcA;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {




    View view;
    FragmentLoginBinding binding;
    private FirebaseAuth auth;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        view = container;
        binding.textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(container).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        binding.GoogleLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    private void login(){
        String email = binding.editEmailLogin.getText().toString();
        String password = binding.editPasswordLogin.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (FirebaseHelper.getInstance(getContext()).userLogin(email,password,getContext())) {
                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                FirebaseHelper.loggedIn = false;
            } else {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }

        }

    }
}