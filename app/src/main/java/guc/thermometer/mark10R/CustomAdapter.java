package guc.thermometer.mark10R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, ArrayList<String> items) {
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
        switch(singleItem){
            case "SQL":
                imgView.setImageResource(R.drawable.sql);
                break;
            case "Java":
                imgView.setImageResource(R.drawable.java);
                break;
            case "JavaScript":
                imgView.setImageResource(R.drawable.javascript);
                break;
            case "C#":
                imgView.setImageResource(R.drawable.seessharp);
                break;
            case "Python":
                imgView.setImageResource(R.drawable.python);
                break;
            case "C++":
                imgView.setImageResource(R.drawable.seeplusplus);
                break;
            case "PHP":
                imgView.setImageResource(R.drawable.php);
                break;
            case "Koltin":
                imgView.setImageResource(R.drawable.kotlin);
                break;
            default:
                imgView.setImageResource(R.drawable.esp);

        }
        return CustomView;
    }
}
