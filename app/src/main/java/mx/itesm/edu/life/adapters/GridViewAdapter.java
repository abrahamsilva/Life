package mx.itesm.edu.life.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

import mx.itesm.edu.life.R;
import mx.itesm.edu.life.models.Tip;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<Tip> tips;

    public GridViewAdapter(Context context, List<Tip> tips) {
        this.context = context;
        this.tips = tips;
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public Object getItem(int position) {
        return tips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String title = (String)tips.get(position).getTitle();
        Uri icon = Uri.parse(tips.get(position).getIcon());
        String desc = tips.get(position).getDesc();

        if(convertView==null){
            LayoutInflater inflater =(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_tip,null);
            ImageView imageView =(ImageView)convertView.findViewById(R.id.iconTip);
            TextView textView = (TextView)convertView.findViewById(R.id.titleTip);

            Picasso.get().load(tips.get(position).getIcon()).into(imageView);

            textView.setText(title);
        }
        return convertView;
    }
}
