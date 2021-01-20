package amo.tripplanner.ui.login;


import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
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


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import amo.tripplanner.Helper.Dialogs;
import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentLoginBinding;
import amo.tripplanner.utilities.ButtonAnim;


public class LoginFragment extends Fragment {
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN =1;
    private View view;
    private FragmentLoginBinding binding;
    private Dialog mProgress;

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
        mProgress = Dialogs.createProgressBarDialog(getContext(), "");
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

        binding.buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithGoogle();
            }
        });

        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        return binding.getRoot();
    }


    private void loginWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            firebaseGoogleAuth(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            //firebaseGoogleAuth(null);
        }
    }

    private void firebaseGoogleAuth(GoogleSignInAccount account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(getActivity() , task -> {
            if (task.isSuccessful()){
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void login() {
        String email = binding.editEmailLogin.getText().toString();
        String password = binding.editPasswordLogin.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (!email.isEmpty() && !password.isEmpty()) {
                FirebaseHelper.getInstance(getContext()).userLogin(email, password, getContext(), view,mProgress);
            } else {
                binding.layoutEmail.setBackgroundResource(R.drawable.background_input_empty);
                binding.layoutPassword.setBackgroundResource(R.drawable.background_input_empty);
            }
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