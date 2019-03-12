package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.sql.DriverManager.println;

public class FaqsFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>>listHashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_faqs,container,false);

        expandableListView = (ExpandableListView)rootView.findViewById(R.id.expListView);
        initData();
        expandableListAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listHashMap);
        expandableListView.setAdapter(expandableListAdapter);

        return rootView;
    }

    public void initData(){

        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Pregunta 1");
        listDataHeader.add("Pregunta 2");
        listDataHeader.add("Pregunta 3");
        listDataHeader.add("Pregunta 4");
        listDataHeader.add("Pregunta 5");

        for(int i=0;i<listDataHeader.size();i++){
            listHashMap.put(listDataHeader.get(i),new ArrayList<String>());
            listHashMap.get(listDataHeader.get(i)).add("Lorem ipsum dolor sit amet, purus pharetra tristique quis " +
                    "dolor sit tellus, scelerisque bibendum platea, a sed. Aliquet ornare phasellus, suspendisse odio, " +
                    "aenean sem felis eros erat, ut etiam, ac maecenas facilisis.");
        }

    }
}
