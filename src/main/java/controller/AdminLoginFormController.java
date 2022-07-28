package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.Navigation;
import util.Routes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class AdminLoginFormController {
    public PasswordField txtPassword;
    private static final String ADMIN_PASSWORD = "dep9@ADMIN";
    public AnchorPane pneAdminLoginForm;
    private static int attempts = 3;

    public void initialize(){
        Platform.runLater(txtPassword::requestFocus);
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {

        if(!txtPassword.getText().equals(ADMIN_PASSWORD)){

            if(attempts==0){
                new Alert(Alert.AlertType.ERROR,"Sorry, You have reached maximum number of attempts").showAndWait();
                Platform.exit();
                return;
            }

            URL resource = this.getClass().getResource("/audio/security-breach.mp3");
            Media media = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();


            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid admin password. You have "+ attempts+" more attempts to try again");

            attempts--;

            InputStream resourceAsStream = this.getClass().getResourceAsStream("/image/access-denied.png");
            Image img = new Image(resourceAsStream);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(48);
            imgView.setFitHeight(48);
            alert.setGraphic(imgView);

            alert.setHeaderText("Invalid Login Credential!");

            alert.setTitle("Access Denied");

            alert.showAndWait();

            mediaPlayer.dispose();

            txtPassword.requestFocus();
            return;
        }

        Navigation.navigate(Routes.CONTROL_CENTER);
    }
}
