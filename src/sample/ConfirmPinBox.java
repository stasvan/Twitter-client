package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmPinBox {

    private static String pin;

    public static String display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Enter pin");
        window.setMinWidth(250);

        TextField tf = new TextField();
        Button btn = new Button("Go");
        btn.setOnAction(e -> {
            pin = tf.getText();
            window.close();
        });

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #B2F1F5;");
        layout.getChildren().addAll(tf, btn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> {
            window.close();
        });

        return pin;
    }
}
