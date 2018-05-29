package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

    private static String pin;

    public static String display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ere");
        window.setMinWidth(250);
        TextField tf = new TextField();
        Button btn = new Button("go");
        btn.setOnAction(e -> {
            pin = tf.getText();
            window.close();
        });
        VBox layout = new VBox();
        layout.getChildren().addAll(tf, btn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> {
            pin = "err";
            window.close();
        });

        return pin;
    }
}
