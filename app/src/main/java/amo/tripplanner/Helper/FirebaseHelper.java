package amo.tripplanner.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import amo.tripplanner.R;

public class FirebaseHelper {

    private Context context;


    public static FirebaseHelper firebaseHelper;

    public static FirebaseAuth mAuth;

    public static FirebaseDatabase mDatabase;

    public static DatabaseReference mDatabaseReference;



    public FirebaseHelper(Context context) {
        this.context = context;
    }


    public static FirebaseHelper getInstance(Context context) {

        if (firebaseHelper == null) {
            return firebaseHelper = new FirebaseHelper(context);
        } else {
            return firebaseHelper;
        }


    }

    public static void initFireBase() {

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

    }


    public void userLogin(String email, String password, final Context context, final View view) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                        } else {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT);
                        }
                    }
                });

    }


    public static void signUp(final String email, final String password, final Context context, final View view, final int id) {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final HashMap<String, String> map = new HashMap<>();
                            map.put("email", email);
                            map.put("password", password);
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(map)
                                    .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Navigation.findNavController(view).navigate(id);
                                            } else {
                                                Toast.makeText(context, "failed", Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });
                        } else {

                        }
                    }
                });
    }


}

