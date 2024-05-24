package com.dtu.swm_learningapp;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.dtu.swm_learningapp.util.Note;
public class NoteDetailsFragment extends Fragment {


    Bundle bundle;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if  (getArguments() != null)
        {
            bundle = getArguments();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        Note note =  bundle.getParcelable(NoteListFragment.SELECTED_NOTE);
        EditText editText = (EditText) view.findViewById(R.id.note_details_textarea);
        assert note != null;

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.note_details_appbar);
        toolbar.setTitle(note.getName());
        editText.setText(note.getDescription());


       return view;
    }
}