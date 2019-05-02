package mx.itesm.edu.life;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import mx.itesm.edu.life.models.CalEvent;

public class EventosFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private CalendarView calendarView;
    private List<EventDay> events;
    private List<CalEvent> eventsDesc;

    public static EventosFragment newInstance(){
        EventosFragment fragment = new EventosFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);
        getActivity().setTitle(R.string.title_eventos);
        calendarView = rootView.findViewById(R.id.calendarView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("events");

        events = new ArrayList<>();
        eventsDesc = new ArrayList<>();

        initData();

        return rootView;
    }

    public void initData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                    CalEvent event = eventSnapshot.getValue(CalEvent.class);
                    eventsDesc.add(event);
                }

                fillEvents();
                calendarView.setEvents(events);

                calendarView.setOnDayClickListener(eventDay -> {
                    Calendar clickedDayCalendar = eventDay.getCalendar();
                    LinkedList<Calendar> days = new LinkedList<>();
                    days.add(clickedDayCalendar);
                    calendarView.setSelectedDates(days);
                    if(events.contains(eventDay)){
                        CalEvent event = eventsDesc.get(events.indexOf(eventDay));
                        Bundle args = new Bundle();
                        String date = DateUtils.formatDateTime(getContext(),
                                eventDay.getCalendar().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
                        args.putString("date", date);
                        args.putString("time", event.getTime());
                        args.putString("title", event.getTitle());
                        args.putString("desc", event.getDesc());
                        DialogFragment dialog = new EventDetailDialog();
                        dialog.setArguments(args);
                        dialog.show(getFragmentManager(), "event details");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void fillEvents(){
        for(CalEvent calEvent : eventsDesc){
            Calendar calendar = Calendar.getInstance();
            calendar.set(calEvent.getYear(),calEvent.getMonth()-1,calEvent.getDay());
            events.add(new EventDay(calendar, R.drawable.blue_circle));
            Log.d("EVENT", calEvent.getTitle() +  calEvent.getDay() +"/"+ calEvent.getMonth());
        }
    }
}
