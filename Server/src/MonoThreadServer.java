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

            while (true) {
                String str;
                str = in.readUTF();
                if (Server.Status.ClientMap.get(str) == null) {
                    if (!str.equals("")) {
                        player.setName(str);
                        break;
                    }
                }else{
                    out.writeUTF("Nickname has already used");
                    clientDialog.close();

                }
            }

            //Добавляем сокет игроку и закидываем его во множество всех игроков. Теперь у нас есть пара ник <-> сокет
            player.PlayerSocket = clientDialog;
            Server.Status.ClientMap.put(player.getName(), player);

            System.out.println("Player " + player.getName() + " is connected");

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