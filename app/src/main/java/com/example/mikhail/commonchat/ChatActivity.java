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

import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import Classes.ChatCommands;
import Classes.CommandManager;
import Classes.CommandsInterface;
import Classes.GlobalVariables;
import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
//import de.tavendo.autobahn.WebSocketHandler;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "de.tavendo.test1";

    //private final WebSocketConnection mConnection = new WebSocketConnection();
    private final WebSocketConnection mConnection = GlobalVariables.wscon;
    CommandManager cmdManager;
    CommandsInterface connectInterface;

   // WebSocket.ConnectionHandler connectionHandler;

    private void start() {
        //final String wsuri = "ws://109.60.225.158:3000";
        //final String wsuri = "ws://10.0.3.2:3000";
       // try {
            //mConnection.connect(wsuri, new WebSocketConnectionHandler() {

         /*   });
        } catch (WebSocketException e) {
            Log.d(TAG, e.toString());
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        connectInterface = new CommandsInterface() {
            @Override
            public void onMessage(JSONObject command) {
                TextView chatSpace = (TextView)findViewById(R.id.chatText);
                chatSpace.setMovementMethod(new ScrollingMovementMethod());

                try {
                    JSONObject msg = new JSONObject(command.get("data").toString());
                    chatSpace.setText(chatSpace.getText() + "\n" + msg.get("sender")+": "+msg.get("message"));
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
        };

        //connectionHandler = new WebSocketConnectionHandler() {
        WebSocket.ConnectionHandler connectionHandler = new WebSocket.ConnectionHandler() {
            @Override
            public void onOpen() {
                //Log.d(TAG, "Status: Connected to " + wsuri);
                //mConnection.sendTextMessage("Hello, world!");
                //JSONObject checkRoom = new JSONObject();
                    /*try {
                        checkRoom.put("type", "getrooms");
                        checkRoom.put("data", "checkRoom");
                        mConnection.sendTextMessage(checkRoom.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
int i = 1;
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

                    /*Log.d(TAG, "Got echo: " + message);
                    JSONObject mes = null;

                    try {
                        mes = new JSONObject(message);
                        if (mes.get("type").equals("getrooms"))
                        {
                            //getRoom(mes);
                        }
                        if (mes.get("type").equals("newchat"))
                        {
                            //joinRoom(mes);
                        }
                        if (mes.get("type").equals("message"))
                        {
                            TextView chatSpace = (TextView)findViewById(R.id.chatText);
                            chatSpace.setMovementMethod(new ScrollingMovementMethod());

                            JSONObject msg = (JSONObject)mes.get("data");
                            chatSpace.setText(chatSpace.getText() + "\n" + msg.get("sender")+": "+msg.get("message"));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

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
        /*try {
            mConnection.connect(GlobalVariables.wsuri, connectionHandler);

        } catch (WebSocketException e) {
            e.printStackTrace();
        }*/

        cmdManager = new CommandManager();
        String roomName = getIntent().getStringExtra("Title");
        setTitle(roomName);

        //this.start();
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
        //JSONObject json = new JSONObject();
        //TextView chatSpace = (TextView)findViewById(R.id.chatText);
        EditText enteredText = (EditText)findViewById(R.id.messageField);
        /*json.put("type", "sendmessage");
        json.put("data", enteredText.getText().toString());
        mConnection.sendTextMessage(json.toString());*/
        ChatCommands.sendMessage(enteredText.getText().toString());
        enteredText.setText("");
    }

    /*public void joinRoom(JSONObject data) throws JSONException {
        String id = data.get("data").toString();
        if (id.contains("[")){
            id = id.substring(1, id.length()-1);
        }
        JSONObject json = new JSONObject();
        JSONObject idJSON = new JSONObject();
        idJSON.put("id", id);
        json.put("type", "enterroom");
        json.put("data", idJSON);
        mConnection.sendTextMessage(json.toString());
        //ChatCommands.enterRoom(idJSON);

    }

    public void getRoom(JSONObject data) throws JSONException {
        //Array a = new Array();
        if(data.get("data").toString().equals("[]")){
            JSONObject json = new JSONObject();

            JSONArray ar = new JSONArray();
            json.put("type", "newchat");
            json.put("data", "asd");
            mConnection.sendTextMessage("{\"type\":\"newchat\", \"data\":{\"participants\":[]}}");
        }
        else{
            joinRoom(data);
        }
    }*/
}
