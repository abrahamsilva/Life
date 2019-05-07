package mx.itesm.edu.life;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mx.itesm.edu.life.models.CalEvent;
import mx.itesm.edu.life.models.NextEvent;

import static android.content.Context.ALARM_SERVICE;

public class EventosFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference eventsRef, nextEventRef;
    private CalendarView calendarView;
    private List<EventDay> events;
    private List<CalEvent> eventsDesc;
    private List<NextEvent> nextEvents;
    private Map<String, List<CalEvent>> eventsPerDay;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private PendingIntent broadcast;

    ImageView imageView;

    public static EventosFragment newInstance(){
        EventosFragment fragment = new EventosFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);
        getActivity().setTitle(R.string.title_eventos);
        imageView = rootView.findViewById(R.id.image_event);
        alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        calendarView = rootView.findViewById(R.id.calendarView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        eventsRef = mFirebaseDatabase.getReference("events");
        nextEventRef = mFirebaseDatabase.getReference("nextEvents");
        eventsPerDay = new HashMap<>();
        events = new ArrayList<>();
        eventsDesc = new ArrayList<>();
        nextEvents = new ArrayList<>();

        initData();
        getNextEvent();

        return rootView;
    }

    public void getNextEvent(){
        nextEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    NextEvent event = eventSnapshot.getValue(NextEvent.class);
                    nextEvents.add(event);
                }
                Picasso.get().load(nextEvents.get(0).getImage()).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"No se pudo leer la imagen",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initData() {
        Intent intent = new Intent();
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    CalEvent event = eventSnapshot.getValue(CalEvent.class);
                    eventsDesc.add(event);
                }
                fillEventsPerDay();
                calendarView.setEvents(events);
                intent.setAction("mx.itesm.edu.life.action.DISPLAY_NOTIFICATION");
                intent.putExtra("event", eventsDesc.get(0));
                calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, 10);


                broadcast = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

                calendarView.setOnDayClickListener(eventDay -> {
                    if (events.contains(eventDay)) {
                        String date = DateUtils.formatDateTime(getContext(),
                                eventDay.getCalendar().getTimeInMillis(),
                                DateUtils.FORMAT_SHOW_DATE);
                        if (eventsPerDay.get(date).size() > 1) {
                            Bundle args = new Bundle();
                            args.putString("date", date);
                            args.putSerializable("events", (Serializable) eventsPerDay.get(date));
                            DialogFragment dialog = new EventListDialog();
                            dialog.setArguments(args);
                            dialog.show(getFragmentManager(), "event list details");
                        } else {
                            CalEvent event = eventsDesc.get(events.indexOf(eventDay));
                            Bundle args = new Bundle();
                            args.putString("date", date);
                            args.putString("time", event.getTime());
                            args.putString("title", event.getTitle());
                            args.putString("desc", event.getDesc());
                            DialogFragment dialog = new EventDetailDialog();
                            dialog.setArguments(args);
                            dialog.show(getFragmentManager(), "event details");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void fillEventsPerDay(){
        for(CalEvent calEvent : eventsDesc){
            Calendar calendar = calEvent.getDate();
            events.add(new EventDay(calendar, R.drawable.blue_circle));
            String date = DateUtils.formatDateTime(getContext(),
                    calEvent.getDate().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
            List<CalEvent> events;
            if(eventsPerDay.containsKey(date)){
                events = eventsPerDay.get(date);
            } else{
                events = new ArrayList<>();
                eventsPerDay.put(date, events);
            }
            events.add(calEvent);
        }
    }
}
