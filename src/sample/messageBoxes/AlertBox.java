package sample.messageBoxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {
    public static void display(String title, String message, Font font) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("file:src/pics/logo.png"));
        window.setTitle(title);
        window.setResizable(false);
        window.centerOnScreen();

        Label label = new Label();
        label.setFont(font);
        label.setText(message);
        Button closeButton = new Button("Ok");
        closeButton.setFont(font);
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #EDFCFC;");
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}
