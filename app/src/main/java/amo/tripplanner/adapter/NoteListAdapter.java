package amo.tripplanner.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.ItemNoteBinding;
import amo.tripplanner.pojo.Note;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAG = "NoteListAdapter";
    private List<Note> notes = new ArrayList<>();

    private OnItemClickListener mListener;
    private ItemNoteBinding itemBinding;

    public interface OnItemClickListener {
        void onItemDeleteClick(int position);

        void onItemChecked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NoteListAdapter() {
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_note, parent, false);

        return new NoteViewHolder(itemBinding, mListener);
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


        holder.itemBinding.layoutItemNote.setOnClickListener(view -> {
            if (mListener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemDeleteClick(position);
                }
            }
        });

//        itemBinding.itemNoteImgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
//                notes.remove(position);
//                TripListViewModel listViewModels = ViewModelProviders.of((FragmentActivity) view.getContext()).get(TripListViewModel.class);
//                listViewModels.deleteItemNote(1, notes);
//            }
//        });
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
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

        public NoteViewHolder(@NonNull ItemNoteBinding itemBinding, final OnItemClickListener listener) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.itemNoteImgDelete.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDeleteClick(position);
                    }
                }
            });


            itemBinding.itemNoteChBoxCompleted.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemChecked(position);
                    }
                }
            });
        }

    }
}
