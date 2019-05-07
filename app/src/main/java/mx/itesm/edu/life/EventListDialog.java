package mx.itesm.edu.life;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.EventRecycleAdapter;
import mx.itesm.edu.life.models.CalEvent;

public class EventListDialog extends DialogFragment {

    public static final String TAG = "event_dialog";
    private Toolbar toolbar;
    private List<CalEvent> calEvents;
    private RecyclerView recyclerView;
    private TextView eventText, eventTime, eventDate;

    public static EventDetailDialog display(FragmentManager fragmentManager) {
        EventDetailDialog eventDialog = new EventDetailDialog();
        eventDialog.show(fragmentManager, TAG);
        return eventDialog;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fullscreen_list_dialog, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recycleView);
        eventText = view.findViewById(R.id.event_description);
        eventDate = view.findViewById(R.id.event_date);
        eventTime = view.findViewById(R.id.event_time);

        calEvents = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mArgs = getArguments();
        String mDate = mArgs.getString("date");
        List<CalEvent> mEvents = (List<CalEvent>) mArgs.getSerializable("events");
        setRecyclerView(mEvents);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(getString(R.string.event_list_title, mDate));
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    private void setRecyclerView(List<CalEvent> calEvents){
        this.calEvents = calEvents;
        EventRecycleAdapter eventRecycleAdapter =
                new EventRecycleAdapter(this.getContext(), calEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(eventRecycleAdapter);
    }
}