package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;
import static bg.uni_ruse.stoyanov.bsilentorganizer.note.NoteList.getNotes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment
                            implements AdapterView.OnItemClickListener{

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private ArrayList<Note> noteList;
    private ArrayAdapter<Note> arrayAdapter;
    private NoteDao noteDao;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NotesFragment.
     */
    public static NotesFragment newInstance() {
       return new NotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteDao = MainActivity.getDaoSession().getNoteDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewNoteDialog();
            }
        });

        ListView listView = view.findViewById(R.id.notes_list);
        noteList = new ArrayList<>();
        arrayAdapter = new NoteAdapter(getContext(), noteList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        arrayAdapter.notifyDataSetChanged();

        noteList.addAll(getNotes(getContext()));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    public void showNewNoteDialog() {
        DialogFragment newFragment = new NewNoteDialogFragment();
        newFragment.setTargetFragment(this, 1);
        newFragment.show(getFragmentManager(), "NewNote");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Long noteId = null;
                String noteTitle = data.getStringExtra("noteTitle");
                String noteText = data.getStringExtra("noteText");

                QueryBuilder<Note> queryBuilder = noteDao.queryBuilder();
                List<Note> notes = queryBuilder.where(NoteDao.Properties.NoteTitle.like(noteTitle)).limit(1).list();
                if (!notes.isEmpty()) {
                    noteId = notes.get(0).getId();
                }
                Note newNote = new Note(noteId, getUserId(getContext()),
                        noteTitle,
                        noteText);
                noteDao.insertOrReplace(newNote);

                arrayAdapter.clear();
                arrayAdapter.addAll(getNotes(getContext()));
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Note note = (Note) adapterView.getItemAtPosition(i);
        Bundle bundle = new Bundle();
        bundle.putString("noteTitle", note.getNoteTitle());
        bundle.putString("noteText", note.getNoteText());
        DialogFragment newFragment = new NewNoteDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.setTargetFragment(this, 2);
        newFragment.show(getFragmentManager(), "ViewNote");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
