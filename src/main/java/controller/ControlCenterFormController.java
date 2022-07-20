package controller;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

public class ControlCenterFormController {

    public AnchorPane pneControlCenter;

    public void initialize(){
        Platform.runLater(pneControlCenter::requestFocus);
    }
}
