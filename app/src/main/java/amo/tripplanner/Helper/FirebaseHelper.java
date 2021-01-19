package amo.tripplanner.Helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import amo.tripplanner.R;
import amo.tripplanner.pojo.Trip;

public class FirebaseHelper {

    private Context context;

    private static final String TAG = "FirebaseHelper";

    private static FirebaseHelper firebaseHelper;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mDatabaseReference;

    private String mUID;
    private String mEmail;

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmUID() {
        return mUID;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

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


//    public static void initFireBase() {
//
//        //initializing firebase auth object
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mDatabase.getReference();
//    }


    public void userLogin(String email, String password, final Context context, final View view) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUID = firebaseAuth.getCurrentUser().getUid();
                            mEmail = firebaseAuth.getCurrentUser().getEmail();
                            if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(context, "Not Verified Account", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                            }
                            else{
                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                            }
                        } else {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT);
                        }
                    }
                });

    }


    public void signUp(final String email, final String password, final Context context, final View view, final int id) {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // send email verification
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "verify your account and login", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                           /* final HashMap<String, String> map = new HashMap<>();
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
                                    });*/
                        }
                    }
                });
    }

    public void logOut(){
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

    }


    public void syncWithBackend(List<Trip> trips,List<Trip> HistoryTrips) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user").child(getmUID()).child("trips");

        rootRef.setValue(trips).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (!task.isSuccessful()) {
                    Log.i(TAG, "onComplete: failed");
                }

            }
        });


        DatabaseReference rootRefHistory = FirebaseDatabase.getInstance().getReference("user").child(getmUID()).child("history");

        rootRefHistory.setValue(HistoryTrips).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (!task.isSuccessful()) {
                    Log.i(TAG, "onComplete: failed");
                }

            }
        });

    }


    public String getCurrentUserId() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }


    public String getCurrentUserEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }



}

