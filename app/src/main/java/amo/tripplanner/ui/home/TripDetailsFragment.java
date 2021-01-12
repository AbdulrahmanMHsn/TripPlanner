//package amo.tripplanner.ui.home;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import amo.tripplanner.R;
//import amo.tripplanner.databinding.FragmentTripDetailsBinding;
//
//
//public class TripDetailsFragment extends Fragment {
//
//    DatabaseReference reference;
//    FragmentTripDetailsBinding tripDetailsBinding;
//
//    public TripDetailsFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        reference = FirebaseDatabase.getInstance().getReference("users");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        tripDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_details, container, false);
//
//        return tripDetailsBinding.getRoot();
//    }
//
////    private void isUser() {
////
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
////        Query checkUser = reference.orderByValue().equalTo()
////        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                 if(snapshot.exists()){
////                     String name = snapshot.child("name").getValue(String.class);
////                     String startPoint = snapshot.child("startPoint").getValue(String.class);
////                     String endPoint = snapshot.child("endPoint").getValue(String.class);
////
////                     tripDetailsBinding.tripDetailsTxtVwName.setText(name);
////                 }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
////    }
//}