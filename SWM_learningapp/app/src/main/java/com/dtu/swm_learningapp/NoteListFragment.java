package com.dtu.swm_learningapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dtu.swm_learningapp.util.Note;
import com.dtu.swm_learningapp.util.NoteListAdapter;
import java.util.ArrayList;
public class NoteListFragment extends Fragment implements NoteListAdapter.onNoteItemClicked {
    public NoteListFragment() {
        // Required empty public constructor
    }
    private static final String TAG = "NoteListFragment";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    private RecyclerView recyclerView;
    private NoteListAdapter noteListAdapter;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        insertFakeNotes(notes);
        initRecyclerView();

    }
    private void initRecyclerView()
    {
        recyclerView = requireActivity().findViewById(R.id.notes_recyclerview);
        noteListAdapter = new NoteListAdapter(notes);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(manager);

        noteListAdapter.setOnNoteItemClicked(this);
    }
    private void insertFakeNotes(ArrayList<Note>arrayList)
    {
        for (int i =0; i < 1000; i++)
        {
            arrayList.add(new Note("Note : "+i,"Desc : "+i,"2024",R.id.delete_note_imageview));
        }

//            noteListAdapter.notifyDataSetChanged();
//            noteListAdapter.notifyItemChanged(notes.size());

    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick: note clicked");
        Bundle intent = new Bundle();
        intent.putParcelable(SELECTED_NOTE,notes.get(position));
        Navigation.findNavController(requireView()).navigate(R.id.action_noteListFragment_to_noteDetailsFragment,intent);

    }

//    @Override
//    public void onClick(int position) {
//        Log.d(TAG, "onClick: note clicked");
//        Bundle intent = new Bundle();
//        intent.putParcelable("Selected note",notes.get(position));
//        Navigation.findNavController(requireView()).navigate(R.id.action_noteListFragment_to_noteDetailsFragment,intent);
//
//    }
}