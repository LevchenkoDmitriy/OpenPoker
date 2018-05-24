

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    Card card = new Card();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Open Poker");

        Scene scene = new Scene(root,1280,720);

        primaryStage.setScene(scene);
        primaryStage.show();
        card.suit=1;
        card.dignity=1;
        first(card);

    }
    private void first (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        imageView.setX(50) ;
        imageView.setY(50) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;
    }

    private void second (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        imageView.setX(250) ;
        imageView.setY(50) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }
    private void third (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        imageView.setX(450) ;
        imageView.setY(50) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }
    private void fourth (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        imageView.setX(650) ;
        imageView.setY(50) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }
    private void fiveth (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        imageView.setX(850) ;
        imageView.setY(50) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }
    private void sixth (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        //Setting the position of the image
        imageView.setX(450) ;
        imageView.setY(530) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }
    private void seventh (Card card){
        String path = new String();

        path = card.setPath(card);
        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream(path) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image) ;

        //Setting the position of the image
        //Setting the position of the image
        imageView.setX(450) ;
        imageView.setY(530) ;

        //setting the fit height and width of the image view
        imageView.setFitHeight(200) ;
        imageView.setFitWidth(150) ;

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true) ;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
