package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Navigation;

import java.io.IOException;
import java.net.URL;

public class SplashScreenFormController {
    public Label lblStatus;
    public Rectangle pgbLoad;
    public Rectangle pgbContainer;

    public void initialize(){
        Timeline t1 = new Timeline();
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblStatus.setText("Connecting with database...!");
                pgbLoad.setWidth(pgbLoad.getWidth()+75);
            }
        });
         KeyFrame keyFrame2 = new KeyFrame(Duration.millis(750), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblStatus.setText("Loading data...!");
                pgbLoad.setWidth(pgbLoad.getWidth()+200);
            }
        });
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(1250), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblStatus.setText("Setting up the UI...!");
                pgbLoad.setWidth(pgbContainer.getWidth());
            }
        });
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL resource = this.getClass().getResource("/view/HomeForm.fxml");
                    Parent homeFormContainer = FXMLLoader.load(resource);

                    AnchorPane pneContainer = (AnchorPane) homeFormContainer.lookup("#pneContainer"); // lookup(css selector - need # for id)
                    Navigation.init(pneContainer);

                    Scene scene = new Scene(homeFormContainer);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("National Fuel Pass App");
                    stage.show();
                    stage.centerOnScreen();
                    lblStatus.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3,keyFrame4);
        t1.playFromStart();
    }
}
