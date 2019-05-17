package mx.itesm.edu.life;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import mx.itesm.edu.life.adapters.ContactRecycleAdapter;
import mx.itesm.edu.life.models.Contact;

public class DirectorioFragment extends Fragment {

    private List<Contact> contacts;
    private RecyclerView recyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView emptyView;
    private ValueEventListener eventListener;
    private Bundle mBundleRecyclerViewState;



    public static DirectorioFragment newInstance(){
        DirectorioFragment fragment = new DirectorioFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_directorio, container, false);
        getActivity().setTitle(R.string.title_directorio);
        recyclerView = rootView.findViewById(R.id.recycleView);
        emptyView = rootView.findViewById(R.id.empty);
        mFirebaseDatabase = MainActivity.getDatabase();
        myRef = mFirebaseDatabase.getReference("contacts");
        contacts = new ArrayList<>();

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot contactSnapshot : dataSnapshot.getChildren()){
                  Contact contact = contactSnapshot.getValue(Contact.class);
                  contacts.add(contact);
                }
                if(contacts.size()<=0){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                setRecyclerView(contacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        myRef.addValueEventListener(eventListener);

        return rootView;
    }

    private void setRecyclerView(List<Contact> contacts){
        this.contacts = contacts;
        ContactRecycleAdapter contactRecycleAdapter =
                new ContactRecycleAdapter(this.getContext(), contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(contactRecycleAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myRef.removeEventListener(eventListener);
    }
}
