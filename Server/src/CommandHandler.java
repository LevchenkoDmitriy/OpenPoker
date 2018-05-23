import java.io.IOException;

public class CommandHandler {

    public void catchCommand(String Command, Player player){
        //Выход клиента
        if(Command.equals("Disconnect")){
            Disconnect(player);
        }

       // if(Command.equals())
    }

    //Отсоединение клиента
    private void Disconnect(Player player) {
        try {
            //Отсоединяем клиент и удаляем его из списка клиентов
            player.PlayerSocket.close();
            Server.ClientMap.remove(player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

