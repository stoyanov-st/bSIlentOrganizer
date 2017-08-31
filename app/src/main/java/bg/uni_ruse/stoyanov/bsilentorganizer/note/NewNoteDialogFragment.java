package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.EditText;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 8/30/17.
 */

public class NewNoteDialogFragment extends DialogFragment
                                    implements DialogInterface.OnClickListener{

    private AlertDialog dialog;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogStyle));
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.layout_new_note, null))
                .setTitle(R.string.new_note)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        switch (getTargetRequestCode()) {
            case 1:
                builder.setPositiveButton(R.string.add_note, this);
                break;
            case 2:
                builder.setPositiveButton(R.string.edit_note, this);
                break;
        }

        dialog = builder.show();

        if (getTargetRequestCode() == 2) {
            Bundle bundle = getArguments();
            EditText dialogTitle = dialog.findViewById(R.id.dialog_title);
            dialogTitle.setText(bundle.getString("noteTitle"));
            dialogTitle.setEnabled(false);
            EditText dialogText = dialog.findViewById(R.id.dialog_text);
            dialogText.setText(bundle.getString("noteText"));
        }
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        EditText dialogTitle = dialog.findViewById(R.id.dialog_title);
        EditText dialogText = dialog.findViewById(R.id.dialog_text);

        Intent intent = new Intent();
        String noteTitle = dialogTitle.getText().toString();
        String noteText = dialogText.getText().toString();

        intent.putExtra("noteTitle", noteTitle);
        intent.putExtra("noteText", noteText);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

    }
}
