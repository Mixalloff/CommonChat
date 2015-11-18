package com.example.mikhail.commonchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Classes.ChatCommands;
import Classes.CommandManager;
import Classes.CommandsInterface;
import Classes.GlobalVariables;
import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "de.tavendo.test1";

    private final WebSocketConnection mConnection = GlobalVariables.wscon;
    CommandManager cmdManager;
    CommandsInterface connectInterface;
    String roomName;
    String roomId;
    ListView messageList;
    ArrayAdapter<String> adapt;
    ArrayList<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messages = new ArrayList<String>();
        messageList = (ListView)findViewById(R.id.listView);
        messageList.setDivider(getResources().getDrawable(android.R.color.transparent));
        messageList.setDividerHeight(10);
        adapt = new ArrayAdapter<String>(this,	R.layout.message, messages);
        messageList.scrollBy(0,0);
        messageList.setAdapter(adapt);

        roomName = getIntent().getStringExtra("Title");
        roomId = getIntent().getStringExtra("roomId");
        setTitle(roomName);


        connectInterface = new CommandsInterface() {
            @Override
            public void onMessage(JSONObject command) {
                //TextView chatSpace = (TextView)findViewById(R.id.chatText);
                //chatSpace.setMovementMethod(new ScrollingMovementMethod());

                try {
                    JSONObject msg = new JSONObject(command.get("data").toString());
                    messages.add("(" + msg.get("time") + ") "+msg.get("senderName")+":\n"+msg.get("message"));
                    adapt.notifyDataSetChanged();

                    //chatSpace.setText(chatSpace.getText() + "\n(" + msg.get("time") + ") "+msg.get("senderName")+":\n"+msg.get("message"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onChatCreate(JSONObject command) {

            }

            @Override
            public void onGetRooms(JSONObject command) {

            }

            @Override
            public void onChangeNickname(JSONObject command) {

            }

            @Override
            public void onGetActiveClients(JSONObject command) {

            }

            @Override
            public void onShowClientInfo(JSONObject command) {

            }

            @Override
            public void onEnterRoom(JSONObject command) {

            }

            @Override
            public void onGetHistory(JSONObject command) {
                /*TextView chatSpace = (TextView)findViewById(R.id.chatText);
                chatSpace.setMovementMethod(new ScrollingMovementMethod());*/
                messages.clear();
                try {
                    JSONArray data = new JSONArray(command.get("data").toString());
                    for (int i = 0; i < data.length(); i++){
                        JSONObject msg = new JSONObject(data.get(i).toString());
                        messages.add("(" + msg.get("time") + ") "+msg.get("senderName")+":\n"+msg.get("message"));
                    }
                    adapt.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onShowRoomInfo(JSONObject command) {

            }
        };

        WebSocket.ConnectionHandler connectionHandler = new WebSocket.ConnectionHandler() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onTextMessage(String message) {
                cmdManager = new CommandManager();
                try {
                    JSONObject cmd = new JSONObject(message);
                    cmdManager.CheckCommand(cmd, connectInterface);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRawTextMessage(byte[] payload) {

            }

            @Override
            public void onBinaryMessage(byte[] payload) {

            }

            @Override
            public void onClose(int code, String reason) {
                Log.d(TAG, "Connection lost.");
            }
        };

        mConnection.changeHandler(connectionHandler);
        cmdManager = new CommandManager();
        ChatCommands.getHistory();
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

    public void enterBtnClick(View view) throws JSONException {
        EditText enteredText = (EditText)findViewById(R.id.messageField);
        ChatCommands.sendMessage(enteredText.getText().toString());
        enteredText.setText("");
    }
}
