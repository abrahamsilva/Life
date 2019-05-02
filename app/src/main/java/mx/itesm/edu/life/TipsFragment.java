package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.GridViewAdapter;
import mx.itesm.edu.life.models.Contact;
import mx.itesm.edu.life.models.Tip;

public class TipsFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Tip> tips;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tips,container,false);
        getActivity().setTitle(R.string.nav_tips);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("tips");
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        initData();


        return rootView;
    }

    public void initData() {

        tips = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot tipSnapshot : dataSnapshot.getChildren()){
                    Tip tip = tipSnapshot.getValue(Tip.class);
                    tips.add(tip);
                }
                gridViewAdapter = new GridViewAdapter(getActivity(),tips);
                gridView.setAdapter(gridViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
