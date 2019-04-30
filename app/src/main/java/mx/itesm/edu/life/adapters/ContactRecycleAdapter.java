package mx.itesm.edu.life.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.itesm.edu.life.R;
import mx.itesm.edu.life.models.Contact;

public class ContactRecycleAdapter
        extends RecyclerView.Adapter<ContactRecycleAdapter.ViewHolder>{

    private Context context;
    private List<Contact> contacts;

    public ContactRecycleAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(
                R.layout.contact_item,viewGroup,
                false);

        ViewHolder holder = new ViewHolder(view, new ViewHolder.ClickListener() {
            @Override
            public void sendEmail(int position) {
                Contact contact = contacts.get(position);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, contact.getMail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "App Consejería y Bienestar");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(contacts.get(i).getName());
        viewHolder.desc.setText(contacts.get(i).getDesc());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ClickListener listener;
        ImageView image;
        TextView name, desc;
        ImageButton emailButton;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            desc = itemView.findViewById(R.id.id_contact);
            emailButton = itemView.findViewById(R.id.mail);

            this.listener = listener;
            emailButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mail:
                    listener.sendEmail(this.getAdapterPosition());
                    break;
                default:
                    break;
            }
        }

        public interface ClickListener {
            void sendEmail(int p);
        }
    }

}
