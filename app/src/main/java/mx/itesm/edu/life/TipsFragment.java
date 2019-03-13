package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.GridViewAdapter;
import mx.itesm.edu.life.models.Tip;

public class TipsFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Tip> tips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tips,container,false);

        gridView = (GridView)rootView.findViewById(R.id.gridView);
        initData();
        gridViewAdapter = new GridViewAdapter(getActivity(),tips);
        gridView.setAdapter(gridViewAdapter);

        return rootView;
    }

    public void initData() {

        tips = new ArrayList<>();

        Tip tip1 = new Tip("Ghosting",R.drawable.logo);
        Tip tip2 = new Tip("Alcohol",R.drawable.logo);
        Tip tip3 = new Tip("Micromachismos",R.drawable.logo);
        Tip tip4 = new Tip("Depresión",R.drawable.logo);

        tips.add(tip1);
        tips.add(tip2);
        tips.add(tip3);
        tips.add(tip4);



    }
}
