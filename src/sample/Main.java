package sample;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application{

    private final int WINDOW_HEIGHT = 600, WINDOW_WIDTH = 900;
    private BorderPane mainBorderPane;
    private GridPane loginGrid;
    private GridPane mainGrid;
    private BorderPane topPane;
    private boolean centerLogin = true;
    private Button buttonLogOut;

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

        TextField nameInput = new TextField();
        nameInput.setPromptText("login");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel, 0, 1);

        TextField passInput = new TextField();
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

}