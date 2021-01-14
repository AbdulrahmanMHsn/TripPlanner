package amo.tripplanner.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.ItemNoteBinding;
import amo.tripplanner.databinding.TripsItemBinding;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.ui.home.HomeFragmentDirections;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAG = "NoteListAdapter";
    private List<Note> notes = new ArrayList<>();

    private OnItemClickListener mListener;
    private View view;
    private ItemNoteBinding itemBinding;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NoteListAdapter() {
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = parent;
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_note, parent, false);

        return new NoteViewHolder(itemBinding);
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        if (notes.isEmpty()) {
            Log.i(TAG, "onBindViewHolder: my list note is empty");
            return;
        }
        Note note = notes.get(position);
        itemBinding.itemNoteTxtVwBody.setText(note.getBody());

        itemBinding.itemNoteImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+notes.size());
        if (notes.size() != 0)
            return notes.size();
        else
            return 0;
    }

    /**
     * Method to get item by position.
     *
     * @param position
     * @return
     */
    @Nullable
    public Note getItem(int position) {
        return notes.get(position);
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding itemBinding;

        public NoteViewHolder(@NonNull ItemNoteBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}
