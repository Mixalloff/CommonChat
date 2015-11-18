package com.example.mikhail.commonchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import Classes.ChatCommands;
import Classes.GlobalVariables;
import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;
    EditText nicknameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nicknameText = (EditText) findViewById(R.id.nicknameText);
        settings = this.getSharedPreferences("settings", 0);
        try {
            String defaultNick = settings.getString("nickname", "");
            nicknameText.setText(defaultNick);
        }catch(Exception e){
            nicknameText.setText("default");
        }


        GlobalVariables.setVariables();
        try {
            GlobalVariables.wscon.connect(GlobalVariables.wsuri, new WebSocket.ConnectionHandler() {
                @Override
                public void onOpen() {

                }

                @Override
                public void onClose(int code, String reason) {

                }

                @Override
                public void onTextMessage(String payload) {

                }

                @Override
                public void onRawTextMessage(byte[] payload) {

                }

                @Override
                public void onBinaryMessage(byte[] payload) {

                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void enterChatClick(View view) {
        //Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        String nickname = nicknameText.getText().toString();

        SharedPreferences.Editor e = settings.edit();
        e.clear();
        e.putString("nickname", nickname);
        e.commit();

        ChatCommands.changeNickname(nickname);
        Intent intent = new Intent(MainActivity.this, ChooseRoomActivity.class);
        startActivity(intent);
    }

}
