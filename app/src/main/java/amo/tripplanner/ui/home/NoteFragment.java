package amo.tripplanner.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.adapter.NoteListAdapter;
import amo.tripplanner.databinding.FragmentNoteBinding;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.viewmodel.TripListViewModel;

public class NoteFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentNoteBinding bindingNote;
    private List<Note> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteListAdapter adapter;
    private int id;



    public NoteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            NoteFragmentArgs  args = NoteFragmentArgs.fromBundle(getArguments());
            id = args.getId();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindingNote = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);

        bindingNote.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NoteListAdapter();

        bindingNote.recyclerView.setAdapter(adapter);

        TripListViewModel listViewModels = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModels.getSubjectById(id).observe(getViewLifecycleOwner(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                list = trip.getTripNotes();
                adapter.setNotes(list);
            }
        });

        bindingNote.noteBtnAdd.setOnClickListener(v -> insertNoteToTrip());

        bindingNote.noteImgClose.setOnClickListener(v-> Navigation.findNavController(container).navigate(R.id.action_noteFragment_to_homeFragment));

        return bindingNote.getRoot();
    }


    /*
     * A function use to update trip within room database
     * */
    private void insertNoteToTrip() {
        if(bindingNote.noteEdTxtVwBody.getText().toString().isEmpty()){
            bindingNote.noteEdTxtVwBody.setError(getString(R.string.text_empty));
            return;
        }
        String body = bindingNote.noteEdTxtVwBody.getText().toString();

        list.add(new Note(body));

        // call observe
        TripListViewModel listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.update(id,list);
        bindingNote.noteEdTxtVwBody.setText("");
    }
}