package amo.tripplanner.ui.home;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.adapter.NoteListAdapter;
import amo.tripplanner.databinding.FragmentNoteBinding;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.viewmodel.TripListViewModel;

public class NoteFragment extends Fragment {

    private FragmentNoteBinding bindingNote;
    private List<Note> list = new ArrayList<>();
    private NoteListAdapter adapter;
    private int id;
    private TripListViewModel listViewModels;


    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         listViewModels = ViewModelProviders.of(this).get(TripListViewModel.class);
        if (getArguments() != null) {
            NoteFragmentArgs  args = NoteFragmentArgs.fromBundle(getArguments());
            id = args.getId();
        }

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindingNote = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);

        bindingNote.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NoteListAdapter();

        bindingNote.recyclerView.setAdapter(adapter);


        listViewModels.getNoteById(id).observe(getViewLifecycleOwner(), trip -> {
            list = trip.getTripNotes();
            adapter.setNotes(list);
        });



        adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemDeleteClick(int position) {

//                list.remove(adapter.getItem(position));
////                adapter.setNotes(list);
//                Note note = adapter.getItem(position);
//                TripListAdapter adapter = new TripListAdapter();
//                final Trip trip = adapter.getItem(position);
//                Note note
//                listViewModels.delete(trip);
////                listViewModels.deleteItemNote(id,list);
//
////                for(Note note:list){
////                    Log.i("TAGNote", "onCreateView: "+note.getBody());
////                }
            }

            @Override
            public void onItemChecked(int position) {
//                list.get(position).setChecked(true);
//                listViewModels.update(id,list);
            }
        });

        bindingNote.noteBtnAdd.setOnClickListener(v -> insertNoteToTrip());

        bindingNote.noteImgClose.setOnClickListener(v-> Navigation.findNavController(container).popBackStack());

        return bindingNote.getRoot();
    }


    /*
     * A function use to update trip within room database
     * */
    private void insertNoteToTrip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if(bindingNote.noteEdTxtVwBody.getText().toString().isEmpty()){
                bindingNote.noteEdTxtVwBody.setError(getString(R.string.text_empty));
                return;
            }
        }
        String body = bindingNote.noteEdTxtVwBody.getText().toString();

        list.add(new Note(body));

        // call observe
        listViewModels.update(id,list);
        bindingNote.noteEdTxtVwBody.setText("");
    }
}