package sample.messageBoxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import sample.fonts.Fonts;
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
        final int LIMIT = 7;
        TextField tf = new TextField();
        tf.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                // Check if the new character is greater than LIMIT
                if (tf.getText().length() >= LIMIT) {
                    // if it's 11th character then just setText to previous
                    // one
                    tf.setText(tf.getText().substring(0, LIMIT));
                }
            }
        });
        tf.setText("");
        tf.setFont(font);

        Label label = new Label("Enter pin");
        label.setTextFill(Color.web("#C59AF9"));
        label.setFont(font);

        Button btn = new Button("Go");
        btn.setFont(font);
        btn.setOnAction(e -> {
            pin = tf.getText();
            window.close();
        });

        font = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 26);
        tf.setFont(font);

        VBox layout = new VBox();
        layout.setPadding(new Insets(10,30,10,30));
        layout.setStyle("-fx-background-color: #EDFCFC;");
        layout.getChildren().addAll(label, tf, btn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> {
            pin = "-1";
            window.close();
        });

        return pin;
    }
}
