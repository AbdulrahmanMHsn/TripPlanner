package amo.tripplanner.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.LayoutAddNoteBinding;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.viewmodel.TripListViewModel;

public class AddNoteFragment extends Fragment {

    //vars
    private static final String TAG = "AddNoteFragment";
    private int id;

    //biding
    private LayoutAddNoteBinding binding;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            AddNoteFragmentArgs  args = AddNoteFragmentArgs.fromBundle(getArguments());
            id = args.getId();
            Log.i(TAG, "onCreate: ididididididi"+id);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_add_note, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // choose start location trip
        binding.addNoteBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyNote = binding.addNoteEdTxtBody.getText().toString();
                List<Note> noteList = new ArrayList<>();
                noteList.add(new Note(bodyNote));
                updateNoteToTrip(id,noteList);
            }
        });
    }


    /*
     * A function use to update trip within room database
     * */
    private void updateNoteToTrip(int id,List<Note> notes) {
        // call observe
        TripListViewModel listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.update(id,notes);
    }
}