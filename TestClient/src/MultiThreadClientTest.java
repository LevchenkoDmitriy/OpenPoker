import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadClientTest {

    // private static ServerSocket server;

    public static void main(String[] args) throws IOException, InterruptedException {

        ExecutorService exec = Executors.newFixedThreadPool(8);
        int j = 0;

        // стартуем цикл в котором с паузой в 10 милисекунд стартуем Runnable клиентов
        while (j < 1) {
            j++;
            exec.execute(new Client());
            Thread.sleep(10);
        }

        // закрываем фабрику
        exec.shutdown();
    }
}