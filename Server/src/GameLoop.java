import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import com.google.gson.*;

public class GameLoop implements Runnable {
    //Создаём множество пользователей, из них формируем игру
    private Map<String, Player> UserMap;

    GameLoop(Map<String, Player> Map) {
        UserMap = Map;
    }

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public void run() {
        //Сообщаем всем игрокам, что игра началась
        String str = "game started";
        sendToAll(UserMap, str);

        //Создаём колоду
        CardDeck Cards = new CardDeck();
        Cards.newDeck();

        //Раздаём каждому по 2 карты
        for (Map.Entry<String, Player> entry : UserMap.entrySet()) {
            String key = entry.getKey();
            Player player = entry.getValue();

            //Вытаскиваем 2 карты
            CardDeck.Card[] userCards = new CardDeck.Card[2];
            userCards[0] = Cards.popCard();
            userCards[1] = Cards.popCard();

            //Передаём их игроку
            player.setCards(userCards);

            //Отправляем игроку
            sendToOne(player, player.getCards());

            //В Map'e тоже отображаем изменения
            UserMap.replace(player.getName(), player);
        }

        //Раздаём карты на стол
        for(int i = 0; i < 5; i++){
            Cards.boardCards[i] = Cards.popCard();
        }
        //Генерируем JSON и отправляем всем игрокам
        sendToAll(UserMap, gson.toJson(Cards.boardCards));



        /* Стартуем игру. 3 раунда */
        for (int Rounds = 1; Rounds <= 3; Rounds++) {
                for (Map.Entry<String, Player> entry : UserMap.entrySet()) {
                    //Получаем имя/структуру игрока
                    String key = entry.getKey();
                    Player player = entry.getValue();
                    try {
                        //Создаём потоки ввода/вывода для клиента
                        DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());
                        DataInputStream in = new DataInputStream(player.PlayerSocket.getInputStream());

                        //Говорим игроку, что его ход и ждём действие в течение 30с
                        out.writeUTF("your turn");

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
           // Server.Status.ClientMap.remove(player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Отправка JSON сразу всем пользователям
    public void sendToAll(Map<String, Player> Map, Object o){
        for (Map.Entry<String, Player> entry : Map.entrySet()){
            Player player = entry.getValue();

            try {
                DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());

                String message = gson.toJson(o);
                out.writeUTF(message);

            }catch (IOException e){
                e.getStackTrace();
            }
        }
    }

    //Отправка JSON одному пользователю
    public void sendToOne(Player player, Object o){
        try {
            DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());

            String message = gson.toJson(o);
            out.writeUTF(message);
        }catch (IOException e){
            e.getStackTrace();
        }

    }
}