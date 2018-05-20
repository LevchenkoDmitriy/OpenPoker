import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadServer implements Runnable {

    private static Socket clientDialog;

    public MonoThreadServer(Socket client) {
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

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом

            //Приветствие и ожидание ника
            out.writeUTF("Hello! Please enter your name");
            Player player = new Player();
            while (true) {
                String str = "0x0";
                str = in.readUTF();
                if (!str.equals("0x0")) {
                    player.setName(str);
                    out.writeUTF("Hello " + player.getName());
                    break;
                }
            }
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