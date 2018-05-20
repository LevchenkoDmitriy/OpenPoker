import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server{

    static ExecutorService executeIt = Executors.newFixedThreadPool(8);
    //Создаём игровую очередь

    public static void main(String[] args) {

        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Server socket created");
            ServerStatus Status = new ServerStatus();

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                //Принимаем подключение
                Socket client = server.accept();

                Status.increasePlayers();
                System.out.println(Status.getPlayers());

                executeIt.execute(new MonoThreadServer(client));
                System.out.println("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            System.out.println("No commands here");
        }
    }
}