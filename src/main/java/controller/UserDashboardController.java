package controller;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

public class UserDashboardController {
    public AnchorPane pneDashboard;

    public void initialize(){
        Platform.runLater(pneDashboard::requestFocus);
    }
}
