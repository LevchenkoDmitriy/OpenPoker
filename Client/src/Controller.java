import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class Controller {

    private Socket socket;

    @FXML
    private TextField usr_name;
    @FXML
    private TextField ip;
    @FXML
    private TextField port;

    private String portstr;

    private String gotin, sendout;
    private boolean play=false;

    @FXML
    private void initialize() {
    }


    public void play_poc(javafx.scene.input.MouseEvent mouseEvent) {
        //проверка правильности порта
        portstr = port.getText();
        if(!(isNumber(portstr))){
         port.setText("it is n't number");
         return;
        }

        //подключение к серверу
        if (!(connecter(usr_name.getText(),ip.getText(),portstr))){
            ip.setText("can not connect");
            return;
        }

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            gotin = in.readUTF();
            if (gotin.equals("connected")){
                out.writeUTF(usr_name.getText());
                gotin = in.readUTF();
                if (! gotin.equals("connected")){
                    usr_name.setText(gotin);
                }
                else {play=true;}
            }
        }
        catch (IOException e){
            e.getStackTrace();
        }



        if (play) {
            Parent SecondSceneParent = null;
            try {
                SecondSceneParent = FXMLLoader.load(getClass().getResource("desktop.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert SecondSceneParent != null;
            Scene SecondScene = new Scene(SecondSceneParent);

            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(SecondScene);
            window.show();
        }

        while (play) {

        }

    }

    private static boolean isNumber(String s) {
        if (s.length() == 0) {
            return false;
        }
        char ch = s.charAt(0);
        return ((ch >= '0') && (ch <= '9'));
    }

    public void exit(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

    private boolean connecter( String name, String IP, String port) {
        try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket(IP, parseInt(port));
            Thread.sleep(2000);
        } catch (Exception e) {
          return false  ;
        }
        return true;
    }

    public void call_act(ActionEvent actionEvent) {
    }

    public void fold_act(ActionEvent actionEvent) {
    }

    public void check_act(ActionEvent actionEvent) {
    }

    public void raise_act(ActionEvent actionEvent) {
    }
}
