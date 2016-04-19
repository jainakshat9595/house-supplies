package jainakshat.in.housesupplies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Akshat Jain on 11/23/2015.
 */
public class MainActivity extends Activity implements LogList_ArrayAdapter.customButtonListener {

    Firebase root = new Firebase("https://home-aj-supplies.firebaseio.com/");

    ListView list;
    LogList_ArrayAdapter adapter;
    private List<String> item_id, item_name, item_quan;
    Button btn_add, btn_refresh;

    sendNotif sndntf = new sendNotif();

    Map map_1;

    @Override
    public void onButtonClickListner(int position, String value) {
        root.child("ITEMS").child(value).removeValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;

        btn_add = (Button)findViewById(R.id.button);
        btn_refresh = (Button)findViewById(R.id.button2);

        btn_add.setWidth((width*60)/100);
        btn_refresh.setWidth((width*10)/100);

        item_id = new ArrayList<String>();
        item_name = new ArrayList<String>();
        item_quan = new ArrayList<String>();
        list = (ListView)findViewById(R.id.listView);

        item_id.clear();
        item_name.clear();
        item_quan.clear();

        root.child("ITEMS").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                item_id.clear();
                item_name.clear();
                item_quan.clear();
                System.out.println("There are " + snapshot.getChildrenCount() + " items in the DB.");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    map_1 = postSnapshot.getValue(java.util.HashMap.class);
                    try {
                        item_id.add(map_1.get("id").toString());
                        item_name.add(map_1.get("name").toString());
                        item_quan.add(map_1.get("quan").toString());
                    } catch (Exception e) {
                    }

                }
                adapter = new LogList_ArrayAdapter(MainActivity.this, item_id, item_name, item_quan);
                adapter.setCustomButtonListner(MainActivity.this);
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup((width*85)/100, (height*60)/100);
            }
        });

    }

    private void showPopup(int wt, int ht) {
        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) MainActivity.this.findViewById(R.id.add_popup);
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.add_popup, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(layout, wt, ht, true);
        /*width phle height baadme*/

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0,0);

        // Getting a reference to Close button, and close the popup when clicked.
        final EditText name = (EditText) layout.findViewById(R.id.editText1);
        final EditText quan = (EditText) layout.findViewById(R.id.editText2);
        Button settle_all = (Button) layout.findViewById(R.id.button8);
        Button close = (Button) layout.findViewById(R.id.button9);

        settle_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Firebase pushRef = root.child("ITEMS").push();
                String i_name = name.getText().toString();
                String i_quan = quan.getText().toString();
                sndntf.notif(i_name);
                Map<String, String> post1 = new HashMap<String, String>();
                post1.put("name", i_name);
                post1.put("quan", i_quan);
                post1.put("id", pushRef.getKey());
                pushRef.setValue(post1);
                name.setText("");
                quan.setText("");
                name.requestFocus();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

}
