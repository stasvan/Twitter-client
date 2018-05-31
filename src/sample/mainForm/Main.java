package sample.mainForm;

import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import sample.messageBoxes.AlertBox;
import sample.fonts.Fonts;
import sample.twitterLogin.TwitterLogin;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import static twitter4j.JSONObjectType.Type.LIMIT;

public class Main extends Application{

    private final int WINDOW_HEIGHT = 600, WINDOW_WIDTH = 900;
    private BorderPane mainBorderPane;
    private VBox loginGrid;
    private GridPane mainGrid;
    private BorderPane topPane;
    private boolean centerLogin = true;
    private Button buttonLogOut;
    private Button buttonLogIn;
    private Label labelLogIn;
    private Label welcom;
    private Button twitButton;
    private ImageView selectedImage;
    private TextArea twitArea;
    private Twitter twitter;
    private Font font, fontMainGridButtons;
    private User user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Twitter");
        window.setResizable(false);
        window.centerOnScreen();
        window.getIcons().add(new Image("file:src/pics/logo.png"));
        font = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 20);
        CreateCenter();
        CreateTop();
        mainBorderPane = new BorderPane();
        CreateMainBorderPane();
        Scene scene = new Scene(mainBorderPane, WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setScene(scene);
        window.show();
    }

    private void CreateCenter() {
        CreateLoginGrid();
        //CreateMainGrid();
    }

    private void CreateLoginGrid() {
        //CENTER
        loginGrid = new VBox();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setPadding(new Insets(80,0,10,0));

        buttonLogIn = new Button("Log in");
        buttonLogIn.setFont(font);
        buttonLogIn.setOnAction(e -> LogIn());

        Font fontLabelLogIn = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 30);
        labelLogIn = new Label("Press the button to Log in!");
        welcom = new Label("Welcome!");
        labelLogIn.setFont(fontLabelLogIn);
        fontLabelLogIn = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 60);
        welcom.setFont(fontLabelLogIn);
        labelLogIn.setTextFill(Color.web("#C59AF9"));

        loginGrid.getChildren().addAll(welcom, labelLogIn, buttonLogIn);
        loginGrid.setStyle("-fx-background-color: #EDFCFC;");
        //END CENTER
    }

    private void CreateMainGrid() {
        //fontMainGridButtons  = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 20);
        mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10,10,10,10));
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);
        mainGrid.setStyle("-fx-background-color: #EDFCFC;");
        twitArea = new TextArea();
        twitArea.setPrefColumnCount(45);
        twitArea.setPrefRowCount(4);
        final int LIMIT = 280;
        twitArea.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                // Check if the new character is greater than LIMIT
                if (twitArea.getText().length() >= LIMIT) {
                    // if it's 11th character then just setText to previous
                    // one
                    twitArea.setText(twitArea.getText().substring(0, LIMIT));
                }
            }
        });
        twitArea.setWrapText(true);
        GridPane.setConstraints(twitArea, 0, 0);

        twitButton = new Button("Twit");
        twitButton.setPadding(new Insets(0,10,0,10));
        twitButton.setFont(font);
        GridPane.setConstraints(twitButton, 1, 0);

        selectedImage = new ImageView();

        try {
            user = twitter.showUser(twitter.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        String path = user.getOriginalProfileImageURL();
        Image logo = new Image(path);
        selectedImage.setImage(logo);
        selectedImage.setFitHeight(100);
        selectedImage.setFitWidth(100);
        GridPane.setConstraints(selectedImage, 2, 0);

        mainGrid.getChildren().addAll(twitArea, twitButton, selectedImage);
        //END CENTER
    }

    private void CreateTop() {
        //TOP
        topPane = new BorderPane();

        HBox topCenterMenu = new HBox();
        topCenterMenu.setPadding(new Insets(15,0,10,50));
        topCenterMenu.setSpacing(10);
        topCenterMenu.setStyle("-fx-background-color: #B2F1F5;");
        Label text = new Label("Twitter Client");
        Font fontBig = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 60);
        text.setFont(fontBig);
        text.setTextFill(Color.web("#4169E1"));
        topCenterMenu.getChildren().addAll(text);

        HBox topLeftMenu = new HBox();
        topLeftMenu.setPadding(new Insets(5,5,10,10));
        //topLeftMenu.setSpacing(10);
        topLeftMenu.setStyle("-fx-background-color: #B2F1F5;");
        try {
            ImageView selectedImage = new ImageView();
            Image logo = new Image(new FileInputStream("src/pics/logo.png"));
            selectedImage.setImage(logo);
            topLeftMenu.getChildren().add(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        HBox topRightMenu = new HBox();
        topRightMenu.setPadding(new Insets(45,30,5,0));
        //topRightMenu.setSpacing(10);
        topRightMenu.setStyle("-fx-background-color: #B2F1F5;");
        buttonLogOut = new Button("Log out");
        buttonLogOut.setFont(font);
        buttonLogOut.setTextFill(Color.web("#4169E1"));
        buttonLogOut.setVisible(false);
        buttonLogOut.setOnAction(e -> LogOut());
        topRightMenu.getChildren().addAll(buttonLogOut);

        topPane.setRight(topRightMenu);
        topPane.setCenter(topCenterMenu);
        topPane.setLeft(topLeftMenu);
        //END TOP

    }

    private void CreateMainBorderPane() {

        mainBorderPane.setCenter(loginGrid);
        mainBorderPane.setTop(topPane);

    }

    private void LogOut() {
        if (!centerLogin) {
            twitter = null;
            PutCenterLogin();
            System.out.println("log out");
            centerLogin = true;
            buttonLogOut.setVisible(false);
        }

    }

    private void LogIn() {
        twitter = TwitterLogin.TwitterIs();
        if (twitter != null) {
            PutCenterClient();
            System.out.println("log in");
            AlertBox.display("Message", "You are logged in", font);
            centerLogin = false;
            buttonLogOut.setVisible(true);
        } else {
            System.out.println("log in fail");
        }
    }

    private void PutCenterClient() {
        CreateMainGrid();
        mainBorderPane.setCenter(mainGrid);
    }

    private void PutCenterLogin() {
        CreateCenter();
        mainBorderPane.setCenter(loginGrid);
    }

}
