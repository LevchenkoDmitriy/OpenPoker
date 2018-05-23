import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.*;

public class GameLoop implements Runnable {
    //Создаём множество пользователей, из них формируем игру
    private Map<String, Player> UserMap;

    GameLoop(Map<String, Player> Map) {
        UserMap = Map;
    }

    //Для работы JSON
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    //Обработчик команд с клиента
    private CommandHandler CommandHandler = new CommandHandler();

    @Override
    public void run() {
        //Сообщаем всем игрокам, что игра началась
        String str = "game started";
        sendToAll(UserMap, str);

        //TODO Выбор дилера

        //Обнуляем банк перед игрой
        Server.Status.setBank(0);

        //Создаём колоду
        CardDeck Cards = new CardDeck();
        Cards.newDeck();

        //Отправляем игрокам их карты, а так же карты на столе
        generateCardsAndSendToUsers(Cards);

        //Забираем слепые ставки
        setBlind();

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

                    //Ожидаем комнады и обрабатываем их
                    String Command = in.readUTF();

                    CommandHandler.catchCommand(Command, player);

                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    //Отправка JSON сразу всем пользователям
    public void sendToAll(Map<String, Player> Map, Object o) {
        for (Map.Entry<String, Player> entry : Map.entrySet()) {
            Player player = entry.getValue();

            try {
                DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());

                String message = gson.toJson(o);
                out.writeUTF(message);

            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    //Отправка JSON одному пользователю
    public void sendToOne(Player player, Object o) {
        try {
            DataOutputStream out = new DataOutputStream(player.PlayerSocket.getOutputStream());

            String message = gson.toJson(o);
            out.writeUTF(message);
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    public void setBlind() {

        //Костыль с малым и большим блайндом
        boolean smallBlind = false;
        boolean bigBlind = false;

        //Проходимся по всем игрокам
        for (Map.Entry<String, Player> blindEntry : UserMap.entrySet()) {
            String keyBlind = blindEntry.getKey();

            //Если человек дилер, то следующие 2 за ним должны заплатить
            if (UserMap.get(keyBlind).isDealer()) {
                smallBlind = true;
            }

            //Сначала малый блайнд в 50. После отбирания денег, отправляем команду клиенту.
            if (smallBlind) {
                try {
                    DataOutputStream out = new DataOutputStream(UserMap.get(keyBlind).PlayerSocket.getOutputStream());

                    UserMap.get(keyBlind).decreaseBalance(50);
                    Server.Status.increaseBank(50);
                    out.writeUTF("blind 50");
                    smallBlind = false;
                    bigBlind = true;
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }

            //Теперь большой блайнд в 100.
            if (bigBlind) {
                try {
                    DataOutputStream out = new DataOutputStream(UserMap.get(keyBlind).PlayerSocket.getOutputStream());
                    UserMap.get(keyBlind).decreaseBalance(100);
                    Server.Status.increaseBank(100);
                    out.writeUTF("blind 100");
                    bigBlind = false;
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    public void generateCardsAndSendToUsers(CardDeck Cards){

        String users = "";
        //Раздаём каждому по 2 карты
        for (Map.Entry<String, Player> entry : UserMap.entrySet()) {
            Player player = entry.getValue();

            //Записываем всех пользователей по правилу nickname:balance
            users = users + player.getName() + ":" + player.getBalance() + "\n";

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

        //Рассылаем всем пользователям
        sendToAll(UserMap, users);

        //Раздаём карты на стол
        for (int i = 0; i < 5; i++) {
            Cards.boardCards[i] = Cards.popCard();
        }

        //Генерируем JSON и отправляем всем игрокам
        sendToAll(UserMap, gson.toJson(Cards.boardCards));
    }

    //Вспомогательные функции, получение первого и следущего за элементом ключа
    private String getFirstKey(Map<String, Player> Map){
        //Получаем первый элемент из Map
        Map.Entry<String, Player> firstEntry = (Entry<String, Player>) Map.entrySet().iterator().next();
        return firstEntry.getKey();
    }

    private String getNextKey(String key, Map<String, Player> Map){
        for(Map.Entry<String, Player> entry : Map.entrySet()){
            if(entry.getKey().equals(key)){
                Map.Entry<String, Player> nextEntry = (Entry<String, Player>) Map.entrySet().iterator().next();
                return nextEntry.getKey();
            }
        }
        return "0";
    }
}