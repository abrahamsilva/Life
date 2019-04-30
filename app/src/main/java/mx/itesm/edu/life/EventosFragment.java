package mx.itesm.edu.life;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import mx.itesm.edu.life.models.Contact;
import mx.itesm.edu.life.models.EventDayWithDesc;

public class EventosFragment extends Fragment {

    public static EventosFragment newInstance(){
        EventosFragment fragment = new EventosFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);
        getActivity().setTitle(R.string.title_eventos);
        List<EventDay> events = new ArrayList<>();
        List<EventDayWithDesc> eventsDesc = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,3,30);

        events.add(new EventDay(calendar, R.drawable.event_1_blue_24dp));
        eventsDesc.add(new EventDayWithDesc(calendar,
                R.drawable.event_1_blue_24dp,
                "dia uno", "te vas a divertir"));

        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        calendarView.setEvents(events);
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDayCalendar = eventDay.getCalendar();
            LinkedList<Calendar> days = new LinkedList<>();
            days.add(clickedDayCalendar);
            calendarView.setSelectedDates(days);
            if(events.contains(eventDay)){
                EventDayWithDesc event = eventsDesc.get(events.indexOf(eventDay));
                Bundle args = new Bundle();
                String date = DateUtils.formatDateTime(getContext(),
                        event.getCalendar().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
                args.putString("date", date);
                args.putString("title", event.getTitle());
                args.putString("desc", event.getDescription());
                DialogFragment dialog = new EventDetailDialog();
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "event details");
            }
        });
        return rootView;
    }
}
