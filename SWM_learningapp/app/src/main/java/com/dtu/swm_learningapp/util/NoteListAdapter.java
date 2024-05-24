package com.dtu.swm_learningapp.util;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dtu.swm_learningapp.R;
import java.util.ArrayList;
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private static final String TAG = "NoteListAdapter";
    private ArrayList<Note> notes;
    public interface onNoteItemClicked{
        void onClick(int position);
    }
    private onNoteItemClicked onNoteItemClicked;

    public void setOnNoteItemClicked(onNoteItemClicked onNoteItemClicked) {
            this.onNoteItemClicked = onNoteItemClicked;
    }


    public NoteListAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout,parent,false);
        return new ViewHolder(view, onNoteItemClicked);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Note note = notes.get(position);

            holder.timeStamp.setText(note.getTimeStamp());
            holder.title.setText(note.getName());
//            holder.deleteNoteImageview.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),note.getImageResourceId()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title;
        TextView timeStamp;
        ImageView deleteNoteImageview;

        onNoteItemClicked onNoteItemClicked;

        public ViewHolder(@NonNull View itemView, onNoteItemClicked onNoteItemClicked) {

            super(itemView);
            this.onNoteItemClicked = onNoteItemClicked;
            title = itemView.findViewById(R.id.note_title);
            timeStamp = itemView.findViewById(R.id.note_timestamp);
            deleteNoteImageview = itemView.findViewById(R.id.delete_note_imageview);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: set on click listener for each view holder");
            onNoteItemClicked.onClick(getAdapterPosition());
        }
    }
}
