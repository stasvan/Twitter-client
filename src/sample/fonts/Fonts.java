package sample.fonts;

import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Fonts {

    public static Font LoadFont(String path, int size){

        try {
            return Font.loadFont(new FileInputStream(new File(path)), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
