package sample.messageBoxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import twitter4j.auth.RequestToken;

import java.awt.*;
import java.net.URI;


public class ConfirmPinBox {

    private static String pin = "-1";

    public static String display(Font font, Desktop desktop, RequestToken requestToken){
        pin = "-1";
        Stage window = new Stage();
        window.getIcons().add(new Image("file:src/pics/logo.png"));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Twitter");
        window.setResizable(false);
        window.centerOnScreen();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(requestToken.getAuthorizationURL())); } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TextField tf = new TextField();
        tf.setText("");
        tf.setFont(font);
        Button btn = new Button("Go");
        btn.setFont(font);
        btn.setOnAction(e -> {
            pin = tf.getText();
            window.close();
        });

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #EDFCFC;");
        layout.getChildren().addAll(tf, btn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> {
            pin = "-1";
            window.close();
        });

        return pin;
    }
}
