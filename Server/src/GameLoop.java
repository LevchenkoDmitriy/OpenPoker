import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameLoop implements Runnable {
    //Создаём множество пользователей, из них формируем игру
    public Map<String, Player> UserMap;

    GameLoop(Map<String, Player> Map) {
        UserMap = Map;
    }

    @Override
    public void run() {
        /* Стартуем игру в отдельном потоке. По умолчанию 3 раунда */
        for (int Rounds = 1; Rounds <= 3; Rounds++) {
            for (Map.Entry<String, Player> entry : UserMap.entrySet()) {
                //Получаем имя/структуру игрока
                String key = entry.getKey();
                Player player = entry.getValue();
                try {
                    //Создаём потоки ввода/вывода для клиента
                    DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());
                    DataInputStream in = new DataInputStream(player.PlayerSocket.getInputStream());


                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    //Отсоединение клиента
    public void Disconnect(Player player) {
        try {
            //Отсоединяем клиент и удаляем его из списка клиентов
            player.PlayerSocket.close();
            Server.Status.ClientMap.remove(player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}