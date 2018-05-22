import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private static Socket socket;

    public Client() {
        try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (
                Scanner consoleInput = new Scanner(System.in);

                // создаём объект для записи и чтения строк из сокета
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())) {
             System.out.println("Client oos & ois initialized");

            //Приветствие и ввод никнейма
            System.out.println(in.readUTF());
            Thread.sleep(20);
            out.writeUTF(consoleInput.next());
            System.out.println(in.readUTF());
            Thread.sleep(20);
            while(true){

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}