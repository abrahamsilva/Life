package mx.itesm.edu.life.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.itesm.edu.life.R;
import mx.itesm.edu.life.models.CalEvent;

public class EventRecycleAdapter
        extends RecyclerView.Adapter<EventRecycleAdapter.ViewHolder>{

    private Context context;
    private List<CalEvent> calEvents;

    public EventRecycleAdapter(Context context, List<CalEvent> calEvents) {
        this.context = context;
        this.calEvents = calEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(
                R.layout.event_item,viewGroup,
                false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("EVENT", calEvents.get(i).getTitle());
        viewHolder.title.setText(calEvents.get(i).getTitle());
        viewHolder.desc.setText(calEvents.get(i).getDesc());
        String date = DateUtils.formatDateTime(context,
                calEvents.get(i).getDate().getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
        viewHolder.date.setText(date);
        viewHolder.time.setText(calEvents.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return calEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, desc, time, title;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.event_date);
            desc = itemView.findViewById(R.id.event_description);
            time = itemView.findViewById(R.id.event_time);
            title = itemView.findViewById(R.id.event_title);
        }

    }

}
