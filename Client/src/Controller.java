import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class Controller {


    public void play_poc(javafx.scene.input.MouseEvent mouseEvent) {
        //connect()
        Parent SecondSceneParent = null;
        try {
            SecondSceneParent = FXMLLoader.load(getClass().getResource("desktop.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert SecondSceneParent != null;
        Scene SecondScene = new Scene(SecondSceneParent);

        Stage window = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(SecondScene);
        window.show();

    }

    public void exit(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

    private void connect( String name, String IP, String port) {

    }
}
