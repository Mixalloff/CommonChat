package com.example.mikhail.commonchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import de.tavendo.autobahn.WebSocketException;

public class ChooseRoomActivity extends AppCompatActivity {

    ListView listView;
    CommandManager cmdManager;
    CommandsInterface Icmd;
    WebSocketConnection webSocket;
    ArrayList<String> roomNames;
    ArrayList<String> roomIds;
    ArrayAdapter<String> adapt;

    // Запросы (пока хардкод)
    //String newChat = "{\"type\":\"newchat\", \"data\":{\"participants\":[]}}";
    //String getRoomsJson = "{\"type\":\"getrooms\", \"data\": \"ass\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        roomNames = new ArrayList<String>();
        roomIds = new ArrayList<String>();
        webSocket = GlobalVariables.wscon;
        // Получаем комнаты
        ChatCommands.getRooms();

        WebSocket.ConnectionHandler connectHandler = new WebSocket.ConnectionHandler() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose(int code, String reason) {

            }

            @Override
            public void onTextMessage(String payload) {
                cmdManager = new CommandManager();
                try {
                    JSONObject cmd = new JSONObject(payload);
                    cmdManager.CheckCommand(cmd, Icmd);
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
        };

        webSocket.changeHandler(connectHandler);

        /*try {
            webSocket.connect(GlobalVariables.wsuri, connectHandler);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }*/

        // Обработка ответа сервера
            Icmd = new CommandsInterface() {
                @Override
                public void onMessage(JSONObject command) {
                    //Toast.makeText(getApplicationContext(), "Message sended!",
                    //        Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChatCreate(JSONObject command) {

                }

                @Override
                public void onGetRooms(JSONObject command) {
                    try {
                        //JSONObject data = new JSONObject(command.get("data").toString());
                        JSONArray roomsData = new JSONArray(command.get("data").toString());
                        roomNames.clear();
                        roomIds.clear();
                        for (int i=0; i<roomsData.length(); i++){
                            JSONObject currentData = new JSONObject(roomsData.get(i).toString());
                            roomNames.add(currentData.get("name").toString());
                            roomIds.add(currentData.get("id").toString());
                            //rooms.add(roomsData.get(i).toString());
                        }
                        adapt.notifyDataSetChanged();
                    }
                    catch (Exception e){
                    }
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

                }

                @Override
                public void onShowRoomInfo(JSONObject command) {

                }
            };

        // Добавление пунктов в список
        listView = (ListView)findViewById(R.id.roomsList);
        String[] testRooms = new String[]{"Политика", "Украина", "IGIL", "Economy", "Putin"};

        adapt = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, roomNames);
        listView.setAdapter(adapt);
        // Обработка клика по пункту
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                String elementText = ((TextView) itemClicked).getText().toString();

                Toast.makeText(getApplicationContext(), "Вы вошли в комнату " + elementText,
                        Toast.LENGTH_SHORT).show();
                ChatCommands.enterRoom(roomIds.get(position));

                Intent intent = new Intent(ChooseRoomActivity.this, ChatActivity.class);
                intent.putExtra("Title", elementText);
                intent.putExtra("roomId", roomIds.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_room, menu);
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

    // Обработка нажатия кнопки "Новая комната"
    public void onNewRoomBtnClick(View view) {
        EditText newName = (EditText)findViewById(R.id.newRoomName);
        //webSocket.sendTextMessage(ChatCommands.newChat(newName.getText().toString()));
        ChatCommands.newChat(newName.getText().toString());
        //webSocket.sendTextMessage(ChatCommands.getRooms());
        ChatCommands.getRooms();

        Toast.makeText(getApplicationContext(), "Комната " + newName.getText().toString() + " создана",
                Toast.LENGTH_SHORT).show();
        newName.setText("");
    }

    // Обработка нажатия кнопки "Обновить"
    public void onRefreshBtnClick(View view) {
        //webSocket.sendTextMessage(ChatCommands.getRooms());
        ChatCommands.getRooms();
        Toast.makeText(getApplicationContext(), "Обновлено",
                Toast.LENGTH_SHORT).show();
    }
}
