package jainakshat.in.housesupplies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akshat Jain on 11/24/2015.
 */
public class LogList_ArrayAdapter extends ArrayAdapter<String> {

    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position,String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private final Activity context;
    private final List<String> name;
    private final List<String> id;
    private final List<String> quan;
    public LogList_ArrayAdapter(Activity context,List<String> id, List<String> name,List<String> quan) {
        super(context, R.layout.log_list_layout, id);
        this.context = context;
        this.name = name;
        this.id = id;
        this.quan = quan;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.log_list_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text_name = (TextView) convertView
                    .findViewById(R.id.textView_name);
            viewHolder.text_quan = (TextView) convertView
                    .findViewById(R.id.textView_quan);
            viewHolder.button = (Button) convertView
                    .findViewById(R.id.btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String temp = getItem(position);
        viewHolder.text_name.setText(name.get(position));
        viewHolder.text_quan.setText(quan.get(position));
        viewHolder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                }

            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView text_name;
        TextView text_quan;
        Button button;
    }

}