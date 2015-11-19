package com.example.mikhail.commonchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ParticipantsActivity extends AppCompatActivity {

    private String participantsWordForm(int count){
        if (count % 10 == 1 && count / 10 != 1){
            return "участник";
        }
        if ((count % 10 == 2 || count % 10 == 3 || count % 10 == 4) && count / 10 != 1){
            return "участника";
        }
        return "участников";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        int countPersons = getIntent().getIntExtra("countPersons", 1);
        setTitle(countPersons + " " + participantsWordForm(countPersons));
        Bundle b=this.getIntent().getExtras();
        ArrayList<String> persons=b.getStringArrayList("persons");
        ListView lvMain = (ListView) findViewById(R.id.participantsList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, persons);
        lvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
