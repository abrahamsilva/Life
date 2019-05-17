package mx.itesm.edu.life;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.List;
import java.util.Map;

import mx.itesm.edu.life.models.CalEvent;
import mx.itesm.edu.life.models.NextEvent;

public class EventosFragment extends Fragment {

    private DatabaseReference eventsRef, nextEventRef;
    private CalendarView calendarView;
    private List<EventDay> events;
    private Map<String, List<CalEvent>> eventsPerDay;
    private String eventPic;
    private ValueEventListener nextEventListener;
    private ValueEventListener eventListener;
    private ImageView imageView;
    private Animation fadeOut;
    private Animation fadeIn;

    public static EventosFragment newInstance(){
        return new EventosFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);
        getActivity().setTitle(R.string.title_eventos);
        imageView = rootView.findViewById(R.id.image_event);
        calendarView = rootView.findViewById(R.id.calendarView);
        FirebaseDatabase mFirebaseDatabase = MainActivity.getDatabase();
        eventsRef = mFirebaseDatabase.getReference("events");
        nextEventRef = mFirebaseDatabase.getReference("nextEvents");
        eventsPerDay = new HashMap<>();
        events = new ArrayList<>();
        fadeOut = AnimationUtils.
                loadAnimation(getContext(), R.anim.fade_out);
        fadeIn = AnimationUtils.
                loadAnimation(getContext(), R.anim.fade_in);
        initData();
        getNextEvent();

        return rootView;
    }

    public void getNextEvent(){
         nextEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    NextEvent event = eventSnapshot.getValue(NextEvent.class);
                    eventPic = event.getImage();
                }
                imageView.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {// a thread in Android
                    @Override
                    public void run() {
                        imageView.startAnimation(fadeIn);
                        imageView.setBackgroundColor(getResources().getColor(R.color.white));
                        Picasso.get().load(eventPic).into(imageView);
                    }
                },800);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"No se pudo leer la imagen",
                        Toast.LENGTH_SHORT).show();
            }
        };
        nextEventRef.addValueEventListener(nextEventListener);
    }


    public void initData() {
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                    CalEvent event = eventSnapshot.getValue(CalEvent.class);
                    fillEventsPerDay(event);
                }
                calendarView.setEvents(events);
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
                            CalEvent event = eventsPerDay.get(date).get(0);
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
        };
        eventsRef.addValueEventListener(eventListener);
    }

    public void fillEventsPerDay(CalEvent calEvent){
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventsRef.removeEventListener(eventListener);
        nextEventRef.removeEventListener(nextEventListener);
    }
}
