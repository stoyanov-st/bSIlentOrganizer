package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 8/29/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0 , notes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_list_item_layout, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.note_title);
        TextView previewTextView = convertView.findViewById(R.id.note_preview);

        if (note != null) {
            titleTextView.setText(note.getNoteTitle());
            previewTextView.setText(note.getNoteText());
        }

        return convertView;
    }
}
