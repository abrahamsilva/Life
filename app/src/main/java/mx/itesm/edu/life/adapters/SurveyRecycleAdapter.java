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

        SurveyRecycleAdapter.ViewHolder holder = new SurveyRecycleAdapter.ViewHolder(view,
                new SurveyRecycleAdapter.ViewHolder.ClickListener() {
            @Override
            public void openSurvey(int position) {
                Survey survey = surveys.get(position);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, survey.getLink());
                intent.putExtra(Intent.EXTRA_SUBJECT, "App Consejería y Bienestar");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SurveyRecycleAdapter.ViewHolder.ClickListener listener;
        TextView name;

        public ViewHolder(View itemView, SurveyRecycleAdapter.ViewHolder.ClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.survey_name);

            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mail:
                    listener.openSurvey(this.getAdapterPosition());
                    break;
                default:
                    break;
            }
        }

        public interface ClickListener {
            void openSurvey(int p);
        }
    }

}
