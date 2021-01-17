package amo.tripplanner.ui.login;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentLoginBinding;
import amo.tripplanner.utilities.ButtonAnim;


public class LoginFragment extends Fragment {


    private View view;
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        onBackPressed();


        view = container;

        binding.textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


//        binding.GoogleLoginImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();

    }


    private void login() {
        String email = binding.editEmailLogin.getText().toString();
        String password = binding.editPasswordLogin.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            FirebaseHelper.getInstance(getContext()).userLogin(email, password, getContext(), view);
        } else {
            binding.layoutEmail.setBackgroundResource(R.drawable.background_input_empty);
            binding.layoutPassword.setBackgroundResource(R.drawable.background_input_empty);
        }

    }

    private void onBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}