package Classes;

import org.json.JSONObject;

/**
 * Created by mikhail on 17.11.15.
 */
// Интерфейс для обработки серверных сообщений
public interface CommandsInterface {

    public void onMessage(JSONObject command);
    public void onChatCreate(JSONObject command);
    public void onGetRooms(JSONObject command);

    public void onChangeNickname(JSONObject command);
    public void onGetActiveClients(JSONObject command);
    public void onShowClientInfo(JSONObject command);
    public void onEnterRoom(JSONObject command);
    public void onGetHistory(JSONObject command);
    public void onShowRoomInfo(JSONObject command);

}
