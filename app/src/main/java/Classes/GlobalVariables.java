package Classes;

import android.app.Application;

import de.tavendo.autobahn.WebSocketConnection;

/**
 * Created by mikhail on 17.11.15.
 */
public class GlobalVariables{
    public static void setVariables(){
        wscon = new WebSocketConnection();
        wsuri = "ws://10.0.3.2:3000";
    }
    public static WebSocketConnection wscon;

    public static String wsuri;

    // Пример обращения к глобальным переменным
    //GlobalVariables glob = (GlobalVariables)this.getApplication();
}
