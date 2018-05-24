import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
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

    private String gotin, sendout, comand;
    private boolean play = false;
    private Player[] players;
    private int myID;
    private int ammo;
    private int bank;
    private int maxbet = 100;
    boolean able = false;

    @FXML
    private void initialize() {
    }


    public void play_poc(javafx.scene.input.MouseEvent mouseEvent) {
        //проверка правильности порта
        portstr = port.getText();
        if (!(isNumber(portstr))) {
            port.setText("it is n't number");
            return;
        }

        //подключение к серверу
        if (!(connecter(usr_name.getText(), ip.getText(), portstr))) {
            ip.setText("can not connect");
            return;
        }

        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            gotin = in.readUTF();
            if (gotin.equals("connected")) {
                out.writeUTF(usr_name.getText());
                gotin = in.readUTF();
                if (!gotin.equals("connected")) {
                    usr_name.setText(gotin);
                } else {
                    play = true;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }


        //creating game table
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


        //TODO: get players and understand what to do with them

        // get ammo of players - ammo
        //get array of structs Player - players

        //starting to play
        while (play) {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                gotin = in.readUTF();
                comand = makecmd(gotin);
                switch (comand) {
                    case "game started": {

                    }

                    case "your turn": {
//блайнд на сервере?
                        able = true;

                    }
                    case "bet": {
                        players[defplayer(defname(gotin))].betsum += defvalue(gotin);
                        players[defplayer(defname(gotin))].cash -= defvalue(gotin);
                        maxbet = defvalue(gotin);

                    }
                    case "blind": {
                        players[defplayer(defname(gotin))].betsum += defvalue(gotin);
                        players[defplayer(defname(gotin))].cash -= defvalue(gotin);

                    }
                    case "check": {
                        defname(gotin);
                        //убрать подсветку?

                    }
                    case "fold": {
                        defname(gotin);
                        //убрать карты


                    }
                    case "winner": {
                        players[defplayer(defname(gotin))].cash += defvalue(gotin);
                        //показать анимацию?


                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private int defplayer(String name) {
        for (int i = 0; i < ammo; ++i)
            if (name.equals(players[i].name))
                return i;
        return -1;
    }

    private String makecmd(String inp) {
        int i = 0;
        String res = "";
        boolean found = false;
        while ((!found) & (i < inp.length())) {
            res += inp.charAt(i);
            i += 1;
            if (inp.charAt(i) == ':') {
                found = true;
            }
        }
        return res;
    }

    private String defname(String inp) {
        int i = 0;
        String res = "";
        boolean found = false;
        while (inp.charAt(i) != ':') {
            i += 1;
        }
        i += 1;
        while (inp.charAt(i) != ':') {
            i += 1;
        }
        i += 1;
        while ((!found) & (i < inp.length())) {
            res += inp.charAt(i);
            i += 1;
            if (inp.charAt(i) == ':') {
                found = true;
            }
        }
        return res;
    }

    private int defvalue(String inp) {
        int i = 0;
        String res = "";
        boolean found = false;
        while (inp.charAt(i) != ':') {
            i += 1;
        }
        i += 1;
        while (!found) {
            res += inp.charAt(i);
            i += 1;
            if (inp.charAt(i) == ':') {
                found = true;
            }
        }
        return Integer.parseInt(res);
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

    private boolean connecter(String name, String IP, String port) {
        try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket(IP, parseInt(port));
            Thread.sleep(2000);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void raise_act(ActionEvent actionEvent) {
        if ((able) & (players[myID].cash > maxbet + 50)) {
            able = false;
            maxbet += 50;
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("bet:" + String.valueOf(maxbet));
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void check_act(ActionEvent actionEvent) {
        if (able) {
            able = false;
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("check:-1");
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void fold_act(ActionEvent actionEvent) {
        if (able) {
            able = false;
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("fold:-1");
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void call_act(ActionEvent actionEvent) {
        if ((able) & (players[myID].cash > maxbet)) {
            able = false;
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("check:" + String.valueOf(maxbet));
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    private void first(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
    }

    private void second(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(250);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

    }

    private void third(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(450);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

    }

    private void fourth(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(650);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

    }

    private void fiveth(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(850);
        imageView.setY(50);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

    }

    private void sixth(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        //Setting the position of the image
        imageView.setX(450);
        imageView.setY(530);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

    }

    private void seventh(Card card) {
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
