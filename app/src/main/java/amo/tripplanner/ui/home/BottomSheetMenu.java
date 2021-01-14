package amo.tripplanner.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;


import amo.tripplanner.R;


public class BottomSheetMenu extends BottomSheetDialogFragment {


    FirebaseAuth mAuth;

    CardView cv_show,cv_edit,cv_delete;
    View vv;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=getLayoutInflater().inflate(R.layout.bottom_sheet_menue,null);

        vv=container;
        cv_delete=view.findViewById(R.id.cv_delete);
        cv_edit=view.findViewById(R.id.cv_edit);
        cv_show=view.findViewById(R.id.cv_show);
        mAuth=FirebaseAuth.getInstance();

//        cv_show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(vv).navigate(R.id.action_homeFragment_to_tripDetailsFragment);
//            }
//        });

        cv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Note")
                        .setMessage("Are you sure to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                refrence.delete()
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(getActivity(), "note deleted", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });


        return view;
    }
}
