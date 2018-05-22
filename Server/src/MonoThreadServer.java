import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadServer implements Runnable {

    private static Socket clientDialog;

    MonoThreadServer(Socket client) {
        MonoThreadServer.clientDialog = client;
    }
    @Override
    public void run() {
        try {
            // Инициализируем каналы для записи/чтения
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");

            //Приветствие и ожидание ника
            out.writeUTF("connected");
            Player player = new Player();

            String str;
            str = in.readUTF();

            if(Server.ClientMap.containsKey(str)) {
                out.writeUTF("Nickname has already used");
                clientDialog.close();
                Thread.currentThread().interrupt();
                System.out.println("The client used an already occupied nickname");
            }else{
                player.setName(str);
                System.out.println("Player " + player.getName() + " has connected");
                out.writeUTF("connected");
            }

            //Добавляем сокет игроку и закидываем его во множество всех игроков. Теперь у нас есть пара ник <-> сокет
            player.PlayerSocket = clientDialog;
            Server.ClientMap.put(player.getName(), player);


            while (!clientDialog.isClosed()) {
                // освобождаем буфер сетевых сообщений
                out.flush();

            }

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}