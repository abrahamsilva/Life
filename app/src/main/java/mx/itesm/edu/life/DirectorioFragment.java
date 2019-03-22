package mx.itesm.edu.life;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.ContactRecycleAdapter;
import mx.itesm.edu.life.models.Contact;

public class DirectorioFragment extends Fragment {

    private List<Contact> contacts;
    private RecyclerView recyclerView;

    public static DirectorioFragment newInstance(){
        DirectorioFragment fragment = new DirectorioFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_directorio, container, false);
        getActivity().setTitle(R.string.title_directorio);
        recyclerView = rootView.findViewById(R.id.recycleView);

        contacts = new ArrayList<>();
        Contact c1 = new Contact("1","Yoali Sotomayor", "yoali@mail.com");
        contacts.add(c1);
        Contact c2 = new Contact("2","Abraham Silva", "abraham@mail.com");
        contacts.add(c2);
        setRecyclerView(contacts);

        return rootView;
    }

    private void setRecyclerView(List<Contact> contacts){
        Log.d("DIRECTORIO", contacts.get(0).toString());
        ContactRecycleAdapter contactRecycleAdapter = new ContactRecycleAdapter(this.getContext(), contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(contactRecycleAdapter);
    }
}
