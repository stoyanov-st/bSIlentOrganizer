package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 9/4/17.
 */

public class NewEventDialogFragment extends DialogFragment
                                    implements DialogInterface.OnClickListener{

    private AlertDialog dialog;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogStyle));
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.layout_new_event, null))
                .setTitle(R.string.new_event)
                .setPositiveButton(R.string.add_event, this)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        dialog = builder.show();

        TimePicker timePicker = dialog.findViewById(R.id.eventTimePicker);
        timePicker.setIs24HourView(true);

        return dialog;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        EditText dialogEventTitle = dialog.findViewById(R.id.dialog_event_title);
        CheckBox importantCheckBox = dialog.findViewById(R.id.importantCheckBox);
        TimePicker eventTimePicker = dialog.findViewById(R.id.eventTimePicker);

        Intent intent = new Intent();
        String eventTitle = dialogEventTitle.getText().toString();
        boolean isImportant = importantCheckBox.isChecked();
        Long timestamp = eventTimePicker.getDrawingTime();


        intent.putExtra("eventTitle", eventTitle);
        intent.putExtra("eventImportant", isImportant);
        intent.putExtra("eventTimestamp", timestamp);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
