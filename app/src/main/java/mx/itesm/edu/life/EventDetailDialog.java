package mx.itesm.edu.life;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EventDetailDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        String mDate = mArgs.getString("date");
        String mTitle = mArgs.getString("title");
        String mDesc = mArgs.getString("desc");
        String mTime = mArgs.getString("time");
        String mMessage = getString(R.string.event_details, mDate, mTime, mDesc);
        builder.setMessage(mMessage)
                .setTitle(mTitle)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}