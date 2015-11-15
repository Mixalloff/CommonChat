package com.example.mikhail.commonchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
//import de.tavendo.autobahn.WebSocketHandler;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "de.tavendo.test1";

    private final WebSocketConnection mConnection = new WebSocketConnection();

    private void start() {
        //final String wsuri = "ws://109.60.225.158:3000";
        final String wsuri = "ws://109.60.225.158:3000";
        try {
            mConnection.connect(wsuri, new WebSocketConnectionHandler() {
                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    //mConnection.sendTextMessage("Hello, world!");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                }
            });
        } catch (WebSocketException e) {
            Log.d(TAG, e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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

    public void enterBtnClick(View view){
        TextView chatSpace = (TextView)findViewById(R.id.chatText);
        EditText enteredText = (EditText)findViewById(R.id.messageField);
        mConnection.sendTextMessage(enteredText.getText().toString());

        chatSpace.setMovementMethod(new ScrollingMovementMethod());
        chatSpace.setText(chatSpace.getText() + "\n" + enteredText.getText());
        enteredText.setText("");
    }
}
