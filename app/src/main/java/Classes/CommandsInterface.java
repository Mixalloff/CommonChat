package Classes;

import org.json.JSONObject;

/**
 * Created by mikhail on 17.11.15.
 */
public interface CommandsInterface {

    public void onMessage(JSONObject command);
    public void onChatCreate(JSONObject command);
    public void onGetRooms(JSONObject command);

}
