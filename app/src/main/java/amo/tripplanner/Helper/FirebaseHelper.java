package amo.tripplanner.Helper;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import amo.tripplanner.pojo.User;

public class FirebaseHelper {

    Context context;


    public static FirebaseHelper firebaseHelper;

    public static FirebaseAuth mAuth;

    public static FirebaseDatabase mDatabase;

    public static DatabaseReference mDatabaseReference;
    public boolean loggedIn  = false;
    public static boolean signed_up  = false;
    public static boolean userRegistered  = false;



    public FirebaseHelper(Context context) {
        this.context = context;
    }


    //query.orderByChild("date").startAt(new DateTime().getMillis())

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


    public boolean userLogin(String email, String password, final Context context) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loggedIn = true;
                        } else {
                            loggedIn = false;
                        }
                    }
                });
        return loggedIn;
    }


   /* public static boolean signUp(final String email, final String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    if (addUser(new User(email, password)) == true){
                    }
                } else {
                }
            }
        });


    }


    public static boolean addUser(final User user) {
        final boolean[] userAdded = new boolean[1];
        final HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());

        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseDatabase.getInstance().getReference("user").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userAdded[0] = true;
                                    } else {
                                        userAdded[0] = false;
                                    }
                                }
                            });

                }
            }
        });
        return userAdded[0];

    }*/

}
