import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        URL resource = this.getClass().getResource("/view/SplashScreenForm.fxml");
        Parent container = FXMLLoader.load(resource);
        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        //scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.centerOnScreen();

        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save QR Code");
        //File file = new File("/home/pebhashi");
        File file = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(file);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files(*.txt)","*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF files(*.gif)","*.gif"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files(*.jpeg | *jpg)","*.jpg","*.jpeg"));
        fileChooser.showSaveDialog(primaryStage);*/

    }
}
