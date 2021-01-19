package amo.tripplanner.ui.login;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentSignUpBinding;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private FirebaseAuth auth;
    private View view;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        view = container;
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        return binding.getRoot();
    }

    private void register(){
        String email = binding.editEmailSignup.getText().toString();
        String password = binding.editPasswordSignUp.getText().toString();
        String confirmPassword = binding.editConfirmPasswordSignUp.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() &&(password.equals(confirmPassword)) &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                FirebaseHelper.getInstance(getContext()).signUp(email, password, getContext(), view, R.id.action_signUpFragment_to_homeFragment);
            }
            else
            {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*private void registerUser(){
        String email = binding.editEmailSignup.getText().toString();
        String password = binding.editPasswordSignUp.getText().toString();


        if(!email.isEmpty() && !password.isEmpty()){
            final HashMap<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseDatabase.getInstance().getReference("User").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).
                                getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    FirebaseDatabase.getInstance().getReference("user").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "gggg", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(getActivity(), task.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }*/
}