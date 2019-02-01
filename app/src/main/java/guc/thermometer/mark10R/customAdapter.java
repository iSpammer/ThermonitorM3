package guc.thermometer.mark10R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


class customAdapter extends ArrayAdapter<String> {
    public customAdapter(Context context, String[] items) {
        super(context,R.layout.custom_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View CustomView = inflater.inflate(R.layout.custom_row,parent,false);
        String singleItem = getItem(position);
        TextView itemText = CustomView.findViewById(R.id.listtv);
        ImageView imgView = CustomView.findViewById(R.id.imageView);
        itemText.setText(singleItem);
        imgView.setImageResource(R.mipmap.ic_launcher_round);
        return CustomView;
    }
}
