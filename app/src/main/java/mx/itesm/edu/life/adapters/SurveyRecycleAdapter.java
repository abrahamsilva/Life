package mx.itesm.edu.life.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.itesm.edu.life.R;
import mx.itesm.edu.life.models.Survey;

public class SurveyRecycleAdapter extends RecyclerView.Adapter<SurveyRecycleAdapter.ViewHolder>{

    private Context context;
    private List<Survey> surveys;

    public SurveyRecycleAdapter(Context context, List<Survey> surveys) {
        this.context = context;
        this.surveys = surveys;
    }

    @NonNull
    @Override
    public SurveyRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(
                R.layout.survey_item,viewGroup,
                false);

        SurveyRecycleAdapter.ViewHolder holder = new SurveyRecycleAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Survey survey = surveys.get(holder.getAdapterPosition());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(survey.getLink()));
                context.startActivity(browserIntent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyRecycleAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(surveys.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.survey_name);
        }
    }
}
