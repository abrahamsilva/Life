package mx.itesm.edu.life;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TipDetailsDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View titleView = getActivity().getLayoutInflater().inflate(R.layout.dialog_title, null);
        TextView tv = titleView.findViewById(R.id.titleBar);

        AlertDialog.Builder builder =
                new AlertDialog.Builder( getActivity(), R.style.DialogTheme );
        Bundle mArgs = getArguments();
        String mTitle = mArgs.getString("title");
        tv.setText(mTitle);
        String mDesc = mArgs.getString("desc");
        builder.setMessage(mDesc)
                .setCustomTitle(titleView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        if (getDialog() == null)
        {
            return;
        }

        getDialog().getWindow().setWindowAnimations(
                R.style.MyAnimation_Window);

        super.onStart();
    }
}