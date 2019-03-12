package mx.itesm.edu.life.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.itesm.edu.life.R;
import mx.itesm.edu.life.models.Contact;

public class ContactRecycleAdapter
        extends RecyclerView.Adapter<ContactRecycleAdapter.ContactRecordHolder>{

    private Context context;
    private List<Contact> contacts;
    
    
    

    public ContactRecycleAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactRecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(
                R.layout.contact_item,viewGroup,
                false);
        final ContactRecordHolder contactRecordHolder
                = new ContactRecordHolder(view);
        contactRecordHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = contacts.get(contactRecordHolder.getAdapterPosition());
                Toast.makeText(context, contact.getName(), Toast.LENGTH_SHORT).show();
//                Intent it  = new Intent(context, ContactActivity.class);
//                it.putExtra("contact", contact);
//                context.startActivity(it);
            }
        });

        return contactRecordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecordHolder contactRecordHolder, int i) {
        contactRecordHolder.name.setText(contacts.get(i).getName());
        contactRecordHolder.id.setText(contacts.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactRecordHolder extends RecyclerView.ViewHolder{

        TextView id, name, artist;
        ImageView image;

        public ContactRecordHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_contact);
            name = itemView.findViewById(R.id.contact_name);
        }
    }
}
