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
            case "newchat": { cmd.onChatCreate(command);};
            case "getrooms": { cmd.onGetRooms(command);};
            case "message": { cmd.onMessage(command); };
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

    ;

}
