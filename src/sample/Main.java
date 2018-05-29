package sample;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main extends Application{

    private final int WINDOW_HEIGHT = 600, WINDOW_WIDTH = 900;
    private BorderPane mainBorderPane;
    private GridPane loginGrid;
    private GridPane mainGrid;
    private BorderPane topPane;
    private boolean centerLogin = true;
    private Button buttonLogOut;
    private PasswordField passInput;
    private TextField nameInput;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Twitter");
        window.setResizable(false);
        window.centerOnScreen();
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

        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel, 0, 0);

        nameInput = new TextField();
        nameInput.setPromptText("login");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel, 0, 1);

        passInput = new PasswordField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        Button loginButton = new Button("Log in");
        loginButton.setOnAction(e -> LogIn());
        GridPane.setConstraints(loginButton, 1, 2);

        loginGrid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);
        //END CENTER
    }

    private void CreateMainGrid() {
        mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(300,10,10,300));
        mainGrid.setVgap(8);
        mainGrid.setHgap(10);

        Label nameLabel = new Label("LOL");
        GridPane.setConstraints(nameLabel, 0, 0);
        mainGrid.getChildren().addAll(nameLabel);
        //END CENTER
    }

    private void CreateTop() {
        //TOP
        topPane = new BorderPane();

        HBox topCenterMenu = new HBox();
        topCenterMenu.setPadding(new Insets(15,12,35,100));
        topCenterMenu.setSpacing(10);
        topCenterMenu.setStyle("-fx-background-color: #339699;");
        Text text = new Text("Twitter");
        topCenterMenu.getChildren().addAll(text);

        HBox topLeftMenu = new HBox();
        topLeftMenu.setPadding(new Insets(15,12,35,50));
        topLeftMenu.setSpacing(10);
        topLeftMenu.setStyle("-fx-background-color: #324699;");
        Label logo = new Label("logo");
        topLeftMenu.getChildren().addAll(logo);

        HBox topRightMenu = new HBox();
        topRightMenu.setPadding(new Insets(15,12,35,50));
        topRightMenu.setSpacing(10);
        topRightMenu.setStyle("-fx-background-color: #336699;");
        buttonLogOut = new Button("Log out");
        buttonLogOut.setDisable(true);
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
            buttonLogOut.setDisable(true);
        }

    }

    private void LogIn() {
//
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthConsumerKey("0Cj4B7jX58aBX9weNgZJ2ymks")
//                .setOAuthConsumerSecret("IcTBCn40cdBaDp3npx0QbGE7jR127Qajd5lNJbi8GrDQooTflW")
//                .setOAuthAccessToken("972505891-yBxtxjXfzBeRQfmu5NboOOfy9TG55qM0NVSemLJb")
//                .setOAuthAccessTokenSecret("yZlQEy6tRPDjwqvGa9X0pEqUOtWzU6o6veptPbDuV1MOJ");
//        TwitterFactory tf = new TwitterFactory(cb.build());
//
//        Twitter twitter = tf.getInstance();
//
//        try {
//            Status stat = twitter.updateStatus("kek");
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        }
        Twitter tw = tt();
        try {
            if (tw != null) {
                Status status = tw.updateStatus("ee");
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        PutCenterClient();
        System.out.println("log in");
        centerLogin = false;
        buttonLogOut.setDisable(false);
    }




    private void PutCenterClient() {
        CreateMainGrid();
        mainBorderPane.setCenter(mainGrid);
    }


    private void PutCenterLogin() {
        CreateCenter();
        mainBorderPane.setCenter(loginGrid);
    }

    private static Twitter tt() {

        String testStatus="Hello from twitter4j";

        ConfigurationBuilder cb = new ConfigurationBuilder();


        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Cj4B7jX58aBX9weNgZJ2ymks")
                .setOAuthConsumerSecret("IcTBCn40cdBaDp3npx0QbGE7jR127Qajd5lNJbi8GrDQooTflW");

        try {
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            try {
                System.out.println("-----");

                // get request token.
                // this will throw IllegalStateException if access token is already available
                // this is oob, desktop client version
                RequestToken requestToken = twitter.getOAuthRequestToken();

                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());

                System.out.println("|-----");

                AccessToken accessToken = null;

                //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                //Scanner in = new Scanner(System.in);

                //while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = ConfirmBox.display();

                    try {

                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);

                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to get the access token.");
                        } else {
                            //te.printStackTrace();
                        }
                    }
                //}
                System.out.println("Got access token.");
                if (accessToken != null) {
                    System.out.println("Access token: " + accessToken.getToken());
                }
                if (accessToken != null) {
                    System.out.println("Access token secret: " + accessToken.getTokenSecret());
                }

            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret is not set.");
                    //System.exit(-1);
                }
            }

            //Status status = twitter.updateStatus(testStatus);

            System.out.println("Successfully updated the status to [].");


            System.out.println("ready exit");
            return twitter;

            //System.exit(0);
        } catch (TwitterException te) {
            //te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            //System.exit(-1);
        }
        return null;
    }

}
