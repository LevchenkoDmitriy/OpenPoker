import java.io.IOException;

public class CommandHandler {
    public void catchCommand(String Command, Player player){

        //Обрабатываем команды в виде command:value
        String[] str;
        str = Command.split("/^[A-Za-z0-9][:][A-Za-z0-9]$/");

        //Выход клиента
        if(str[0].equals("Disconnect")){
            Disconnect(player);
            return;
        }

        //Ставка
        if(str[0].equals("Bet")){
            player.decreaseBalance(Integer.parseInt(str[1]));
            Server.Status.increaseBank(Integer.parseInt(str[1]));
            return;
        }

        //Пропуск хода
        if(str[0].equals("Check")){
            return;
        }

        //Сброс карт
        if(str[0].equals("Fold")){
            GameLoop.UserMap.remove(player.getName());
            return;
        }
    }

    //Отсоединение клиента
    private void Disconnect(Player player) {
        try {
            //Отсоединяем клиент и удаляем его из списка клиентов
            player.PlayerSocket.close();
            Server.ClientMap.remove(player.getName());
            Server.Status.decreasePlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

