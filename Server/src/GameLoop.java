import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class GameLoop implements Runnable {
    //Создаём множество пользователей, из них формируем игру
    private Map UserMap = new HashMap(8);
    

    public GameLoop(Map Map) {
        UserMap = Map;
    }

    @Override
    public void run() {
        /* Стартуем игру в отдельном потоке. По умолчанию 3 раунда */
        for(int Rounds = 1; Rounds <= 3; Rounds++){
            for(Object entry: UserMap.entrySet()){

            }
        }
    }

    //Отсоединение клиента
    public void Disconnect(Socket Client) {
        try {
            Client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}