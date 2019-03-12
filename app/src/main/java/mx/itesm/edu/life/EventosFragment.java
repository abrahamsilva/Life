package mx.itesm.edu.life;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.itesm.edu.life.models.Contact;

public class EventosFragment extends Fragment {

    public static EventosFragment newInstance(){
        EventosFragment fragment = new EventosFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eventos, container, false);
        return rootView;
    }
}
