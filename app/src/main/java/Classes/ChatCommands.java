package Classes;

import android.support.annotation.ArrayRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mikhail on 17.11.15.
 */
public class ChatCommands {

    // Генерирует команду создания пустого чата (комнаты)
    public static void newChat(String name){
        ArrayList<String> persons = new ArrayList<String>();
        newChat(persons, name);
    }

    // Генерирует команду создания чата (комнаты)
    // persons - участники в комнате
    public static void newChat(ArrayList<String> persons, String name){
        JSONObject cmd = new JSONObject();
        JSONArray participants = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            data.put("participants",participants);
            data.put("name",name);
            cmd.put("type", "newchat");
            cmd.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GlobalVariables.wscon.sendTextMessage(cmd.toString());
    }

    public static void getRooms(){
        JSONObject cmd = new JSONObject();
        try{
            cmd.put("type", "getrooms");
            cmd.put("data","get all rooms");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalVariables.wscon.sendTextMessage(cmd.toString());
    }

    // Войти в комнату с id = roomId
    public static void enterRoom(String roomId){
        JSONObject cmd = new JSONObject();
        JSONObject id = new JSONObject();
        try{
            id.put("id", roomId);
            cmd.put("type", "enterroom");
            cmd.put("data",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalVariables.wscon.sendTextMessage(cmd.toString());
    }

    // Отправка сообщения
    public static void sendMessage(String message){
        JSONObject cmd = new JSONObject();
        try{
            cmd.put("type", "sendmessage");
            cmd.put("data",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GlobalVariables.wscon.sendTextMessage(cmd.toString());
    }

    /*public static void getCommand(String type){

        switch(type){
            case "newchat": {}
            default: {}
        }
        return "";
    }*/
}
