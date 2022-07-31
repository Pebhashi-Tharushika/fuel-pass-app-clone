package controller;

import com.google.zxing.WriterException;
import db.InMemoryDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.io.InputStream;

public class LoginFormController {

    public TextField txtNIC;
    public Button btnLogin;

    public void initialize(){
        Platform.runLater(txtNIC::requestFocus);
    }

    public void lblRegisterHereOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.REGISTRATION);

    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, WriterException {
        if(!RegisterFormController.isValidNIC(txtNIC.getText()) || InMemoryDB.findUser(txtNIC.getText())==null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid NIC to continue");

            InputStream resourceAsStream = this.getClass().getResourceAsStream("/image/access-denied.png");
            Image img = new Image(resourceAsStream);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(48);
            imgView.setFitHeight(48);
            alert.setGraphic(imgView);

            alert.setHeaderText("Invalid NIC");
            alert.setTitle("Access Denied");

            alert.showAndWait();

            txtNIC.requestFocus();
            txtNIC.selectAll();
        }else{
            UserDashboardController ctrl = (UserDashboardController) Navigation.navigate(Routes.DASHBOARD);
            ctrl.setData(txtNIC.getText());
        }
    }
}
