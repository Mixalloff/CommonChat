package Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 17.11.15.
 */
public class CommandManager implements CommandsInterface{
    public CommandManager(){

    }

    public void CheckCommand(JSONObject command, CommandsInterface cmd) throws JSONException {
        switch (command.get("type").toString()){
            case "newchat": { cmd.onChatCreate(command);break;}
            case "getrooms": { cmd.onGetRooms(command);break;}
            case "message": { cmd.onMessage(command); break;}

            case "changenickname": { cmd.onChangeNickname(command); break;}
            case "activeclients": { cmd.onGetActiveClients(command); break;}
            case "showclientinfo": { cmd.onShowClientInfo(command); break;}
            case "room": { cmd.onEnterRoom(command); break;}
            case "gethistory": { cmd.onGetHistory(command); break;}
            case "showroominfo": { cmd.onShowRoomInfo(command);break;}

            default: {};
        }
    }

    @Override
    public void onMessage(JSONObject command){}

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

    }

    @Override
    public void onShowRoomInfo(JSONObject command) {

    }


}
