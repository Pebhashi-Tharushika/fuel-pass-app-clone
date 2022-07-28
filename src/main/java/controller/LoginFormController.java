package controller;

import db.InMemoryDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.Navigation;
import util.Routes;

import java.io.IOException;

public class LoginFormController {

    public TextField txtNIC;
    public Button btnLogin;

    public void initialize(){
        Platform.runLater(txtNIC::requestFocus);
    }

    public void lblRegisterHereOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.REGISTRATION);

    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if(!RegisterFormController.isValidNIC(txtNIC.getText()) || InMemoryDB.findUser(txtNIC.getText())==null){
            new Alert(Alert.AlertType.ERROR,"Please enter valid NIC to login").showAndWait();
            txtNIC.requestFocus();
        }else{
            Navigation.navigate(Routes.DASHBOARD);
        }
    }
}
