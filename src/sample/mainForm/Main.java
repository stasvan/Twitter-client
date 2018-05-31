package sample.mainForm;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import twitter4j.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application{

    private final int WINDOW_HEIGHT = 700, WINDOW_WIDTH = 1000;
    private BorderPane mainBorderPane;
    private VBox loginGrid;
    private GridPane mainGrid;
    private BorderPane topPane;
    private boolean centerLogin = true;
    private Button buttonLogOut;
    private Button buttonLogIn;
    private Label labelLogIn;
    private Label welcom;
    private Label nick;
    private Label tweetsCount;
    private Label followersCount;
    private Label followingCount;
    private Button tweetButton;
    private Button showFriendsTweets;
    private Button showMyTweets;
    private ImageView selectedImage;
    private TextArea tweetArea;
    private TextArea tweetsArea;
    private Twitter twitter;
    private Font font;
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
        loginGrid.setPadding(new Insets(60,0,10,0));

        buttonLogIn = new Button("Log in");
        buttonLogIn.setFont(Fonts.LoadFont("src/fonts/ObelixPro.ttf", 30));
        buttonLogIn.setOnAction(e -> LogIn());

        Font fontLabelLogIn = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 30);
        labelLogIn = new Label("Press the button to Log in");
        welcom = new Label("Welcome!");
        welcom.setPadding(new Insets(0,0,30,0));
        labelLogIn.setPadding(new Insets(10,0,20,0));
        labelLogIn.setFont(fontLabelLogIn);
        fontLabelLogIn = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 70);
        welcom.setFont(fontLabelLogIn);
        labelLogIn.setTextFill(Color.web("#C59AF9"));

        loginGrid.getChildren().addAll(welcom, labelLogIn, buttonLogIn);
        loginGrid.setStyle("-fx-background-color: #EDFCFC;");
        //END CENTER
    }

    private void CreateMainGrid() {
        mainGrid = new GridPane();
        mainGrid.setPrefSize(100,100);
        mainGrid.setPadding(new Insets(10,10,10,10));
        mainGrid.setVgap(25);
        mainGrid.setHgap(25);
        mainGrid.setStyle("-fx-background-color: #EDFCFC;");
        tweetArea = new TextArea();
        tweetArea.setPrefColumnCount(35);
        tweetArea.setPrefRowCount(4);
        tweetArea.setFont(new Font(15));
        tweetArea.setFocusTraversable(true);
        final int LIMIT = 280;
        tweetArea.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (tweetArea.getText().length() >= LIMIT) {
                    tweetArea.setText(tweetArea.getText().substring(0, LIMIT));
                }
            }
        });
        tweetArea.setWrapText(true);
        mainGrid.add(tweetArea, 0, 0);

        VBox butVBox = new VBox();
        butVBox.setPadding(new Insets(0, 5, 0, 5));
        tweetButton = new Button("Tweet");
        tweetButton.setOnAction(e -> Twit(twitter));
        tweetButton.setPadding(new Insets(5, 20, 5, 20));
        tweetButton.setFont(font);
        nick = new Label("");
        Font nickFont = new Font(20);
        nick.setFont(nickFont);
        nick.setTextFill(Color.web("#C59AF9"));
        nick.setPadding(new Insets(0, 0, 5, 0));
        nickFont = new Font(15);
        tweetsCount = new Label("");
        tweetsCount.setFont(nickFont);
        tweetsCount.setTextFill(Color.web("#C59AF9"));
        tweetsCount.setPadding(new Insets(0, 0, 5, 0));
        followersCount = new Label("");
        followersCount.setFont(nickFont);
        followersCount.setTextFill(Color.web("#C59AF9"));
        followersCount.setPadding(new Insets(0, 0, 5, 0));
        followingCount = new Label("");
        followingCount.setFont(nickFont);
        followingCount.setTextFill(Color.web("#C59AF9"));
        followingCount.setPadding(new Insets(0, 0, 5, 0));

        butVBox.getChildren().addAll(nick,tweetsCount, followersCount, followingCount, tweetButton);
        mainGrid.add(butVBox, 1, 0);

        selectedImage = new ImageView();

        try {
            user = twitter.showUser(twitter.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        String path = user.getOriginalProfileImageURL();
        Image logo = new Image(path);
        selectedImage.setImage(logo);
        selectedImage.setFitHeight(149);
        selectedImage.setFitWidth(149);
        mainGrid.add(selectedImage, 2, 0);

        tweetsArea = new TextArea();
        tweetsArea.setPrefColumnCount(44);
        tweetsArea.setPrefRowCount(18);
        tweetsArea.setFont(new Font(15));
        tweetsArea.setFocusTraversable(true);
        tweetsArea.setWrapText(true);
        tweetsArea.setEditable(false);
        mainGrid.add(tweetsArea, 0, 1);

        showFriendsTweets = new Button("Show F");
        showFriendsTweets.setOnAction(e -> tweetsArea.setText(FriendsTweets().toString()));
        mainGrid.add(showFriendsTweets, 1, 1);

        showMyTweets = new Button("Show M");
        showMyTweets.setOnAction(e -> tweetsArea.setText(MyTweets().toString()));
        mainGrid.add(showMyTweets, 2, 1);

        mainGrid.setGridLinesVisible(true);

    }

    private void Twit(Twitter twitter) {

        if (twitter != null) {

            try {
                if (!tweetArea.getText().trim().equals("")) {
                    twitter.updateStatus(tweetArea.getText());
                    tweetArea.setText("");
                    AlertBox.display("Message", "Tweetted!", font);
                } else {
                    AlertBox.display("Message", "Tweet is empty!", font);
                }
            } catch (TwitterException e) {
                AlertBox.display("Message", "Error", font);
            }
            UpdateClientInfo();
        }

    }

    private void CreateTop() {
        //TOP
        topPane = new BorderPane();

        HBox topCenterMenu = new HBox();
        topCenterMenu.setPadding(new Insets(15,0,10,70));
        topCenterMenu.setSpacing(10);
        topCenterMenu.setStyle("-fx-background-color: #B2F1F5;");
        Label text = new Label("Twitter Client");
        Font fontBig = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 65);
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


        VBox topRightMenu = new VBox();
        topRightMenu.setPadding(new Insets(55,30,5,0));
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
        buttonLogIn.setDisable(true);
        twitter = TwitterLogin.TwitterIs();
        if (twitter != null) {
            PutCenterClient();
            System.out.println("log in");
            //AlertBox.display("Message", "You are logged in", font);
            UpdateClientInfo();
            centerLogin = false;
            buttonLogOut.setVisible(true);
        } else {
            System.out.println("log in fail");
        }
        buttonLogIn.setDisable(false);
    }

    private void UpdateClientInfo() {

        try {
            user = twitter.showUser(twitter.getId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        nick.setText(user.getScreenName());
        tweetsCount.setText("Tweets: " + user.getStatusesCount());
        followersCount.setText("Followers: " + user.getFollowersCount());
        followingCount.setText("Following: " + user.getFriendsCount());
        tweetsArea.setText(MyTweets().toString());

    }

    private StringBuilder MyTweets() {
        ResponseList<Status> list;
        StringBuilder str = new StringBuilder("-----------------------------------------------------------------------------------------------------\n");
        try {
            list = twitter.getUserTimeline();
            for (Status status : list) {
                str.append("@").append(status.getUser().getScreenName()).append(" - ").append(status.getText()).append("\n").append("-----------------------------------------------------------------------------------------------------\n");
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return str;
    }

    private StringBuilder FriendsTweets() {
        ResponseList<Status> list;
        StringBuilder str = new StringBuilder("-----------------------------------------------------------------------------------------------------\n");
        try {
            list = twitter.getHomeTimeline();
            for (Status status : list) {
                str.append("@").append(status.getUser().getScreenName()).append(" - ").append(status.getText()).append("\n").append("-----------------------------------------------------------------------------------------------------\n");
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return str;
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
