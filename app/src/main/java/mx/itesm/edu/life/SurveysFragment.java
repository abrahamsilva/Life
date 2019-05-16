package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.SurveyRecycleAdapter;
import mx.itesm.edu.life.models.Survey;

public class SurveysFragment extends Fragment {

    private List<Survey> surveys;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    public static SurveysFragment newInstance(){
        SurveysFragment fragment = new SurveysFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_surveys, container, false);
        getActivity().setTitle(R.string.nav_surveys);
        recyclerView = rootView.findViewById(R.id.recycleView);
        emptyView = rootView.findViewById(R.id.empty);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("surveys");

        surveys = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot surveySnapshot : dataSnapshot.getChildren()){
                    Survey survey = surveySnapshot.getValue(Survey.class);
                    surveys.add(survey);
                }
                setRecyclerView(surveys);
                if(surveys.size()<=0){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return rootView;
    }

    private void setRecyclerView(List<Survey> surveys){
        this.surveys = surveys;
        SurveyRecycleAdapter surveyRecycleAdapter =
                new SurveyRecycleAdapter(this.getContext(), surveys);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(surveyRecycleAdapter);
    }
}
