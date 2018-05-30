package sample.messageBoxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmPinBox {

    private static String pin = "-1";

    public static String display(Font font){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Twitter");
        window.setResizable(false);
        window.centerOnScreen();

        TextField tf = new TextField();
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
