package mx.itesm.edu.life;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.itesm.edu.life.adapters.ExpandableListAdapter;
import mx.itesm.edu.life.models.Contact;
import mx.itesm.edu.life.models.Faq;

public class FaqsFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>>listHashMap;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_faqs,container,false);
        getActivity().setTitle(R.string.nav_faqs);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("faqs");

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
                    System.out.println(faq.getQuestion());
                    listDataHeader.add(faq.getQuestion());
                    listHashMap.put(listDataHeader.get(Integer.parseInt(faq.getId())),new ArrayList<String>());
                    listHashMap.get(listDataHeader.get(Integer.parseInt(faq.getId()))).add(faq.getAnswer());
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
