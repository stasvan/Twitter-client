package sample.mainForm;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import sample.messageBoxes.AlertBox;
import sample.fonts.Fonts;
import sample.twitterLogin.TwitterLogin;
import twitter4j.Twitter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application{

    private final int WINDOW_HEIGHT = 600, WINDOW_WIDTH = 900;
    private BorderPane mainBorderPane;
    private GridPane loginGrid;
    private GridPane mainGrid;
    private BorderPane topPane;
    private boolean centerLogin = true;
    private Button buttonLogOut;
    private Button buttonLogIn;
    private Twitter twitter;
    private Font font;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Twitter");
        window.setResizable(false);
        window.centerOnScreen();
        font = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 12);
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
        CreateMainGrid();
    }

    private void CreateLoginGrid() {
        //CENTER
        loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(300,10,10,300));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        buttonLogIn = new Button("Log in");
        buttonLogIn.setFont(font);
        buttonLogIn.setOnAction(e -> LogIn());
        GridPane.setConstraints(buttonLogIn, 1, 2);

        loginGrid.getChildren().addAll(buttonLogIn);
        loginGrid.setStyle("-fx-background-color: #EDFCFC;");
        //END CENTER
    }

    private void CreateMainGrid() {
        mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(300,10,10,100));
        mainGrid.setVgap(8);
        mainGrid.setHgap(10);
        mainGrid.setStyle("-fx-background-color: #EDFCFC;");
        Label nameLabel = new Label("LOL");
        GridPane.setConstraints(nameLabel, 0, 0);
        mainGrid.getChildren().addAll(nameLabel);
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
        Font fontOut = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 20);
        buttonLogOut.setFont(fontOut);
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
