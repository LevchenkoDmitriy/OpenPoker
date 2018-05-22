import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    public static ServerStatus Status = new ServerStatus();

    public static Map<String, Player> ClientMap = new HashMap<String, Player>(8);

    private static ExecutorService executeIt = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {

        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Server socket created");

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                //Принимаем подключение
                Socket client = server.accept();

                //Запускаем нить с отдельным клиентом
                executeIt.execute(new MonoThreadServer(client));

                //Увеличиваем число игроков
                Server.Status.increasePlayers();

                //Если игроков больше двух и игра не запущена, то стартуем игру
                if((Status.getPlayers() >= 2) && !Status.isGameStarted){
                    Status.isGameStarted = true;
                    executeIt.execute(new GameLoop(ClientMap));
                    System.out.println("Game started");
                }
                System.out.println("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}