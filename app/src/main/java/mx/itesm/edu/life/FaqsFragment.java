package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.itesm.edu.life.adapters.ExpandableListAdapter;
import mx.itesm.edu.life.models.Faq;

public class FaqsFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>>listHashMap;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_faqs,container,false);
        getActivity().setTitle(R.string.nav_faqs);

        mFirebaseDatabase = MainActivity.getDatabase();
        myRef = mFirebaseDatabase.getReference("faqs");

        emptyView = rootView.findViewById(R.id.empty);
        expandableListView = (ExpandableListView)rootView.findViewById(R.id.expListView);
        initData();
        return rootView;
    }

    public void initData(){

        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot faqSnapshot : dataSnapshot.getChildren()){
                    Faq faq = faqSnapshot.getValue(Faq.class);
                    listDataHeader.add(faq.getQuestion());
                    listHashMap.put(listDataHeader
                            .get(Integer.parseInt(faq.getId())),new ArrayList<String>());
                    listHashMap.get(listDataHeader
                            .get(Integer.parseInt(faq.getId()))).add(faq.getAnswer());
                }

                if(listDataHeader.size()<=0){
                    expandableListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                }

                expandableListAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listHashMap);
                expandableListView.setAdapter(expandableListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
