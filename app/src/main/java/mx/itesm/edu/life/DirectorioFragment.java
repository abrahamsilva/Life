package mx.itesm.edu.life;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mx.itesm.edu.life.adapters.ContactRecycleAdapter;
import mx.itesm.edu.life.models.Contact;

public class DirectorioFragment extends Fragment {

    private List<Contact> contacts;
    private RecyclerView recyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    public static DirectorioFragment newInstance(){
        DirectorioFragment fragment = new DirectorioFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_directorio, container, false);
        getActivity().setTitle(R.string.title_directorio);
        recyclerView = rootView.findViewById(R.id.recycleView);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("contacts");

        contacts = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot contactSnapshot : dataSnapshot.getChildren()){
                  Contact contact = contactSnapshot.getValue(Contact.class);

                  contacts.add(contact);
                }
                setRecyclerView(contacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return rootView;
    }

    private void setRecyclerView(List<Contact> contacts){
        this.contacts = contacts;
        Log.d("DIRECTORIO", contacts.get(0).toString());
        ContactRecycleAdapter contactRecycleAdapter = new ContactRecycleAdapter(this.getContext(), contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(contactRecycleAdapter);
    }
}
